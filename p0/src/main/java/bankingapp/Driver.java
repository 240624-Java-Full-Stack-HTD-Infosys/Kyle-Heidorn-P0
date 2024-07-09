package bankingapp;


import bankingapp.menus.LoginMenu;
import bankingapp.menus.MainMenu;
import bankingapp.menus.MenuUtil;
import bankingapp.menus.RegisterMenu;
import io.javalin.Javalin;

public class Driver {
    public static void main(String[] args) {

        Javalin app = Javalin.create().start(8080);
    }

}
