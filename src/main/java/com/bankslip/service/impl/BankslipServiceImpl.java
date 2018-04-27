package com.bankslip.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bankslip.constants.Constants;
import com.bankslip.entity.Bankslip;
import com.bankslip.exceptions.BankslipNotFoundException;
import com.bankslip.repository.BankslipRepository;
import com.bankslip.service.IBankslipService;
import com.bankslip.service.IMessage;
import com.bankslip.types.Status;
import com.bankslip.util.CalculateBankslipAssessment;
import com.bankslip.util.converter.RequestBankslipDataTypeConverter;
import com.bankslip.util.converter.ResponseBankslipDataTypeConverter;
import com.bankslip.web.vo.BankslipVO;

@Component
public class BankslipServiceImpl implements IBankslipService {

    public static final Logger logger = LoggerFactory.getLogger(BankslipServiceImpl.class);

    @Autowired
    private BankslipRepository bankslipRepository;

    @Autowired
    private IMessage messageService;

    private RequestBankslipDataTypeConverter bankslipIn = new RequestBankslipDataTypeConverter();

    private ResponseBankslipDataTypeConverter bankslipOut = new ResponseBankslipDataTypeConverter();

    @Override
    public BankslipVO save(final BankslipVO vo) {
        Bankslip entity = bankslipIn.toEntity(vo);
        entity = bankslipRepository.save(entity);
        BankslipVO voOut = bankslipOut.toEntity(entity);
        logger.info("Bankslip persisted {}", voOut);
        return voOut;
    }

    @Override
    public List<BankslipVO> list() {
        List<Bankslip> bankslipList = bankslipRepository.findAll();
        List<BankslipVO> banksliVoList = bankslipOut.toEntityList(bankslipList);
        logger.info("Bankslip listed {}", banksliVoList);
        return banksliVoList;
    }

    @Override
    public BankslipVO find(String id) throws BankslipNotFoundException {
        Bankslip bankslip = findEntity(id);
        int calculateAssessment = CalculateBankslipAssessment.calculateAssessment(bankslip.getDueDate(), bankslip.getTotalInCents());
        bankslip.setAssessment(calculateAssessment);
        BankslipVO vo = bankslipOut.toEntity(bankslip);
        logger.info("Bankslip found {}", vo);
        return vo;
    }

    @Override
    public BankslipVO pay(String id) throws BankslipNotFoundException {
        Bankslip bankslip = findEntity(id);
        changeStatus(bankslip, Status.PAID);
        BankslipVO vo = bankslipOut.toEntity(bankslip);
        logger.info("Bankslip payed {}", vo);
        return vo;
    }

    @Override
    public BankslipVO cancel(String id) throws BankslipNotFoundException {
        Bankslip bankslip = findEntity(id);
        changeStatus(bankslip, Status.CANCELED);
        BankslipVO vo = bankslipOut.toEntity(bankslip);
        logger.info("Bankslip canceled {}", vo);
        return vo;
    }

    private void changeStatus(final Bankslip bankslip, final Status status) {
        bankslip.setStatus(status.name());
        bankslipRepository.save(bankslip);
    }

    private Bankslip findEntity(String id) throws BankslipNotFoundException {
        Optional<Bankslip> entity = bankslipRepository.findById(id);
        if (!entity.isPresent()) {
            throw new BankslipNotFoundException(this.messageService.getMessage(Constants.MSG_BANKSLIP_NOT_FOUND_SPECIFIED_ID));
        }
        return entity.get();
    }
}