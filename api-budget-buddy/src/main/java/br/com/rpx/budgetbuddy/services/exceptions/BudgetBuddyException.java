package br.com.rpx.budgetbuddy.services.exceptions;

import java.io.Serializable;

public class BudgetBuddyException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public BudgetBuddyException(String msg){
        super(msg);
    }

}
