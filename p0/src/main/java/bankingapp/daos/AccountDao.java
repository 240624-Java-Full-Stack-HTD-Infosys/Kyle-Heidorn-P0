package bankingapp.daos;

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

public class AccountDao {

    Connection connection;

    public AccountDao(Connection connection) throws SQLException, IOException {
        this.connection = ConnectionUtils.getConnection();
    }

    public Account saveAccount(Account account) throws SQLException {
        if (account.getAccountId() == null) {
            String sql = "INSERT INTO accounts (user_id, balance, account_name, account_type) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, account.getUser().getUserId());
                pstmt.setDouble(2, account.getBalance());
                pstmt.setString(3, account.getAccountName());
                pstmt.setString(4, account.getAccountType());
                pstmt.executeUpdate();

                ResultSet keys = pstmt.getGeneratedKeys();
                if (keys.next()) {
                    account.setAccountId(keys.getInt(1));
                }
            }
        } else {
            String sql = "UPDATE accounts SET user_id = ?, balance = ?, account_name = ?, account_type = ? WHERE account_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, account.getUser().getUserId());
                pstmt.setDouble(2, account.getBalance());
                pstmt.setString(3, account.getAccountName());
                pstmt.setString(4, account.getAccountType());
                pstmt.setInt(5, account.getAccountId());
                pstmt.executeUpdate();
            }
        }
        return account;
    }

    public Account readAccount(int id) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet results = pstmt.executeQuery();

        Account account = new Account();
        if (results.next()) {
            account.setAccountId(results.getInt("account_id"));
            account.setBalance(results.getDouble("balance"));
            account.setAccountName(results.getString("account_name"));
            account.setAccountType(results.getString("account_type"));

            User user = new User();
            user.setUserId(results.getInt("user_id"));
            account.setUser(user);
        } else {
            return null;
        }

        return account;
    }

    public List<Account> readAllAccountsByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        List<Account> accounts = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
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

    public void deleteAccount(int accountId) throws SQLException {
        String sql = "DELETE FROM accounts WHERE account_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            pstmt.execute();
        }
    }
}

