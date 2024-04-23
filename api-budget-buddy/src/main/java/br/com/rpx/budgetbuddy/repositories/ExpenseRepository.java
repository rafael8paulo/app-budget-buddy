package br.com.rpx.budgetbuddy.repositories;

import br.com.rpx.budgetbuddy.entities.Expense;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("select sum(i.installmentValue) from Expense e " +
            "join e.installments i where i.dueDate between :dtStart and :dtStop")
    BigDecimal getTotalExpenseAmount(LocalDate dtStart, LocalDate dtStop);

    Page<Expense> findAll(Pageable pageable);
    @Query("select e from Expense e where upper(e.description) like %:description% order by e.description asc ")
    Page<Expense> findByDescription(String description, Pageable pageable);
}
