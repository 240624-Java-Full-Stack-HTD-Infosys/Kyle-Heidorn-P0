package bankingapp.controllers;

import bankingapp.dtos.TransactionDto;
import bankingapp.exceptions.InsufficientFundException;
import bankingapp.exceptions.NoAccountException;
import bankingapp.exceptions.NonZeroBalanceException;
import bankingapp.models.Account;
import bankingapp.models.Transaction;
import bankingapp.services.AccountService;
import bankingapp.services.TransactionService;
import bankingapp.services.UserService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.List;

public class AccountController {

    AccountService accountService;
    UserService userService;
    TransactionService transactionService;
    Javalin api;

    public AccountController(AccountService accountService, UserService userService, TransactionService transactionService, Javalin api) {
        this.accountService = accountService;
        this.userService = userService;
        this.transactionService = transactionService;
        this.api = api;

        api.post("/account", this::createAccount);
        api.get("/account/{userId}", this::viewUserAccounts);
        api.post("/account/{updateAccount}", this::updateAccount);
        api.post("/account/{accountId}/deposit", this::depositMoney);
        api.post("/account/{accountId}/withdraw", this::withdrawMoney);
        api.post("/account/{fromAccountId}/transfer/{toAccountId}", this::transferMoney);
        api.get("/account/{accountId}/transactions", this::getTransactions);
        api.delete("/account/{accountId}", this::deleteAccount);

    }

    public void createAccount(Context ctx) throws SQLException {
        Account account = ctx.bodyAsClass(Account.class); //Send the request to the account object
        Account createdAccount = accountService.createAccount(account); //calls the account service to create the account
        ctx.status(201).json(createdAccount);  //returns created account with status
    }

    public void updateAccount(Context ctx) throws SQLException{
        Account account = ctx.bodyAsClass(Account.class); //sends update request to the account object
        Account updatedAccount = accountService.updateAccount(account); //calls the account service to update the account
        ctx.status(201).json(updatedAccount); //returns updated account with status
    }

    public void viewUserAccounts(Context ctx) throws SQLException {
        int userId = Integer.parseInt(ctx.pathParam("userId")); //finds the userId path and gets it
        List<Account> accounts = accountService.getAccountsByUserId(userId); //call the service method set up to retrieve the accounts
        ctx.json(accounts);
    }

    public void deleteAccount(Context ctx) throws SQLException, NonZeroBalanceException {
        int accountId = Integer.parseInt(ctx.pathParam("accountId")); //finds the accountId path
        accountService.deleteAccount(accountId); //calls the delete account method from account service
        ctx.status(204);
    }

    public void depositMoney(Context ctx) throws NoAccountException, SQLException {
        int accountId = Integer.parseInt(ctx.pathParam("accountId"));   //finds the account Id
        double amount = ctx.bodyAsClass(TransactionDto.class).getAmount(); //calls the dto to retrieve the amount
        accountService.deposit(accountId, amount); //uses the deposit service method to send the amount to the account
        ctx.json("+ $" + amount);
        ctx.status(200);
    }

    public void withdrawMoney(Context ctx) throws NoAccountException, SQLException, InsufficientFundException {
        int accountId = Integer.parseInt(ctx.pathParam("accountId"));   //finds the account Id
        double amount = ctx.bodyAsClass(TransactionDto.class).getAmount(); //
        accountService.withdraw(accountId, amount);
        ctx.json("- $" + amount);
        ctx.status(200);
    }

    public void transferMoney(Context ctx) throws NoAccountException, SQLException, InsufficientFundException {
        int fromAccountId = Integer.parseInt(ctx.pathParam("fromAccountId"));
        int toAccountId = Integer.parseInt(ctx.pathParam("toAccountId"));
        TransactionDto transactionDto = ctx.bodyAsClass(TransactionDto.class);

        transactionDto.setFromAccountId(fromAccountId);
        transactionDto.setToAccountId(toAccountId);

        accountService.transfer(transactionDto.getFromAccountId(), transactionDto.getToAccountId(), transactionDto.getAmount());
        ctx.json("Transferred $" + transactionDto.getAmount());
        ctx.status(200);
    }

    public void getTransactions(Context ctx) throws SQLException {
        int accountId = Integer.parseInt(ctx.pathParam("accountId"));
        List<Transaction> transactions = accountService.getTransactionHistory(accountId);
        ctx.json(transactions);
    }

}
