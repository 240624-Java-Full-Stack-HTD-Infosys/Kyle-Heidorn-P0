package bankingapp.controllers;

import bankingapp.dtos.AuthDto;
import bankingapp.exceptions.BadPasswordException;
import bankingapp.exceptions.NoSuchUserException;
import bankingapp.models.User;
import bankingapp.services.UserService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Cookie;

import java.sql.SQLException;

public class UserController {

    UserService userService;
    Javalin api;

    public UserController(UserService userService, Javalin api) {
        this.userService = userService;
        this.api = api;


        api.post("/register", this::registerNewUser);
        api.post("/login", this::login);
        api.put("/user", this::updateUser);
        api.get("/user/:username", this::getUserByUsername);
        api.delete("/user/:userId", this::deleteUser);

    }

    public void registerNewUser(Context ctx) throws SQLException {
        User user = ctx.bodyAsClass(User.class);
        User registerUser = userService.registerNewUser(user);
        ctx.status(201);
    }

    public void login(Context ctx) throws BadPasswordException, NoSuchUserException{
        AuthDto auth = ctx.bodyAsClass(AuthDto.class);
        User result = null;
        try {
            result = userService.authenticateUser(auth.getUsername(), auth.getPassword());
            ctx.json(result);
            Cookie cookie = new Cookie("Auth", result.getUsername());
            ctx.cookie(cookie);
            ctx.status(200);
        } catch (NoSuchUserException e) {
            ctx.status(418);
            ctx.result("No such user");
        } catch (BadPasswordException e) {
            ctx.status(401);
            ctx.result("Bad password");
        }

    }

    public void updateUser(Context ctx) throws SQLException {
        User user = ctx.bodyAsClass(User.class);
        User updatedUser = userService.updateUser(user);
        ctx.json(updatedUser);
    }

    public void getUserByUsername(Context ctx) throws SQLException {
        String username = ctx.pathParam("username");
        User user = userService.readUserByUsername(username);
        ctx.json(user);
    }

    public void deleteUser(Context ctx) throws SQLException {
        int userId = Integer.parseInt(ctx.pathParam("userId"));
        userService.deleteUser(userId);
        ctx.status(204);
    }


}
