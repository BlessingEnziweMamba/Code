package Blessing;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/* this program is about a bank simulator where the user insert their name and 
 * amounts for their bank account*/

public class BankAccount_2 {
	private String ownerName;
	private double balance;
	private ArrayList<String> transactionHistory;

	public BankAccount(String ownerName, double startingBalance) {

		this.ownerName = ownerName;
		this.balance = startingBalance;
		this.transactionHistory = new ArrayList<String>();
		transactionHistory.add("The account is opened with balace R" + startingBalance);

	}

	public void deposit(double amount) {
		if (amount <= 0) {
			System.out.println("Deposit amount must be positive always.");
			return;

		}

		balance += amount;
		transactionHistory.add("Deposit R" + amount);
		System.out.println("Deposit R" + amount + ". New balance: R" + balance);

	}
	public void withdraw(double amount) {
		if (amount > balance) {
			transactionHistory.add("Failed withdrawal attempt of R" + amount + "( insuffient funds)");
			System.out.println("Insufficient funds, the balance: R" + balance);
			return;
		}

		balance -= amount;
		transactionHistory.add("The withdrawal R" + amount);
		System.out.println("The withdrawn amount is R" + amount + ", new balance: R" + balance);

	}

	public void printHistory() {
		System.out.println("\n Transaction History for " + ownerName + "  ");
		int count = 1;
		for (String entry : transactionHistory) {
			System.out.println(count + ". " + entry);
			count++;

		}

		System.out.println(".size() reports " + transactionHistory.size() + "total balance");

	}

	public double getBalance() {
		return balance;

	}

	public String getOwnerName() {
		return ownerName;
	}

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		scanner.useLocale(Locale.US);

		System.out.print("Enter owner Name: ");
		scanner.useLocale(Locale.US);

		System.out.print("Enter the starting balance: R");
		double startingBalance =scanner.nextDouble();

		BankAccount account = new BankAccount(ownerName, startingBalance);

		boolean  running = true;
		while (running) {

			System.out.println("\nWhat would you like to do?");
			System.out.println("1. Deposit");
			System.out.println("2. Withdraw");
			System.out.println("3. View the transaction history");
			System.out.println("4. Check balance");
			System.out.println("5. Exit");
			System.out.println("Enter your choice");
			int choice = scanner.nextInt();

			switch (choice) {

			case 1:
				System.out.print("Enter amount to deposit: R ");
				double depositAmount = scanner.nextDouble();
				account.deposit(depositAmount);
				break;

			case 2:
				System.out.print("Enter amount to withdraw: R");
				double withdrawAmount = scanner.nextDouble();
				account.withdraw(withdrawAmount);

			case 3:
				account.printHistory();
				break;

			case 4:
				System.out.println("Current balance: R" + account.getBalance());
				break;

			case 5:
				System.out.println("GOODBYE, " + account.getOwnerName());
				running = false;
				break;

			default:
				System.out.println("Invalid choice, please try again.");


			}

			scanner.close();

		}

	}

}
