package br.com.rpx.budgetbuddy.services;

import br.com.rpx.budgetbuddy.dto.ExpenseCreateDTO;
import br.com.rpx.budgetbuddy.dto.ExpenseDTO;
import br.com.rpx.budgetbuddy.dto.ExpenseUpdateDTO;
import br.com.rpx.budgetbuddy.entities.Expense;
import br.com.rpx.budgetbuddy.entities.Installment;
import br.com.rpx.budgetbuddy.repositories.ExpenseRepository;
import br.com.rpx.budgetbuddy.services.exceptions.BudgetBuddyException;
import br.com.rpx.budgetbuddy.services.exceptions.DatabaseException;
import br.com.rpx.budgetbuddy.services.exceptions.ResourceNotFoundException;
import br.com.rpx.budgetbuddy.util.StringUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class ExpenseService {

    private ExpenseRepository expenseRepository;
    private InstallmentService installmentService;

    public List<Expense> getExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses;
    }

    public ExpenseService(
            ExpenseRepository expenseRepository,
            InstallmentService installmentService
    ) {
        this.expenseRepository = expenseRepository;
        this.installmentService = installmentService;
    }

    public Expense create(ExpenseCreateDTO dto) {

        dto.validation();

        try {
            Expense expense = new Expense(dto.getValue(), dto.getDescription());
            Set<Installment> installments = installmentService.generateInstallments(dto.getQtyInstallment(), dto.getDueDay(), dto.getValue(), expense);
            expense.setInstallments(installments);
            return expenseRepository.save(expense);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BudgetBuddyException("Ocorreu um erro ao tentar salvar a despesa.");
        }

    }

    public BigDecimal getTotalExpenseAmount(
            final LocalDate dtStart,
            final LocalDate dtStop
    ) {
        return expenseRepository.getTotalExpenseAmount(dtStart, dtStop);
    }

    public Page<Expense> findAll(
            final String description,
            final Pageable pageable
    ) {
        if (StringUtil.isEmpty(description))
            return expenseRepository.findAll(pageable);
        else
            return expenseRepository.findByDescription(description.toUpperCase(), pageable);
    }

    public void delete(Long id) {

        try {

            Expense obj = expenseRepository.getReferenceById(id);

            for (Installment i : obj.getInstallments()) {
                if (i.isPaid())
                    throw new BudgetBuddyException("Não é permitido excluír uma parcela que já está paga");
            }

            expenseRepository.findById(id).ifPresent(e -> expenseRepository.delete(e));

        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }

    }

    public Expense findById(final Long id) {
        Optional<Expense> obj = expenseRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Expense update(
            final Long id,
            final ExpenseUpdateDTO obj
    ) {
        try {
            Expense entity = expenseRepository.getReferenceById(id);
            updateData(entity, obj);
            return expenseRepository.save(entity);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(
            Expense entity,
            final ExpenseUpdateDTO obj
    ) {
        entity.setDescription(obj.getDescription());
    }
}
