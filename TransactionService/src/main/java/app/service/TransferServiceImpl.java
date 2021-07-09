package app.service;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import app.model.Account;
import app.model.Transaction;

@Service("TransferService")
public class TransferServiceImpl implements TransferService {

	private Set<Account> accounts;
	private ResponseEntity<String> response;

	public Set<Account> getAccounts() {
		return this.accounts;
	}

	public TransferServiceImpl() {
		super();
		this.accounts = new CopyOnWriteArraySet<Account>();
	}

	public ResponseEntity<String> addAccount(Account account) {
		if (this.getAccounts().add(account))
			return new ResponseEntity<String>(new String("Account registered successfully"), HttpStatus.OK);
		return new ResponseEntity<String>(new String("Account already registered."), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> transferBalance(Transaction transaction) {

		this.response = null;
		Optional<Account> acc = this.getAccounts().stream().filter(x -> {
			return (x.getAccountNumber().equals(transaction.getDestinationAccNumber())
					|| x.getAccountNumber().equals(transaction.getSrcAccountNumber()));
		}).reduce((x, y) -> {
			if (x.getAccountNumber().equals(transaction.getSrcAccountNumber())) {
				if (updateBalance(x, y, transaction.getAmount())) {
					this.response = new ResponseEntity<String>(
							new String("Transaction is successful.Revised Balance is follow:" + "Source " + x + "\n"
									+ "Destination " + y),
							HttpStatus.OK);
				} else {
					this.response = new ResponseEntity<String>(new String("Insufficient balance in " + x),
							HttpStatus.BAD_REQUEST);
				}
				return x;
			} else {
				if (updateBalance(y, x, transaction.getAmount())) {
					this.response = new ResponseEntity<String>(
							new String("Transaction is successful.Revised Balance is follow:" + "Source " + x + "\n"
									+ "Destination " + y),
							HttpStatus.OK);
				} else {
					this.response = new ResponseEntity<String>(new String("Insufficient balance in " + y),
							HttpStatus.BAD_REQUEST);
				}
				return y;
			}
		});

		if (acc.isEmpty()) {
			this.response = new ResponseEntity<String>(
					new String("Non registered accounts passed, please registerd both accounts first" + ""),
					HttpStatus.BAD_REQUEST);
		}
		if (acc.isPresent() && null == this.response) {
			this.response = new ResponseEntity<String>(new String("Only account number : "
					+ acc.get().getAccountNumber() + " is present for transaction.Please register missing account."),
					HttpStatus.BAD_REQUEST);
		}
		return this.response;
	}

	private Boolean checkSufficientBalance(Account srcAccount, Double amount) {
		if (srcAccount.getBalance() >= amount)
			return true;
		return false;
	}

	private Boolean updateBalance(Account srcAccount, Account destAccount, Double amount) {
		if (checkSufficientBalance(srcAccount, amount)) {
			destAccount.setBalance(destAccount.getBalance() + amount);
			srcAccount.setBalance(srcAccount.getBalance() - amount);
			return true;
		}
		return false;
	}
}
