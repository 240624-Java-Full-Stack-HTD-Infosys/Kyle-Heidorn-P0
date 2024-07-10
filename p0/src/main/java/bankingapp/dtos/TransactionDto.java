package bankingapp.dtos;

public class TransactionDto {
    private Double amount;
    private String transactionType;
    private Integer accountId;
    private Integer fromAccountId;
    private Integer toAccountId;

    public TransactionDto(Double amount, String transactionType, Integer accountId, Integer fromAccountId, Integer toAccountId) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.accountId = accountId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Integer fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Integer getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Integer toAccountId) {
        this.toAccountId = toAccountId;
    }
}

