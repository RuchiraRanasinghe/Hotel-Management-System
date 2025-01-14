package controller.signIn;

import DBConnection.DBConnection;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SignInController implements SignInService {
    public static SignInController instance;
    private SignInController(){}

    public static SignInController getInstance(){return instance == null?instance=new SignInController():instance;}

    @Override
    public boolean authenticateUser(User user) {
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM users WHERE username='" + user.getUsername() + "' AND role='" + user.getRole() + "'");
            if (resultSet.next()){
                String password = resultSet.getString(3);
                if (user.getPassword().equals(password)){
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
