package bankingapp.models;

public class Transaction {

    private String transactionType; // "Deposit", "Withdraw", "Transfer"
    private double amount;
    private String date;

    public Transaction(String transactionType, double amount, String date) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
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

}
