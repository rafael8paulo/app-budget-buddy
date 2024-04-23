package br.com.rpx.budgetbuddy.services;

import br.com.rpx.budgetbuddy.entities.Expense;
import br.com.rpx.budgetbuddy.entities.Installment;
import br.com.rpx.budgetbuddy.repositories.InstallmentRepository;
import br.com.rpx.budgetbuddy.services.exceptions.BudgetBuddyException;
import br.com.rpx.budgetbuddy.services.exceptions.DatabaseException;
import br.com.rpx.budgetbuddy.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InstallmentService {

    private InstallmentRepository repository;

    public InstallmentService(InstallmentRepository repository) {
        this.repository = repository;
    }

    public Set<Installment> generateInstallments(
            int qtyInstallment,
            final int dueDay,
            final BigDecimal value,
            final Expense expense
    ) {

        LocalDate dueDate = LocalDate.of(
                LocalDate.now().getYear(),
                LocalDate.now().getMonth(),
                dueDay
        );

        List<Installment> installments = new ArrayList<>();
        Installment installment;

        final BigDecimal qtyInstallmentBd = new BigDecimal(qtyInstallment);
        final BigDecimal valueInstallment = value.divide(qtyInstallmentBd, 2, RoundingMode.HALF_UP);

        for (int i = 0; i < qtyInstallment; i++) {
            dueDate = dueDate.plusMonths(1);
            installment = new Installment(i + 1, dueDate, valueInstallment, expense);
            installments.add(installment);
        }

        final BigDecimal resultMultiply = valueInstallment.multiply(qtyInstallmentBd);
        final int result = value.compareTo(resultMultiply);

        if (!(result == 0)) {
            log.error("A soma das parcelas é diferente do valor total da despesas");
            final BigDecimal resultSubtract = resultMultiply.subtract(value);
            log.error("Está faltando: " + resultSubtract);

            if (resultSubtract.compareTo(BigDecimal.ZERO) < 0)
                installments.get(installments.size() - 1).setInstallmentValue(
                        installments.get(installments.size() - 1).getInstallmentValue().subtract(resultSubtract)
                );
            else
                installments.get(installments.size() - 1).setInstallmentValue(
                        installments.get(installments.size() - 1).getInstallmentValue().add(resultSubtract)
                );

        }


        return installments.stream().collect(Collectors.toSet());
    }

    public List<Installment> getByDate(
            final LocalDate dtStart,
            final LocalDate dtStop
    ) {

        if (!dtStart.isAfter(dtStop)) {
            List<Installment> installments = repository.getByDate(dtStart, dtStop);
            return installments;
        } else
            throw new BudgetBuddyException("A data inicial deve ser menor que a data final");

    }
    @Transactional
    public void delete(Long id) {
        try {

            Installment obj = repository.getReferenceById(id);

            if(!obj.isPaid())
                repository.findById(id).ifPresent(installment -> repository.delete(installment));
            else
                throw new BudgetBuddyException("Não é permitido excluír uma parcela que já está paga");

        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Installment pay(Long id) {
        try {
            Installment i = new Installment(true, LocalDate.now());
            return update(id, i);
        }catch (Exception e) {
            throw new BudgetBuddyException("Ocorreu um erro ao tentar registrar o pagamento");
        }
    }
    public Installment update(Long id, Installment obj) {
        try {
            Installment entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Installment entity, Installment obj) {
        entity.setPaid(obj.isPaid());
        entity.setPayDate(obj.getPayDate());
    }

}
