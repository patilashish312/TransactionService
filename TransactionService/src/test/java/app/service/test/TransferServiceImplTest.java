package app.service.test;

import static org.junit.Assert.*;
import app.model.*;
import app.service.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class TransferServiceImplTest {

	@Test
	public void testGetAccountsNotPresents() {
		TransferServiceImpl impl = new TransferServiceImpl();
		Assert.assertTrue(impl.getAccounts().isEmpty());
	}

	@Test
	public void testGetAccountsPresents() {
		TransferServiceImpl impl = new TransferServiceImpl();
		impl.getAccounts().addAll(accountPopulator());
		Assert.assertFalse(impl.getAccounts().isEmpty());
	}

	@Test
	public void testAddAccountCheck() {
		TransferServiceImpl impl = new TransferServiceImpl();
		Account account1 = new Account();
		account1.setAccountNumber(Long.valueOf(1));
		account1.setBalance(Double.valueOf(10.0));
		impl.addAccount(account1);
		Account account2 = new Account();
		account2.setAccountNumber(Long.valueOf(1));
		account2.setBalance(Double.valueOf(10.0));
		Assert.assertEquals(impl.addAccount(account2).getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testTransferBalanceSuccessful() {
		TransferServiceImpl impl = new TransferServiceImpl();
		impl.getAccounts().addAll(accountPopulator());
		Transaction transaction = new Transaction();
		transaction.setAmount(Double.valueOf(10.0));
		transaction.setSrcAccountNumber(Long.valueOf(1));
		transaction.setDestinationAccNumber(Long.valueOf(2));
		Assert.assertEquals(impl.transferBalance(transaction).getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testTransferBalanceSameAccount() {
		TransferServiceImpl impl = new TransferServiceImpl();
		impl.getAccounts().addAll(accountPopulator());
		Transaction transaction = new Transaction();
		transaction.setAmount(Double.valueOf(10.0));
		transaction.setSrcAccountNumber(Long.valueOf(1));
		transaction.setDestinationAccNumber(Long.valueOf(1));
		Assert.assertEquals(impl.transferBalance(transaction).getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testTransferBalanceOneAcctNotPresent() {
		TransferServiceImpl impl = new TransferServiceImpl();
		impl.getAccounts().addAll(accountPopulator());
		Transaction transaction = new Transaction();
		transaction.setAmount(Double.valueOf(10.0));
		transaction.setSrcAccountNumber(Long.valueOf(1));
		transaction.setDestinationAccNumber(Long.valueOf(8));
		Assert.assertEquals(impl.transferBalance(transaction).getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testTransferBalanceBothAcctsNotPresent() {
		TransferServiceImpl impl = new TransferServiceImpl();
		impl.getAccounts().addAll(accountPopulator());
		Transaction transaction = new Transaction();
		transaction.setAmount(Double.valueOf(10.0));
		transaction.setSrcAccountNumber(Long.valueOf(7));
		transaction.setDestinationAccNumber(Long.valueOf(8));
		Assert.assertEquals(impl.transferBalance(transaction).getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testTransferBalanceInsufficientBal() {
		TransferServiceImpl impl = new TransferServiceImpl();
		impl.getAccounts().addAll(accountPopulator());
		Transaction transaction = new Transaction();
		transaction.setAmount(Double.valueOf(100));
		transaction.setSrcAccountNumber(Long.valueOf(1));
		transaction.setDestinationAccNumber(Long.valueOf(8));
		Assert.assertEquals(impl.transferBalance(transaction).getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testTransferBalanceAmountChecks() {
		TransferServiceImpl impl = new TransferServiceImpl();
		impl.getAccounts().addAll(accountPopulator());
		Transaction transaction = new Transaction();
		transaction.setAmount(Double.valueOf(10.0));
		transaction.setSrcAccountNumber(Long.valueOf(1));
		transaction.setDestinationAccNumber(Long.valueOf(2));
		impl.transferBalance(transaction);
		System.out.println(impl.getAccounts());
		Assert.assertTrue(impl.getAccounts().stream().allMatch(x -> {
			if (x.getAccountNumber().equals(Long.valueOf(1))) {
				return x.getBalance().equals(Double.valueOf(0.0));
			} else if (x.getAccountNumber().equals(Long.valueOf(2))) {
				return x.getBalance().equals(Double.valueOf(20.0));
			} else
				return x.getBalance().equals(Double.valueOf(10.0));

		}));
	}

	private Set<Account> accountPopulator() {
		Account account1 = new Account();
		account1.setAccountNumber(Long.valueOf(1));
		account1.setBalance(Double.valueOf(10.0));
		Account account2 = new Account();
		account2.setAccountNumber(Long.valueOf(2));
		account2.setBalance(Double.valueOf(10.0));
		Account account3 = new Account();
		account3.setAccountNumber(Long.valueOf(3));
		account3.setBalance(Double.valueOf(10.0));
		return Stream.of(account1, account2, account3).collect(Collectors.toSet());
	}

}
