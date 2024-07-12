package bankingapp.daos;

import bankingapp.models.Account;
import bankingapp.models.Transaction;
import bankingapp.models.User;
import bankingapp.utils.ConnectionUtils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {

    Connection connection;

    public TransactionDao(Connection connection) throws SQLException, IOException {
        this.connection = ConnectionUtils.getConnection();
    }

    //checks if the transaction id already exists and then creates the id for it
    //Also checks if a transaction id exists and needs to be updated
    public Transaction saveTransaction(Transaction transaction) throws SQLException {
        if (transaction.getTransactionId() == null) {
            String sql = "INSERT INTO transactions (transaction_type, amount, date, account_id, user_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, transaction.getTransactionType());
                pstmt.setDouble(2, transaction.getAmount());
                pstmt.setString(3, transaction.getDate());  // Assuming you are not using timestamp
                pstmt.setInt(4, transaction.getAccounts().get(0).getAccountId()); // Assuming first account for simplicity
                pstmt.setInt(5, transaction.getUser().getUserId());
                pstmt.executeUpdate();

                ResultSet keys = pstmt.getGeneratedKeys();
                if (keys.next()) {
                    transaction.setTransactionId(keys.getInt(1));
                }
            }
        } else {
            String sql = "UPDATE transactions SET transaction_type = ?, amount = ?, date = ?, user_id = ? WHERE transaction_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, transaction.getTransactionType());
                pstmt.setDouble(2, transaction.getAmount());
                pstmt.setString(3, transaction.getDate());  // Assuming you are not using timestamp
                pstmt.setInt(4, transaction.getUser().getUserId());
                pstmt.setInt(5, transaction.getTransactionId());
                pstmt.executeUpdate();
            }
        }
        return transaction;
    }

    //retrieves the transaction by the id
    public Transaction getTransactionById(int transactionId) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            try (ResultSet results = pstmt.executeQuery()) {
                if (results.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setTransactionId(results.getInt("transaction_id"));
                    transaction.setTransactionType(results.getString("transaction_type"));
                    transaction.setAmount(results.getDouble("amount"));
                    transaction.setDate(results.getString("date"));

                    User user = new User();
                    user.setUserId(results.getInt("user_id"));
                    transaction.setUser(user);

                    Account account = new Account();
                    account.setAccountId(results.getInt("account_id"));
                    transaction.setAccounts(List.of(account));

                    return transaction;
                } else {
                    return null;
                }
            }
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

                Account account = new Account();
                account.setAccountId(results.getInt("account_id"));
                transaction.setAccounts(List.of(account));

                transactions.add(transaction);
            }
        }
        return transactions;
    }

    public List<Transaction> getTransactionsByAccountId(int accountId) throws  SQLException{
        String sql = "SELECT * FROM transactions WHERE account_id = ?";
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, accountId);
            ResultSet results = pstmt.executeQuery();

            while(results.next()){
                Transaction transaction = new Transaction();
                transaction.setTransactionId(results.getInt("transaction_id"));
                transaction.setTransactionType(results.getString("transaction_type"));
                transaction.setAmount(results.getDouble("amount"));
                transaction.setDate(results.getString("date"));

                Account account = new Account();
                account.setAccountId(results.getInt("account_id"));
                transaction.setAccounts(List.of(account));

                User user = new User();
                user.setUserId(results.getInt("user_id"));
                transaction.setUser(user);

                transactions.add(transaction);
            }
        }
        return transactions;
    }
}

