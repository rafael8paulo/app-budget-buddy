package br.com.rpx.budgetbuddy.services;

import br.com.rpx.budgetbuddy.dto.GainDTO;
import br.com.rpx.budgetbuddy.entities.Expense;
import br.com.rpx.budgetbuddy.entities.Gain;
import br.com.rpx.budgetbuddy.entities.Installment;
import br.com.rpx.budgetbuddy.repositories.GainRepository;
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
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class GainService {

    private GainRepository repository;

    public GainService(GainRepository repository) {
        this.repository = repository;
    }

    public Gain create(GainDTO dto) {
        dto.validation();
        try {
            Gain entity = new Gain(dto);
            return repository.save(entity);
        } catch (Exception e) {
            throw new BudgetBuddyException("Ocorreu um erro ao tentar registar os ganhos");
        }

    }

    public List<Gain> list(
            final LocalDate dtStart,
            final LocalDate dtStop
    ) {

        if (dtStart == null || dtStop == null)
            return repository.findAll();
        else if (!dtStart.isAfter(dtStop))
            return repository.listByDate(dtStart, dtStop);
        else
            throw new BudgetBuddyException("A data inicial deve ser maior que a final");

    }


    public BigDecimal totalWinnings(
            final LocalDate dtStart,
            final LocalDate dtStop
    ) {
        try {
            if (!dtStart.isAfter(dtStop)){
                BigDecimal value = repository.totalWinnings(dtStart, dtStop);
                return value == null ? BigDecimal.ZERO : value;
            } else
                throw new BudgetBuddyException("A data inicial deve ser maior que a final");
        }catch (Exception e) {
            throw new BudgetBuddyException("Ocorreu um erro ao recuperar os ganhos");
        }
    }

    public Page<Gain> findAll(
            final String description,
            final Pageable pageable
    ) {
        if(StringUtil.isEmpty(description))
            return repository.findAll(pageable);
        else
            return repository.findByDescription(description.toUpperCase(), pageable);
    }

    public void delete(final Long id) {

        final LocalDate dtNow = LocalDate.now();

        try {

            final Gain gain = repository.getReferenceById(id);
            long dias = ChronoUnit.DAYS.between(gain.getCreateDate(), dtNow);

            if(dias > 7)
                throw new BudgetBuddyException("Não é permitido excluír ganhos registrados a mais de 7 dias");

            repository.findById(id).ifPresent(g -> repository.delete(g));

        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }

    }

    public Gain findById(final Long id) {
        Optional<Gain> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Gain update(
            final Long id,
            final GainDTO obj
    ) {
        try {
            Gain entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(
            Gain entity,
            final GainDTO obj
    ) {
        entity.setDescription(obj.getDescription());
        entity.setGainValue(obj.getValue());
    }
}
