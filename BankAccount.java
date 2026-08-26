import java.util.ArrayList;

public class BankAccount {

    private final String ownerName;
    private double balance;
    private final ArrayList<String> transactionHistory;

    public BankAccount(String ownerName, double startingBalance) {
        this.ownerName = ownerName;
        this.balance = startingBalance;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account opened with balance R" + startingBalance);
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }
        balance = balance + amount;
        transactionHistory.add("Deposited R" + amount);
        System.out.println("Deposited R" + amount + ". New balance: R" + balance);
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            transactionHistory.add("Failed withdrawal attempt of R" + amount + " (insufficient funds)");
            System.out.println("Insufficient funds. Current balance: R" + balance);
            return;
        }
        balance = balance - amount;
        transactionHistory.add("Withdrew R" + amount);
        System.out.println("Withdrew R" + amount + ". New balance: R" + balance);
    }

    public void printHistory() {
        System.out.println("\n--- Transaction History for " + ownerName + " ---");
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
}