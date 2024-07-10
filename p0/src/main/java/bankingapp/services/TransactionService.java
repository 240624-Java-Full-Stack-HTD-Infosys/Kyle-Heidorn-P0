package bankingapp.services;

import bankingapp.daos.TransactionDao;
import bankingapp.models.Transaction;

import java.sql.SQLException;
import java.util.List;

public class TransactionService {

    TransactionDao transactionDao;

    public TransactionService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public Transaction createTransaction(Transaction transaction) throws SQLException {
        return transactionDao.saveTransaction(transaction);
    }


    public Transaction getTransactionById(int transactionId) throws SQLException {
        return transactionDao.getTransactionById(transactionId);
    }

    public List<Transaction> getTransactionsByAccountId(int accountId) throws SQLException {
        return transactionDao.getTransactionsByAccountId(accountId);
    }

   public List<Transaction> getTransactionsByUserId(int userId) throws SQLException {
        return transactionDao.readAllTransactionsByUserId(userId);
   }
}

