package com.terminus;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.terminus.controller.PhoneNumberController;
import com.terminus.entity.PhoneNumberEntity;
import com.terminus.repository.PhoneNumberRepository;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnit4.class)
@ExtendWith(SpringExtension.class)
public class PhoneNumberControllerTest {

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@InjectMocks
	PhoneNumberController employeeController;

	@Mock
	private PhoneNumberRepository repo;

	@Test
	public void getAllPhoneNumberEntity() throws Exception {

		PhoneNumberEntity employee1 = new PhoneNumberEntity(1, 1111, 1234567890, "Active");
		PhoneNumberEntity employee2 = new PhoneNumberEntity(2, 2222, 1234567890, "Active");
		List<PhoneNumberEntity> employees = new ArrayList<PhoneNumberEntity>();
		employees.add(employee1);
		employees.add(employee2);

		when(repo.findAll()).thenReturn(employees);

		// when
		List<PhoneNumberEntity> result = employeeController.getAllPhoneNumbers();

		// then
		Assert.assertEquals(2, result.size());

		Assert.assertTrue(result.get(0).getPhoneId() == employee1.getPhoneId());

		Assert.assertTrue(result.get(1).getPhoneId() == employee2.getPhoneId());
	}

	@Test
	public void getParticularPhoneNumberEntity() throws Exception {

		PhoneNumberEntity employee1 = new PhoneNumberEntity(1, 1111, 1234567890, "Active");
		PhoneNumberEntity employee2 = new PhoneNumberEntity(2, 1111, 1234567890, "Active");
		PhoneNumberEntity employee3 = new PhoneNumberEntity(3, 2222, 1234567890, "Active");
		List<PhoneNumberEntity> employees = new ArrayList<PhoneNumberEntity>();
		employees.add(employee1);
		employees.add(employee2);
		// employees.add(employee3);

		when(repo.findByCustomerID(1111)).thenReturn(employees);

		// when
		List<PhoneNumberEntity> result = employeeController.getAllPhoneNumbers(1111);

		// then
		Assert.assertEquals(2, result.size());

		Assert.assertTrue(result.get(0).getPhoneId() == employee1.getPhoneId());

		Assert.assertTrue(result.get(1).getPhoneId() == employee2.getPhoneId());
	}

	@Test
	public void activatePhoneNumberEntity() throws Exception {

		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		// when(repo.save(any(PhoneNumberEntity.class))).thenReturn(true);
		PhoneNumberEntity pn = new PhoneNumberEntity(1, 1111, 1234567890, "Active");
		when(repo.updateStatus(pn.getStatus(), pn.getCustomerID(), pn.getPhoneNumber())).thenReturn(1);
		ResponseEntity<PhoneNumberEntity> responseEntity = employeeController.updatePhoneNumber(pn);

		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}
}
