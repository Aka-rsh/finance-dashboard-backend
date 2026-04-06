package com.finance.dashboard.exception;

public class FinancialOperationException extends RuntimeException {
    public FinancialOperationException(String message) {
        super(message);
    }
}