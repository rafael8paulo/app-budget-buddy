package br.com.rpx.budgetbuddy.dto;

import br.com.rpx.budgetbuddy.entities.Installment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class InstallmentDTO {

    private Long id;
    private int installmentNumber;
    private boolean isPaid;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate payDate;
    private BigDecimal value;
    private ExpenseDTO expense;

    public InstallmentDTO(Installment obj) {
        setId(obj.getId());
        setInstallmentNumber(obj.getInstallmentNumber());
        setPaid(obj.isPaid());
        setValue(obj.getInstallmentValue());
        setPayDate(obj.getPayDate());
        setDueDate(obj.getDueDate());
        setExpense(new ExpenseDTO());
        expense.setDescription(obj.getExpense().getDescription());
    }
}
