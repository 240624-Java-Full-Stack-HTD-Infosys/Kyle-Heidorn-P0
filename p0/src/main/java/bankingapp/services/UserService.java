package bankingapp.services;

import bankingapp.daos.UserDao;
import bankingapp.exceptions.BadPasswordException;
import bankingapp.exceptions.NoSuchUserException;
import bankingapp.models.User;

import java.sql.SQLException;

public class UserService {
    UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User registerNewUser(User user) throws SQLException {
        return userDao.saveUser(user);
    }

    public User authenticateUser(String username, String password) throws NoSuchUserException, BadPasswordException {
        User result;
        try{
            result = userDao.readUserByUsername(username);
        }catch (SQLException e){
            throw new NoSuchUserException("User not found.");
        }

        if(result.getPassword().equals(password)){
            return result;
        }else{
            throw new BadPasswordException("Wrong password!");
        }

    }

    public User readUserByUsername(String username) throws SQLException{
        return userDao.readUserByUsername(username);
    }

}
