package bankingapp.daos;

import bankingapp.models.Transaction;
import bankingapp.models.Account;
import bankingapp.models.User;
import bankingapp.utils.ConnectionUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {

    private final Connection connection;

    public TransactionDao() throws SQLException, IOException {
        this.connection = ConnectionUtils.getConnection();
    }

    public void createTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (transaction_type, amount, date, user_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, transaction.getTransactionType());
            pstmt.setDouble(2, transaction.getAmount());
            pstmt.setString(3, transaction.getDate());
            pstmt.setInt(4, transaction.getUser().getUserId());
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                transaction.setTransactionId(keys.getInt(1));
            }
        }
    }

    public Transaction readTransaction(int id) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet results = pstmt.executeQuery();

        Transaction transaction = new Transaction();
        if (results.next()) {
            transaction.setTransactionId(results.getInt("transaction_id"));
            transaction.setTransactionType(results.getString("transaction_type"));
            transaction.setAmount(results.getDouble("amount"));
            transaction.setDate(results.getString("date"));

            User user = new User();
            user.setUserId(results.getInt("user_id"));
            transaction.setUser(user);

            List<Account> accounts = getAccountsByTransactionId(id);
            transaction.setAccounts(accounts);
        } else {
            return null;
        }

        return transaction;
    }

    public void updateTransaction(Transaction transaction) throws SQLException {
        String sql = "UPDATE transactions SET transaction_type = ?, amount = ?, date = ?, user_id = ? WHERE transaction_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, transaction.getTransactionType());
            pstmt.setDouble(2, transaction.getAmount());
            pstmt.setString(3, transaction.getDate());
            pstmt.setInt(4, transaction.getUser().getUserId());
            pstmt.setInt(5, transaction.getTransactionId());
            pstmt.executeUpdate();
        }
    }

    public void deleteTransaction(Transaction transaction) throws SQLException {
        String sql = "DELETE FROM transactions WHERE transaction_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, transaction.getTransactionId());
            pstmt.execute();
        }
    }

    public List<Transaction> readAllTransactionsByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE user_id = ?";
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet results = pstmt.executeQuery();
            while (results.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(results.getInt("transaction_id"));
                transaction.setTransactionType(results.getString("transaction_type"));
                transaction.setAmount(results.getDouble("amount"));
                transaction.setDate(results.getString("date"));

                User user = new User();
                user.setUserId(results.getInt("user_id"));
                transaction.setUser(user);

                List<Account> accounts = getAccountsByTransactionId(results.getInt("transaction_id"));
                transaction.setAccounts(accounts);

                transactions.add(transaction);
            }
        }
        return transactions;
    }

    private List<Account> getAccountsByTransactionId(int transactionId) throws SQLException {
        String sql = "SELECT a.* FROM accounts a JOIN transaction_accounts ta ON a.account_id = ta.account_id WHERE ta.transaction_id = ?";
        List<Account> accounts = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            ResultSet results = pstmt.executeQuery();
            while (results.next()) {
                Account account = new Account();
                account.setAccountId(results.getInt("account_id"));
                account.setBalance(results.getDouble("balance"));
                account.setAccountName(results.getString("account_name"));
                account.setAccountType(results.getString("account_type"));

                User user = new User();
                user.setUserId(results.getInt("user_id"));
                account.setUser(user);

                accounts.add(account);
            }
        }
        return accounts;
    }
}
