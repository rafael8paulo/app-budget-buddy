package br.com.rpx.budgetbuddy.repositories;

import br.com.rpx.budgetbuddy.entities.Gain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface GainRepository extends JpaRepository<Gain, Long> {
    @Query("select g from Gain g where g.createDate between :dtStart and :dtStop")
    List<Gain> listByDate(LocalDate dtStart, LocalDate dtStop);
    @Query("select sum(g.gainValue) from Gain g where g.createDate between :dtStart and :dtStop")
    BigDecimal totalWinnings(final LocalDate dtStart, final LocalDate dtStop);
    @Query("select g from Gain g where upper(g.description) like %:description% order by g.description asc")
    Page<Gain> findByDescription(String description, Pageable pageable);
}
