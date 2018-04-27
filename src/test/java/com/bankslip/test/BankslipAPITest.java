package com.bankslip.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.bankslip.init.BankslipApp;
import com.bankslip.types.Status;
import com.bankslip.web.message.OutputMessage;
import com.bankslip.web.vo.BankslipVO;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { BankslipApp.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
public class BankslipAPITest {

	private Random random = new Random();

	private static final String BASE_URL_API = "/rest/bankslips";

	private ObjectMapper objectMaper = new ObjectMapper();

	@Autowired
	private TestRestTemplate restTemplate;

	private BankslipVO createBankslip() {
		BankslipVO b = new BankslipVO();
		b.setCustomer("Customer" + random.nextInt());
		b.setDueDate(getDateWithPlusDays(3));
		b.setStatus(Status.PENDING.name());
		b.setTotalInCents(String.valueOf(random.nextInt()));
		return b;
	}

	private String getDateWithPlusDays(int days) {
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		localDate = localDate.plusDays(days);
		String dateFormated = localDate.format(formatter);
		return dateFormated;
	}

	@Test
	public void saveBankslip() {
		ResponseEntity<OutputMessage> responseEntity = restTemplate.postForEntity(BASE_URL_API,
				createBankslip(), OutputMessage.class);
		OutputMessage output = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(output.getObject());
		assertNull(output.getListError());
		BankslipVO responseObject = objectMaper.convertValue(output.getObject(), BankslipVO.class);
		assertEquals(Status.PENDING.name(), responseObject.getStatus());
	}

	@Test
	public void tryCreateBankslipNotProvided() {
		ResponseEntity<OutputMessage> responseEntity = restTemplate.postForEntity(BASE_URL_API , null,
				OutputMessage.class);
		OutputMessage output = responseEntity.getBody();
		assertNull(output.getObject());
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	public void tryListAll() {
		ResponseEntity<OutputMessage> responseEntity = restTemplate.getForEntity(BASE_URL_API,
				OutputMessage.class);
		OutputMessage output = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(output.getObject());
		assertEquals("Ok", output.getMessage());
	}

	@Test
	public void findBankslipInvalidId() {
		ResponseEntity<OutputMessage> responseEntity = restTemplate
				.getForEntity(BASE_URL_API + "/12345678-12a1-3sd3-1c11-x121", OutputMessage.class);
		OutputMessage output = responseEntity.getBody();
		assertEquals("Invalid id provided - it must be a valid UUID", output.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNull(output.getObject());
	}

	@Test
	public void findBankslipWithNonExistent() {
		ResponseEntity<OutputMessage> responseEntity = restTemplate
				.getForEntity(BASE_URL_API + "/56be6deb-e7ad-4171-8b2a-e4e43497addb", OutputMessage.class);
		OutputMessage output = responseEntity.getBody();
		assertEquals("Bankslip not found with the specified id", output.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNull(output.getObject());
	}

	@Test
	public void payBankslip() {
		ResponseEntity<OutputMessage> postResponseEntity = restTemplate.postForEntity(BASE_URL_API,
				createBankslip(), OutputMessage.class);
		OutputMessage postOutput = postResponseEntity.getBody();
		BankslipVO postResponseObject = objectMaper.convertValue(postOutput.getObject(), BankslipVO.class);
		ResponseEntity<OutputMessage> responseEntity = restTemplate.exchange(
				BASE_URL_API + "/" + postResponseObject.getId() + "/pay", HttpMethod.PUT, null, OutputMessage.class);
		OutputMessage output = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(output.getObject());
		assertEquals("Bankslip paid", output.getMessage());
		BankslipVO responseObject = objectMaper.convertValue(output.getObject(), BankslipVO.class);
		assertEquals(Status.PAID.name(), responseObject.getStatus());
	}

	@Test
	public void payBankslipWithInvalidId() {
		ResponseEntity<OutputMessage> responseEntity = restTemplate.exchange(
				BASE_URL_API + "/56be6deb-e7ad-4171-8b2a-e4e43497addb/pay", HttpMethod.PUT, null, OutputMessage.class);
		OutputMessage output = responseEntity.getBody();
		assertEquals("Bankslip not found with the specified id", output.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNull(output.getObject());
	}

	@Test
	public void cancelBankslip() {
		ResponseEntity<OutputMessage> postResponseEntity = restTemplate.postForEntity(BASE_URL_API,
				createBankslip(), OutputMessage.class);
		OutputMessage postOutput = postResponseEntity.getBody();
		BankslipVO postResponseObject = objectMaper.convertValue(postOutput.getObject(), BankslipVO.class);
		ResponseEntity<OutputMessage> responseEntity = restTemplate.exchange(
				BASE_URL_API + "/" + postResponseObject.getId() + "/cancel", HttpMethod.DELETE, null,
				OutputMessage.class);
		OutputMessage output = responseEntity.getBody();
		assertEquals("Bankslip canceled", output.getMessage());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(output.getObject());
		BankslipVO responseObject = objectMaper.convertValue(output.getObject(), BankslipVO.class);
		assertEquals(Status.CANCELED.name(), responseObject.getStatus());
	}
	
}
