package bankingapp.models;

public class Account {

    private Integer accountId;
    private double balance;
    private String accountName;
    private String accountType; // "Savings" or "Checking"
    User user;

    public Account() {

    }

    public Account(Integer accountId, double balance, String accountName, String accountType, User user) {
        this.accountId = accountId;
        this.balance = balance;
        this.accountName = accountName;
        this.accountType = accountType;
        this.user = user;
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

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
