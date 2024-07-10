package bankingapp.controllers;

import bankingapp.dtos.TransactionDto;
import bankingapp.exceptions.InsufficientFundException;
import bankingapp.exceptions.NoAccountException;
import bankingapp.exceptions.NonZeroBalanceException;
import bankingapp.models.Account;
import bankingapp.services.AccountService;
import bankingapp.services.UserService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.List;

public class AccountController {

    AccountService accountService;
    UserService userService;
    Javalin api;

    public AccountController(AccountService accountService, UserService userService, Javalin api) {
        this.accountService = accountService;
        this.userService = userService;
        this.api = api;

        api.post("/account", this::createAccount);
        api.get("/account/:userId", this::viewUserAccounts);
        api.delete("/account/:accountId", this::deleteAccount);
        api.post("/account/:accountId/deposit", this::depositMoney);
        api.post("/account/:accountId/withdraw", this::withdrawMoney);
        api.post("/account/transfer", this::transferMoney);

    }

    public void createAccount(Context ctx) throws SQLException {
        Account account = ctx.bodyAsClass(Account.class);
        Account createdAccount = accountService.createAccount(account);
        ctx.status(201).json(createdAccount);
    }

    public void viewUserAccounts(Context ctx) throws SQLException {
        int userId = Integer.parseInt(ctx.pathParam("userId"));
        List<Account> accounts = accountService.getAccountsByUserId(userId);
        ctx.json(accounts);
    }

    public void deleteAccount(Context ctx) throws SQLException, NonZeroBalanceException {
        int accountId = Integer.parseInt(ctx.pathParam("accountId"));
        accountService.deleteAccount(accountId);
        ctx.status(204);
    }

    public void depositMoney(Context ctx) throws NoAccountException, SQLException {
        int accountId = Integer.parseInt(ctx.pathParam("accountId"));
        double amount = ctx.bodyAsClass(TransactionDto.class).getAmount();
        accountService.deposit(accountId, amount);
        ctx.status(200);
    }

    public void withdrawMoney(Context ctx) throws NoAccountException, SQLException, InsufficientFundException {
        int accountId = Integer.parseInt(ctx.pathParam("accountId"));
        double amount = ctx.bodyAsClass(TransactionDto.class).getAmount();
        accountService.withdraw(accountId, amount);
        ctx.status(200);
    }

    public void transferMoney(Context ctx) throws NoAccountException, SQLException, InsufficientFundException {
        TransactionDto transactionDto = ctx.bodyAsClass(TransactionDto.class);
        accountService.transfer(transactionDto.getFromAccountId(), transactionDto.getToAccountId(), transactionDto.getAmount());
        ctx.status(200);
    }

}
