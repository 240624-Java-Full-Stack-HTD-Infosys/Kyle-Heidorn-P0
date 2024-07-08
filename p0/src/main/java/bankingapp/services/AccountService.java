package bankingapp.services;

import bankingapp.daos.AccountDao;
import bankingapp.daos.TransactionDao;
import bankingapp.exceptions.NoAccountException;
import bankingapp.models.Account;
import bankingapp.models.User;

import java.sql.SQLException;
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

    public void deleteAccount(int accountId){
        Account account = accountDao.getAccountById(accountId);


    }
}
