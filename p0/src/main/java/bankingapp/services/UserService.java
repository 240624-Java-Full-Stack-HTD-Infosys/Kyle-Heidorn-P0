package bankingapp.services;

import bankingapp.daos.UserDao;
import bankingapp.exceptions.BadPasswordException;
import bankingapp.exceptions.NoSuchUserException;
import bankingapp.models.User;

import java.sql.SQLException;

public class UserService {
    UserDao userDao;

    //implements the methods in the UserDao, so I can create the objects the controller will be using
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    //Calls the saveUser method because that is how the postgres database will be creating and storing the new users
    public User registerNewUser(User user) throws SQLException {
        return userDao.saveUser(user);
    }

    //the result User object is sent to the userDao to see if the username already exists
    //it then uses my custom no user and bad password exceptions to check if the username exists / if password is right
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

    //checks the user in the database to get their id
    public User readByUserId(int id) throws SQLException {
        return userDao.readUserId(id);
    }

    //checks the user by username in the database
    public User readUserByUsername(String username) throws SQLException{
        return userDao.readUserByUsername(username);
    }

    //updates the user in the database with the save user dao method that also checks if the user already exists
    public User updateUser(User user) throws SQLException {
        return userDao.saveUser(user);
    }

    //gets the user id and deletes the user id in the database via the dao object
    public User deleteUser(int userId) throws SQLException {
        return userDao.deleteUser(userId);
    }
}
