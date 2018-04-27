package com.bankslip.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.bankslip.constants.Constants;
import com.bankslip.exceptions.BankslipBadRequestException;
import com.bankslip.exceptions.BankslipNotFoundException;
import com.bankslip.exceptions.InvalidBankslipEntryException;
import com.bankslip.service.IBankslipService;
import com.bankslip.service.IMessage;
import com.bankslip.web.message.OutputMessage;
import com.bankslip.web.validator.IBankslipValidator;
import com.bankslip.web.vo.BankslipVO;

@RestController
@RequestMapping("/rest/bankslips")
public class BankslipController {

    private static final Logger logger = LoggerFactory.getLogger(BankslipController.class);

    @Autowired
    private IMessage messageService;

    @Autowired
    private IBankslipValidator bankslipValidator;

    @Autowired
    private IBankslipService bankslipService;

    @PostMapping
    public ResponseEntity<OutputMessage> create(@RequestBody BankslipVO request, UriComponentsBuilder ucBuilder)
            throws InvalidBankslipEntryException {
        OutputMessage output = new OutputMessage();
        bankslipValidator.validateEntryFields(request);
        BankslipVO bankslip = bankslipService.save(request);
        output.setObject(bankslip);
        output.setMessage(this.messageService.getMessage(Constants.MSG_BANKSLIP_CREATED));
        output.setStatusCode(HttpStatus.CREATED.value());
        logger.info("Bankslip created: {}", request);
        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<OutputMessage> get() {
        OutputMessage output = new OutputMessage();
        output.setObject(bankslipService.list());
        output.setMessage(this.messageService.getMessage(Constants.OK));
        output.setStatusCode(HttpStatus.OK.value());
        logger.info("Listing all bankslips");
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutputMessage> get(@PathVariable String id)
            throws BankslipBadRequestException, BankslipNotFoundException {
        OutputMessage output = new OutputMessage();
        bankslipValidator.validateBankslipId(id);
        output.setObject(bankslipService.find(id));
        output.setMessage(this.messageService.getMessage(Constants.OK));
        output.setStatusCode(HttpStatus.OK.value());        
        logger.info("Foudnd bankslip : {}", id);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<OutputMessage> pay(@PathVariable String id)
            throws BankslipBadRequestException, BankslipNotFoundException {
        OutputMessage output = new OutputMessage();
        bankslipValidator.validateBankslipId(id);
        BankslipVO response = bankslipService.pay(id);
        output.setObject(response);
        output.setMessage(this.messageService.getMessage(Constants.MSG_BANKSLIP_PAID));
        output.setStatusCode(HttpStatus.OK.value());
        logger.info("Bankslip paid: {}", id);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<OutputMessage> cancel(@PathVariable String id)
            throws BankslipBadRequestException, BankslipNotFoundException {
        OutputMessage output = new OutputMessage();
        bankslipValidator.validateBankslipId(id);
        BankslipVO response = bankslipService.cancel(id);
        output.setObject(response);
        output.setMessage(this.messageService.getMessage(Constants.MSG_BANKSLIP_CANCELED));
        output.setStatusCode(HttpStatus.OK.value());
        logger.info("Canceled bankslip: {}", id);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }
}
