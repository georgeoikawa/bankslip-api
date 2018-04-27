package com.bankslip.web.message;

import java.io.Serializable;

public class MessageInfo implements Serializable {

	private static final long serialVersionUID = 1L;
    private String code;
    private String message;

    public MessageInfo() {}

    public MessageInfo(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
