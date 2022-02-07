package pl.tomaszplonski.warsztat2;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDao {


    private static final String DATABASE = "workshop2";
    private static final String CREATE_USER_QUERY = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String READ_USER_QUERY = "SELECT * FROM users WHERE ";
    private static final String UPDATE_QUERY = "UPDATE users SET email = ?, username = ?, password = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM users";

    public static User createUser(User user){

        try (Connection conn = DBUtil.connect(DATABASE);
             PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS))
        {
            statement.setString( 1, user.getUserName());
            statement.setString( 2, user.getEmail());
            statement.setString( 3, hashPassword(user.getPassword()));
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                user.setId(rs.getInt(1));
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return user;


    }

    public static User read(int userId){

        String readUser = READ_USER_QUERY + "id = "  + userId;

        try (Connection conn = DBUtil.connect(DATABASE);
             PreparedStatement statement = conn.prepareStatement(readUser);
             ResultSet resultSet = statement.executeQuery();)
        {
            if(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static int deleteUser(int userID){

        try (Connection conn = DBUtil.connect(DATABASE);
             PreparedStatement statement = conn.prepareStatement(DELETE_QUERY))
        {
            statement.setInt(1,userID);
            return statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }

    public static User updateUser(User user){

        try (Connection conn = DBUtil.connect(DATABASE);
             PreparedStatement statement = conn.prepareStatement(UPDATE_QUERY))
        {
            statement.setString( 1, user.getUserName());
            statement.setString( 2, user.getEmail());
            statement.setString( 3, hashPassword(user.getPassword()));
            statement.setInt(4,user.getId());
            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return user;

    }

    public static List<User> readAll() {

        List<User> users = new ArrayList<>();

        try (Connection conn = DBUtil.connect(DATABASE);
             PreparedStatement statement = conn.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                System.out.println(user.getUserName());
                users.add(user);
            }

            return users;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }



}
