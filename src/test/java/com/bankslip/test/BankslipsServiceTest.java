package com.bankslip.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bankslip.exceptions.BankslipNotFoundException;
import com.bankslip.init.BankslipApp;
import com.bankslip.service.IBankslipService;
import com.bankslip.types.Status;
import com.bankslip.util.CalculateBankslipAssessment;
import com.bankslip.web.vo.BankslipVO;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankslipApp.class)
public class BankslipsServiceTest {
	
	@Autowired
	private IBankslipService service;
	
	private Random random = new Random();
	
	private BankslipVO createBankslipExpired20days() {
		String dateFormated = getDateMinusDays(20);
		BankslipVO vo = createBankslip();
		vo.setDueDate(dateFormated);
		return vo;
	}

	private BankslipVO createBankslipExpired8days() {
		String dateFormated = getDateMinusDays(8);
		BankslipVO vo = createBankslip();
		vo.setDueDate(dateFormated);
		return vo;
	}
	
	private String getDateMinusDays(int days) {
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		localDate = localDate.minusDays(days);
		String dateFormated = localDate.format(formatter);
		return dateFormated;
	}
	
	private String getDatePlusDays(int days) {
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		localDate = localDate.plusDays(days);
		String dateFormated = localDate.format(formatter);
		return dateFormated;
	}
	
	private BankslipVO createBankslip() {
		BankslipVO b = new BankslipVO();
		b.setCustomer("Customer" + random.nextInt());
		b.setDueDate(getDatePlusDays(3));
		b.setStatus(Status.PENDING.name());
		b.setTotalInCents(String.valueOf(random.nextInt(Integer.MAX_VALUE) + 1));		
		return b;
	}
	
	@Test
	public void save() {
		BankslipVO response = service.save(createBankslip());
		assertNotNull(response);
		assertNotNull(response.getId());
	}

	
	@Test
	public void saveAndApplyAssessmentOfHalfPercent() throws BankslipNotFoundException {
		BankslipVO request = createBankslipExpired8days();
		BankslipVO saveResponse = service.save(request);
		assertNotNull(saveResponse);
		assertNotNull(saveResponse.getId());
		assertEquals(request.getTotalInCents(), saveResponse.getTotalInCents());
		BankslipVO response = service.find(saveResponse.getId());
		assertNotNull(response.getAssessment());
		LocalDate dueDate = getLocalDateDueDate(request);
        Integer assessmentResult = CalculateBankslipAssessment.calculateAssessment(dueDate, Integer.parseInt(request.getTotalInCents()));
		assertEquals(String.valueOf(assessmentResult), response.getAssessment());
	}

	@Test
	public void saveAndApplyAssessmentOfOnePercent() throws BankslipNotFoundException {
		BankslipVO request = createBankslipExpired20days();
		BankslipVO saveResponse = service.save(request);
		assertNotNull(saveResponse);
		assertNotNull(saveResponse.getId());
		assertEquals(request.getTotalInCents(), saveResponse.getTotalInCents());
		BankslipVO response = service.find(saveResponse.getId());
		assertNotNull(response.getAssessment());
		LocalDate dueDate = getLocalDateDueDate(request);
        Integer assessmentResult = CalculateBankslipAssessment.calculateAssessment(dueDate, Integer.parseInt(request.getTotalInCents()));
		assertEquals(String.valueOf(assessmentResult), response.getAssessment());
	}
	
	private LocalDate getLocalDateDueDate(BankslipVO request) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dueDate = LocalDate.parse(request.getDueDate(), formatter);
		return dueDate;
	}
}
