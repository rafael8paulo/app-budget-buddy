package br.com.rpx.budgetbuddy.dto;

import br.com.rpx.budgetbuddy.services.exceptions.BudgetBuddyException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseCreateDTO {

    private String description;
    private BigDecimal value;
    private int qtyInstallment;
    private int dueDay;

    public void validation() {

        if (qtyInstallment < 0)
            throw new BudgetBuddyException("Numero de parcelas não é valido");
        else if (value.compareTo(BigDecimal.ZERO) < 0)
            throw new BudgetBuddyException("Valor da despesa  não é invalido");
        else if (dueDay > 28 || dueDay < 1)
            throw new BudgetBuddyException("Dia de vencimento não é valido");
        else if (description.length() == 0)
            throw new BudgetBuddyException("Descrição não é valida");
        else if (description.length() > 60)
            throw new BudgetBuddyException("A descrição deve ter apenas 60 caracteres");
        else if (qtyInstallment == 0)
            setQtyInstallment(1);

    }

}
