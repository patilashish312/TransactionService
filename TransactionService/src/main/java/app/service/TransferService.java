package app.service;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import app.model.Account;
import app.model.Transaction;


public interface TransferService {

	public ResponseEntity<String> addAccount(Account account);
	
	public ResponseEntity<String> transferBalance(Transaction transaction);
	public Set<Account> getAccounts();
	
}
