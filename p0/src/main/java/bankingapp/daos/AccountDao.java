package bankingapp.daos;

import bankingapp.models.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDao {

    private final Connection connection;

    public AccountDao(Connection connection) {
        this.connection = connection;
    }

    public void createAccount(Account account) throws SQLException {
        String sql = "INSERT into accounts (user_id, balance, accountType, accountName VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, account.getUser().getUserId());
            pstmt.setDouble(2, account.getBalance());
            pstmt.setString(3, account.getAccountType());
            pstmt.setString(4, account.getAccountName());
            pstmt.executeUpdate();
        }

    }

    public Account readAccount(int id) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet results = pstmt.executeQuery();

        Account account = new Account();
        if(results.next()) {
            account.setAccountId(results.getInt("account_id"));


        }

    }

    public void updateAccount(Account account){


    }

    public void deleteAccount(Account account){


    }
}
