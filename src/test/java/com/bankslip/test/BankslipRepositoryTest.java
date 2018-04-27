package com.bankslip.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bankslip.entity.Bankslip;
import com.bankslip.init.BankslipApp;
import com.bankslip.repository.BankslipRepository;
import com.bankslip.types.Status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankslipApp.class)
public class BankslipRepositoryTest {

    @Autowired
    private BankslipRepository repository;

    private Random randomGenerator = new Random();

    private Bankslip createBankslip() {
        Bankslip b = new Bankslip();
        b.setCustomer("Customer" + randomGenerator.nextInt());
        b.setDueDate(getDatePlusDays(5));
        b.setStatus(Status.PENDING.name());
        b.setAssessment(randomGenerator.nextInt());
        return b;
    }

    private LocalDate getDatePlusDays(int days) {
        LocalDate localDate = LocalDate.now();
        return localDate.plusDays(days);
    }

    @Test
    public void createAndFind() {
        Bankslip reponse = repository.save(createBankslip());
        assertNotNull(reponse);
        List<Bankslip> bankslips = repository.findAll();
        assertFalse(bankslips.isEmpty());
    }
}