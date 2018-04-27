package com.bankslip.web.message;

import java.io.Serializable;
import java.util.List;

public class OutputMessage implements Serializable {

	private static final long serialVersionUID = 1L;
    private Integer statusCode;
    private String message;

    private List<MessageInfo> errorList;

    private Object object;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer status) {
        this.statusCode = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MessageInfo> getListError() {
        return errorList;
    }

    public void setErrorList(List<MessageInfo> errorList) {
        this.errorList = errorList;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
