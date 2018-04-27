package com.bankslip.web.validator;

import org.springframework.stereotype.Component;


import com.bankslip.exceptions.InvalidBankslipEntryException;
import com.bankslip.exceptions.BankslipBadRequestException;
import com.bankslip.web.vo.BankslipVO;

@Component
public interface IBankslipValidator {

    void validateEntryFields(final BankslipVO request) throws InvalidBankslipEntryException;

	void validateBankslipId(String id) throws BankslipBadRequestException;

}
