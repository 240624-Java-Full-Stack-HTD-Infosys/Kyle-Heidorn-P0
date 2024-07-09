package bankingapp.services;

import bankingapp.daos.AccountDao;
import bankingapp.daos.TransactionDao;
import bankingapp.exceptions.InsufficientFundException;
import bankingapp.exceptions.NoAccountException;
import bankingapp.exceptions.NonZeroBalanceException;
import bankingapp.models.Account;
import bankingapp.models.Transaction;
import bankingapp.models.User;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class AccountService {

    AccountDao accountDao;
    TransactionDao transactionDao;

    public AccountService(AccountDao accountDao, TransactionDao transactionDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
    }

    public Account createAccount(Account account) throws SQLException {
        return accountDao.saveAccount(account);
    }

    public Account getAccountById(int accountId) throws NoAccountException, SQLException {
        Account account = accountDao.readAccount(accountId);
        if(account == null){
            throw new NoAccountException("Account not found.");
        }
        return account;
    }

    public List<Account> getAccountsByUserId(int userId) throws SQLException {
        return accountDao.readAllAccountsByUserId(userId);
    }

    public Account updateAccount(Account account) throws SQLException {
        return accountDao.saveAccount(account);
    }

    public void deleteAccount(int accountId) throws SQLException, NonZeroBalanceException {
        Account account = accountDao.readAccount(accountId);

        if(account.getBalance() != 0){
            throw new NonZeroBalanceException("Balance is not 0");
        }else{
            accountDao.deleteAccount(account.getAccountId());
        }
    }

    public void deposit(int accountId, double amount) throws NoAccountException, SQLException {
        Account account = getAccountById(accountId);
        account.setBalance(account.getBalance() + amount);
        accountDao.saveAccount(account);

        Transaction transaction = new Transaction("Deposit", amount, "current_date", account.getUser(), Arrays.asList(account));
        transactionDao.saveTransaction(transaction);
    }

    public void withdraw(int accountId, double amount) throws NoAccountException, SQLException, InsufficientFundException {
        Account account = getAccountById(accountId);
        if(account.getBalance() <= amount){
            throw new InsufficientFundException("Not enough funds in account to withdraw.");
        }else{
            account.setBalance(account.getBalance() - amount);
        }
        accountDao.saveAccount(account);
        Transaction transaction = new Transaction("Withdraw", amount, "current_date", account.getUser(), Arrays.asList(account));
        transactionDao.saveTransaction(transaction);
    }

    public void transfer(int fromAccountId, int toAccountId, double amount) throws NoAccountException, SQLException, InsufficientFundException {
        Account fromAccount = getAccountById(fromAccountId);
        Account toAccount = getAccountById(toAccountId);

        if(fromAccount.getBalance() <= amount){
            throw new InsufficientFundException("Not enough funds in account to withdraw.");
        }else{
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);
        }
        accountDao.saveAccount(fromAccount);
        Transaction transaction = new Transaction("Transfer Out", amount, "current_date", fromAccount.getUser(), Arrays.asList(fromAccount));
        transactionDao.saveTransaction(transaction);


        accountDao.saveAccount(toAccount);
        Transaction transaction1 = new Transaction("Transfer In", amount, "current_date", toAccount.getUser(), Arrays.asList(toAccount));
        transactionDao.saveTransaction(transaction1);

    }

    public List<Transaction> getTransactionHistory(int accountId) throws SQLException {
        return transactionDao.readAllTransactionsByUserId(accountId);
    }
}

