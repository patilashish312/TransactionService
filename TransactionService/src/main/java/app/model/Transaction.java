package app.model;

import org.springframework.stereotype.Component;

@Component
public class Transaction {
	Long srcAccountNumber;
	Long destinationAccNumber;
	Double amount;
	public Long getSrcAccountNumber() {
		return this.srcAccountNumber;
	}
	public void setSrcAccountNumber(Long srcAccountNumber) {
		this.srcAccountNumber = srcAccountNumber;
	}
	public Long getDestinationAccNumber() {
		return this.destinationAccNumber;
	}
	public void setDestinationAccNumber(Long destinationAccNumber) {
		this.destinationAccNumber = destinationAccNumber;
	}
	public Double getAmount() {
		return this.amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}
