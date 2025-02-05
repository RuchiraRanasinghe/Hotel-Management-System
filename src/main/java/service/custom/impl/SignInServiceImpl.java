package service.custom.impl;

import DBConnection.DBConnection;
import dto.User;
import service.custom.SignInService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SignInServiceImpl implements SignInService {
    public static SignInServiceImpl instance;
    private SignInServiceImpl(){}

    public static SignInServiceImpl getInstance(){return instance == null?instance=new SignInServiceImpl():instance;}

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
