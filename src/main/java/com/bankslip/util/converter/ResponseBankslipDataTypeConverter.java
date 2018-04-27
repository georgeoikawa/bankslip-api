package com.bankslip.util.converter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.bankslip.entity.Bankslip;
import com.bankslip.web.vo.BankslipVO;

public class ResponseBankslipDataTypeConverter {

    public BankslipVO toEntity(Bankslip entity) {
        if (entity == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dueDate = entity.getDueDate().format(formatter);

        return new BankslipVO(entity.getId(), entity.getCustomer(), dueDate, entity.getStatus(),
                String.valueOf(entity.getTotalInCents()), String.valueOf(entity.getAssessment()));
    }

    public List<BankslipVO> toEntityList(List<Bankslip> entityList) {
        List<BankslipVO> bankslips = new ArrayList<>();

        if (entityList != null && !entityList.isEmpty()) {
            for (Bankslip vo : entityList) {
                bankslips.add(toEntity(vo));
            }
        }

        return bankslips;
    }

}
