package bankingapp.models;

import java.util.List;

public class Transaction {

    Integer transactionId;
    String transactionType; // "Deposit", "Withdraw", "Transfer"
    double amount;
    String date;
    User user;
    List<Account> accounts;

    public Transaction(String transactionType, double amount, String date, User user, List<Account> accounts) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
        this.user = user;
        this.accounts = accounts;
    }

    public Transaction() {
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}

