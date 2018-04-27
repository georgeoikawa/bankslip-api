package com.bankslip.util.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.bankslip.entity.Bankslip;
import com.bankslip.web.vo.BankslipVO;

public class RequestBankslipDataTypeConverter {

    public Bankslip toEntity(BankslipVO vo) {
        if (vo == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(vo.getDueDate(), formatter);

        return new Bankslip(vo.getCustomer(), localDate, vo.getStatus(), Integer.valueOf(vo.getTotalInCents()));
    }
}
