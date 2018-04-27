package com.bankslip.service;

import java.util.List;

import com.bankslip.exceptions.BankslipNotFoundException;
import com.bankslip.web.vo.BankslipVO;

public interface IBankslipService {

    BankslipVO save(BankslipVO vo);

    List<BankslipVO> list();

    BankslipVO find(String id) throws BankslipNotFoundException;

    BankslipVO pay(String id) throws BankslipNotFoundException;

    BankslipVO cancel(String id) throws BankslipNotFoundException;

}
