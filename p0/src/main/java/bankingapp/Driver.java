package bankingapp;


import bankingapp.menus.LoginMenu;
import bankingapp.menus.MainMenu;
import bankingapp.menus.MenuUtil;
import bankingapp.menus.RegisterMenu;

public class Driver {
    public static void main(String[] args) {
        MenuUtil menuUtil = MenuUtil.getMenuUtil();
        menuUtil.register(new MainMenu());
        menuUtil.register(new RegisterMenu());
        menuUtil.register(new LoginMenu());
        menuUtil.run();

    }

}
