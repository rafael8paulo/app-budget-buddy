package br.com.rpx.budgetbuddy.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "installment")
@NoArgsConstructor
public class Installment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int installmentNumber;
    private boolean isPaid;
    private LocalDate dueDate;
    private LocalDate payDate;
    private BigDecimal installmentValue;

    @ManyToOne
    @JoinColumn(name = "id_expense")
    private Expense expense;

    public Installment(
            final int installmentNumber,
            final LocalDate dueDate,
            final BigDecimal value,
            final Expense expense
    ) {
        this.dueDate = dueDate;
        this.installmentValue = value;
        this.isPaid = false;
        this.payDate = null;
        this.installmentNumber = installmentNumber;
        this.expense = expense;
    }

    public Installment(
            final boolean isPaid,
            final LocalDate payDate
    ) {
        this.isPaid = isPaid;
        this.payDate = payDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getInstallmentValue() {
        return installmentValue;
    }

    public void setInstallmentValue(BigDecimal installmentValue) {
        this.installmentValue = installmentValue;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public int getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(int installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Installment that = (Installment) o;
        return installmentNumber == that.installmentNumber && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, installmentNumber);
    }

    @Override
    public String toString() {
        return "Installment{" +
                "id=" + id +
                ", installmentNumber=" + installmentNumber +
                ", isPaid=" + isPaid +
                ", dueDate=" + dueDate +
                ", payDate=" + payDate +
                ", installmentValue=" + installmentValue +
                ", expense=" + expense +
                '}';
    }
}
