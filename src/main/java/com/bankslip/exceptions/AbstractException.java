package com.bankslip.exceptions;

import org.springframework.http.HttpStatus;

public abstract class AbstractException extends Exception {

	private static final long serialVersionUID = 1L;
	
	protected HttpStatus httpStatus;

    public AbstractException(final HttpStatus status, final String message) {
        super(message);
        this.httpStatus = status;
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }

    public void setStatus(HttpStatus status) {
        this.httpStatus = status;
    }
}
