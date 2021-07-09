package app.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import app.model.Account;
import app.model.Transaction;
import app.service.TransferService;

@EnableWebMvc
@RestController
public class ResourceController {

	@Autowired
	Transaction transaction;

	@Autowired
	Account account;

	@Autowired
	TransferService transferService;

	@RequestMapping("/getAccountsAndBalances")
	public Set<Account> getAccountOverview() {
		return transferService.getAccounts();
	}

	@RequestMapping(value = "/registerAccount", method = RequestMethod.POST)
	public ResponseEntity<String> updateBalance(@RequestBody Account account) {
		return transferService.addAccount(account);
	}

	@RequestMapping(value = "/transferBalance", method = RequestMethod.POST)
	public ResponseEntity<String> transferBalance(@RequestBody Transaction transaction) {
		if (transaction.getAmount() <= 0)
			return new ResponseEntity<String>(new String("Invalid Amount in transaction"), HttpStatus.BAD_REQUEST);
		else
			return transferService.transferBalance(transaction);
	}
}
