package com.bankslip.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.bankslip.web.message.MessageInfo;

public class InvalidBankslipEntryException extends AbstractException {

	private static final long serialVersionUID = 1L;
	
    private final List<MessageInfo> listErros = new ArrayList<>();

    public InvalidBankslipEntryException(final String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }

    public InvalidBankslipEntryException(final List<MessageInfo> listErros, final String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
        addErrors(listErros);
    }

    public List<MessageInfo> getListErros() {
        return listErros;
    }

    public void addErrors(final List<MessageInfo> errors) {
        listErros.addAll(errors);
    }

    public void addError(final MessageInfo error) {
        listErros.add(error);
    }

}
