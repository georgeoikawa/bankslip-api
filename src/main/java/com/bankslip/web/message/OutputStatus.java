package com.bankslip.web.message;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

public class OutputStatus implements Serializable {

	private static final long serialVersionUID = 1L;

    private Integer httpStatus;

    private String message;

    public OutputStatus(HttpStatus status, String message) {
        this.httpStatus = status.value();
        this.message = message;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
