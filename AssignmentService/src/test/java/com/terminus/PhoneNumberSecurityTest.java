package com.terminus;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.terminus.config.SpringSecurityConfig;
import com.terminus.entity.PhoneNumberEntity;
import com.terminus.repository.PhoneNumberRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = SpringSecurityConfig.class)
@AutoConfigureMockMvc
public class PhoneNumberSecurityTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PhoneNumberRepository PhoneNumberRepository;

	List<PhoneNumberEntity> phoneNumber;

	@Before
	public void init() {
		phoneNumber = new ArrayList<PhoneNumberEntity>();
		PhoneNumberEntity phoneNumber1 = new PhoneNumberEntity(101, 1, 1234567890, "InActive");
		phoneNumber.add(phoneNumber1);
	}

	@WithMockUser("USER")
	@Test
	public void getPhoneNumberForCustomerSuccess() throws Exception {

		when(PhoneNumberRepository.findByCustomerID(1)).thenReturn(phoneNumber);

		mockMvc.perform(get("/getPhoneNumberEntity/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].customerID", is(1))).andExpect(jsonPath("$[0].phoneNumber", is(1234567890)))
				.andExpect(jsonPath("$[0].status", is("InActive")));
	}

	@Test
	public void getPhoneNumberForCustomerUnauthorized() throws Exception {
		mockMvc.perform(get("/getPhoneNumberEntity/1")).andDo(print()).andExpect(status().isUnauthorized());
	}

	@WithMockUser("USER")
	@Test
	public void getAllPhoneNumberEntitySuccess() throws Exception {

		PhoneNumberEntity phoneNumber2 = new PhoneNumberEntity(101, 2, 1234567899, "Active");
		phoneNumber.add(phoneNumber2);

		when(PhoneNumberRepository.findAll()).thenReturn(phoneNumber);

		mockMvc.perform(get("/getAllPhoneNumberEntity")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[*].customerID").value(Lists.newArrayList(1, 2)))
				.andExpect(jsonPath("$[*].phoneNumber").value(Lists.newArrayList(1234567890, 1234567899)))
				.andExpect(jsonPath("$[*].status").value(Lists.newArrayList("InActive", "Active")));
	}

	@Test
	public void getAllPhoneNumberEntityUnauthorized() throws Exception {
		mockMvc.perform(get("/getAllPhoneNumberEntity")).andDo(print()).andExpect(status().isUnauthorized());
	}

	@WithMockUser("ADMIN")
	@Test
	public void activatePhoneNumberEntitySuccess() throws Exception {

		when(PhoneNumberRepository.updateStatus("Active", 1, 1234567890)).thenReturn(1);

		mockMvc.perform(patch("/activatePhoneNumberEntity"))
	      .andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].customerID", is(1))).andExpect(jsonPath("$[0].phoneNumber", is(1234567890)))
				.andExpect(jsonPath("$[0].status", is("Active")));
	}

	@WithMockUser("USER")
	@Test
	public void activatePhoneNumberEntity_Unauthorized() throws Exception {
		mockMvc.perform(patch("/activatePhoneNumberEntity")).andDo(print()).andExpect(status().isForbidden());
	}
}
