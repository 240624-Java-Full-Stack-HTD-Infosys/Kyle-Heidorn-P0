package bankingapp.utils;

import bankingapp.daos.AccountDao;
import bankingapp.daos.TransactionDao;
import bankingapp.daos.UserDao;
import bankingapp.services.AccountService;
import bankingapp.services.TransactionService;
import bankingapp.services.UserService;
import io.javalin.Javalin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ServerUtil {

    private static ServerUtil serverUtil;

    private ServerUtil(){

    }

    public static ServerUtil getServerUtil(){
        if(serverUtil == null){
            serverUtil = new ServerUtil();
        }
        return serverUtil;
    }

    public Javalin initialize(int port) throws SQLException, IOException, ClassNotFoundException{
        Javalin api = Javalin.create().start(port);
        Connection conn = ConnectionUtils.getConnection();

        UserDao userDao = new UserDao(conn);
        AccountDao accountDao = new AccountDao(conn);
        TransactionDao transactionDao = new TransactionDao(conn);

        UserService userService = new UserService(userDao);
        AccountService accountService = new AccountService(accountDao, transactionDao);
        TransactionService transactionService = new TransactionService(transactionDao);




        return api;
    }

}
