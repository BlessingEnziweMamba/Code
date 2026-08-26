package Blessing;

import java.util.ArrayList;

public class BankAccount {

	private String ownerName;
	private double balance;
	private ArrayList<String> transactionHistory;

	public BankAccount(String ownerName, double startingBalance) {

		this.ownerName = ownerName;
		this.balance = startingBalance;
		this.transactionHistory = new ArrayList<String>();
		transactionHistory.add("Account opened with balance R " + startingBalance);

	}

	public void deposit(double amount) {
		if (amount <= 0) {
			System.out.println("Deposit amount must be positive.");
			return;

		}
		balance += amount;
		transactionHistory.add("Deposit R" + amount);
		System.out.println("Deposit R" + amount + ". New balance: R" + balance);
	}

	public void withdraw(double amount) {
		if (amount > balance) {
			transactionHistory.add("Failed withdrawal attempt of R" + amount + "(insufficient funds)");
			System.out.println("insufficient funds, The balance: R" + balance);
			return;

		}
		balance -= amount;
		transactionHistory.add("Withdrew R" + amount);
		System.out.println("Withdrew R" + amount + ", new balance: R" + balance);

	}

	public void printHistory() {
		System.out.println("\n  Transaction History for " + ownerName + "  ");
		int count = 1;
		for (String entry : transactionHistory) {
			System.out.println(count + ". " + entry);
			count++;

		}
		System.out.println(".size() reports " + transactionHistory.size() + " total transactions.");

	}

	public double getBalance() {
		return balance;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public static void main(String[] args) {
		BankAccount acc = new BankAccount("Blessing", 100.0);
		acc.deposit(50.0);
		acc.withdraw(30.0);
		acc.withdraw(1000.0);
		acc.printHistory();
		System.out.println("Final balance: R" + acc.getBalance());
	}

}
