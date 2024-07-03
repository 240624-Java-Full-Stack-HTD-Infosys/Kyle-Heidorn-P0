package bankingapp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtils {

    public static Connection getConnection() throws SQLException, IOException{

        InputStream inputStream = ConnectionUtils.class.getClassLoader().getResourceAsStream("application.properties");
        Properties props = new Properties();
        props.load(inputStream);

        Connection conn = DriverManager.getConnection(props.getProperty("url"), props.getProperty("username"), props.getProperty("password"));


        return conn;
    }

}
