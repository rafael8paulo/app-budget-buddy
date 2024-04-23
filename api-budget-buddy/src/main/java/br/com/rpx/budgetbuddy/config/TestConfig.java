package br.com.rpx.budgetbuddy.config;

import br.com.rpx.budgetbuddy.repositories.ExpenseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    private ExpenseRepository expenseRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Rodando configuração do profile de test");

//        Set<Installment> installments = new HashSet<>();
//        Expense e = new Expense(BigDecimal.ONE, "Despesa de Teste", installments);
//        Installment installment = new Installment(LocalDate.now(), BigDecimal.ONE);
//        installment.setExpense(e);
//        installments.add(installment);
//
//        expenseRepository.save(e);

    }

    public TestConfig(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

}
