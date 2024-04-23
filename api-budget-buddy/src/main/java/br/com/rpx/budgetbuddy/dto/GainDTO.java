package br.com.rpx.budgetbuddy.dto;

import br.com.rpx.budgetbuddy.entities.Gain;
import br.com.rpx.budgetbuddy.services.exceptions.BudgetBuddyException;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class GainDTO {

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createDate;
    private String description;
    private BigDecimal value;

    public GainDTO(Gain obj) {
        setDescription(obj.getDescription());
        setValue(obj.getGainValue());
        setId(obj.getId());
        setCreateDate(obj.getCreateDate());
    }

    public void validation() {

        if (value.compareTo(BigDecimal.ZERO) < 0)
            throw new BudgetBuddyException("Valor da despesa  não é invalido");
        else if (description.length() == 0)
            throw new BudgetBuddyException("Descrição não é valida");
        else if (description.length() > 60)
            throw new BudgetBuddyException("A descrição deve ter apenas 60 caracteres");

    }

}
