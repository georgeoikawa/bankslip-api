package com.bankslip.exceptions;

import org.springframework.http.HttpStatus;

public class BankslipNotFoundException extends AbstractException {

	private static final long serialVersionUID = 1L;

    public BankslipNotFoundException(final String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}
