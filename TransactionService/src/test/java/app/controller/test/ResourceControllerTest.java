package app.controller.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import app.controller.ResourceController;
import app.model.Account;
import app.model.Transaction;

import app.service.TransferServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ResourceController.class, Transaction.class, Account.class,
		TransferServiceImpl.class })
@WebAppConfiguration
public class ResourceControllerTest {

	private MockMvc mockmvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() {
		mockmvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testGetAccountOverview() throws Exception {
		mockmvc.perform(get("/getAccountsAndBalances")).andExpect(status().isOk());
	}

	@Test
	public void testregisterAccount() throws Exception {
		mockmvc.perform(post("/registerAccount").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("{\"accountNumber\":\"1\", \"balance\":\"100.00\"}"))
				.andExpect(status().isOk());
	}
	@Test
	public void testtransferBalance() throws Exception {
		mockmvc.perform(post("/transferBalance").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("{\"srcAccountNumber\":\"2\","
						+ "\"destinationAccNumber\":\"1\",\"amount\":\"100\"}"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testtransferBalanceNegativeBalance() throws Exception {
		mockmvc.perform(post("/transferBalance").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("{\"srcAccountNumber\":\"2\","
						+ "\"destinationAccNumber\":\"1\",\"amount\":\"-100\"}"))
				.andExpect(status().isBadRequest());
	}
}
