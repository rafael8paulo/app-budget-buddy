package br.com.rpx.budgetbuddy.dto;

import br.com.rpx.budgetbuddy.services.exceptions.BudgetBuddyException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseUpdateDTO {

    private String description;

    public void validation() {

        if (description.length() == 0)
            throw new BudgetBuddyException("Descrição não é valida");
        else if (description.length() > 60)
            throw new BudgetBuddyException("A descrição deve ter apenas 60 caracteres");
    }

}
