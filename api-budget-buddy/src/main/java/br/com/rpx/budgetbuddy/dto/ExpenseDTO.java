package br.com.rpx.budgetbuddy.dto;

import br.com.rpx.budgetbuddy.entities.Expense;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseDTO implements Serializable {

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime createDate;
    private BigDecimal value;
    private String description;
    private List<InstallmentDTO> installments;

    public ExpenseDTO(Expense obj) {
        setId(obj.getId());
        setCreateDate(obj.getCreateDate());
        setDescription(obj.getDescription());
        setValue(obj.getExpenseValue());
        setInstallments(obj.getInstallments().stream()
                .map(installment -> new InstallmentDTO(installment))
                .collect(Collectors.toList())
        );
    }
}
