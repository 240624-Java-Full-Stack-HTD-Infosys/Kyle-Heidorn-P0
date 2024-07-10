package bankingapp;


import bankingapp.utils.ServerUtil;
import io.javalin.Javalin;

import java.io.IOException;
import java.sql.SQLException;

public class Driver {
    public static void main(String[] args) {

        try {
            ServerUtil.getServerUtil().initialize(8080);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
