package com.bankslip.exceptions;

import org.springframework.http.HttpStatus;

public class BankslipBadRequestException extends AbstractException {

	private static final long serialVersionUID = 1L;

    public BankslipBadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
