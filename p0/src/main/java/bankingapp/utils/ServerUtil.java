package bankingapp.utils;

import bankingapp.controllers.AccountController;
import bankingapp.controllers.UserController;
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
        Javalin api = Javalin.create().start(port);    //this initializes the api
        Connection conn = ConnectionUtils.getConnection();  //initializes the connection with database from the connection set up

        UserDao userDao = new UserDao(conn);        //links the userDao objects with the user table in postgres
        AccountDao accountDao = new AccountDao(conn);   //links the accountDao objects with the user table in postgres
        TransactionDao transactionDao = new TransactionDao(conn);   //links the transactionDao objects with the user table in postgres

        UserService userService = new UserService(userDao);     //creates a new userService object for the Api to utilize
        AccountService accountService = new AccountService(accountDao, transactionDao, userDao); //creates a new accountService (takes in all three dao objects) object for the Api to utilize
        TransactionService transactionService = new TransactionService(transactionDao);  //creates a new transactionService object for the Api to utilize

        UserController userController = new UserController(userService, api);   //the user controller uses the services methods derived from the dao to send to the api server
        AccountController accountController = new AccountController(accountService, userService, transactionService, api);



        return api;
    }

}
