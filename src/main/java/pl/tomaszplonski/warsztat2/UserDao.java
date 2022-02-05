package pl.tomaszplonski.warsztat2;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {


    private static final String DATABASE = "workshop2";
    private static final String CREATE_USER_QUERY = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";

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

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


}
