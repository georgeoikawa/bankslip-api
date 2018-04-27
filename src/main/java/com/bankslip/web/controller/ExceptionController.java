package com.bankslip.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bankslip.constants.Constants;
import com.bankslip.exceptions.AbstractException;
import com.bankslip.exceptions.BankslipBadRequestException;
import com.bankslip.exceptions.BankslipNotFoundException;
import com.bankslip.exceptions.InvalidBankslipEntryException;
import com.bankslip.service.IMessage;
import com.bankslip.web.message.OutputMessage;

@ControllerAdvice
public class ExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @Autowired
    private IMessage messageService;

    @ExceptionHandler(BankslipBadRequestException.class)
    public ResponseEntity<OutputMessage> exceptionHandler(BankslipBadRequestException ex) {
        return getResponseEntity(ex);
    }

    @ExceptionHandler(InvalidBankslipEntryException.class)
    public ResponseEntity<OutputMessage> exceptionHandler(InvalidBankslipEntryException ex) {
        logger.error(ex.getMessage(), ex);
        OutputMessage output = getStandardOutput(ex);
        output.setErrorList(ex.getListErros());
        return new ResponseEntity<>(output, ex.getStatus());
    }

    @ExceptionHandler(BankslipNotFoundException.class)
    public ResponseEntity<OutputMessage> exceptionHandler(BankslipNotFoundException ex) {
        return getResponseEntity(ex);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<OutputMessage> exceptionHandler(HttpMessageNotReadableException ex) {
        logger.error(ex.getMessage(), ex);
        return getOutputForBadRequestNotFound();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<OutputMessage> exceptionHandler(HttpMediaTypeNotSupportedException ex) {
        logger.error(ex.getMessage(), ex);
        return getOutputForBadRequestNotFound();
    }

    private ResponseEntity<OutputMessage> getOutputForBadRequestNotFound() {
        OutputMessage output = new OutputMessage();
        output.setStatusCode(HttpStatus.BAD_REQUEST.value());
        output.setMessage(messageService.getMessage(Constants.MSG_BANKSLIP_NOT_PROVIDED));
        return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<OutputMessage> exceptionHandler(Exception ex) {
        logger.error(ex.getMessage(), ex);
        OutputMessage output = new OutputMessage();
        output.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        output.setMessage(ex.getMessage());
        return new ResponseEntity<>(output, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<OutputMessage> getResponseEntity(AbstractException ex) {
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(getStandardOutput(ex), ex.getStatus());
    }

    private OutputMessage getStandardOutput(AbstractException ex) {
        OutputMessage output = new OutputMessage();
        output.setStatusCode(ex.getStatus().value());
        output.setMessage(ex.getMessage());
        return output;
    }
}
