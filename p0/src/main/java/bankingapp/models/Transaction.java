package bankingapp.models;

import java.util.List;

public class Transaction {

    private Integer transactionId;
    private String transactionType; // "Deposit", "Withdraw", "Transfer"
    private double amount;
    private String date;
    User user;
    List<Account> accounts;

    public Transaction(Integer transactionId, String transactionType, double amount, String date, User user) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
        this.user = user;
    }

    public Transaction() {

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

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
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
