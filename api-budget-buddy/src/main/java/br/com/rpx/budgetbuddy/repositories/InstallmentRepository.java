package br.com.rpx.budgetbuddy.repositories;

import br.com.rpx.budgetbuddy.entities.Installment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface InstallmentRepository extends JpaRepository<Installment, Long> {

    @Query("select i from Installment i where i.dueDate between :dtStart and :dtStop ")
    List<Installment> getByDate(LocalDate dtStart, LocalDate dtStop);

}
