package com.bankslip.web.validator.impl;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankslip.constants.Constants;
import com.bankslip.exceptions.BankslipBadRequestException;
import com.bankslip.exceptions.InvalidBankslipEntryException;
import com.bankslip.service.IMessage;
import com.bankslip.web.message.MessageInfo;
import com.bankslip.web.validator.IBankslipValidator;
import com.bankslip.web.validator.AbstractValidatior;
import com.bankslip.web.vo.BankslipVO;

@Service
public class BankslipValidatorImpl extends AbstractValidatior implements IBankslipValidator {

    @Autowired
    private IMessage serviceMessage;

    @Override
    public void validateEntryFields(final BankslipVO request) throws InvalidBankslipEntryException {
        List<MessageInfo> messages = new ArrayList<>();
        validateRequiredFields(request, messages, this.serviceMessage);
        if (!messages.isEmpty()) {
            throw new InvalidBankslipEntryException(serviceMessage.mergeMessages(messages),
                    this.serviceMessage.getMessage(Constants.MSG_INVALID_BANKSLIP));
        }
    }
    
    @Override
	public void validateBankslipId(final String id) throws BankslipBadRequestException {
		if(!id.matches((Constants.VALID_UUID))) {
			throw new BankslipBadRequestException(serviceMessage.getMessage(Constants.MSG_BANKSLIP_INVALID_ID_PROVIDED));
		}
	}
}
