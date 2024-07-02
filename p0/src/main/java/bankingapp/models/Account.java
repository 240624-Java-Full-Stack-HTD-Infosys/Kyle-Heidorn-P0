package bankingapp.models;

public class Account {

    private double balance;
    private String accountType; // "Savings" or "Checking"
    private String status; // "Active" or "Closed"

    public Account(double balance, String accountType, String status) {
        this.balance = balance;
        this.accountType = accountType;
        this.status = status;
    }

    public Account() {

    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
