package br.com.rpx.budgetbuddy.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "expense")
@NoArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createDate;
    private BigDecimal expenseValue;
    private String description;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Installment> installments;

    public Expense(BigDecimal value, String description) {
        this.expenseValue = value;
        this.description = description;
        this.createDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getExpenseValue() {
        return expenseValue;
    }

    public void setExpenseValue(BigDecimal expenseValue) {
        this.expenseValue = expenseValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Installment> getInstallments() {
        return installments;
    }

    public void setInstallments(Set<Installment> installments) {
        this.installments = installments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Objects.equals(id, expense.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", expenseValue=" + expenseValue +
                ", description='" + description + '\'' +
                ", installments=" + installments +
                '}';
    }
}
