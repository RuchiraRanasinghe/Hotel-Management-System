package repository.custom.impl;

import DBConnection.DBConnection;
import dto.User;
import entity.UserEntity;
import repository.custom.SignInDao;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SignInDaoImpl implements SignInDao {
    private static SignInDaoImpl instance;

    public SignInDaoImpl() {}

    public static SignInDaoImpl getInstance(){return instance==null?instance=new SignInDaoImpl():instance;}

    @Override
    public boolean save(UserEntity entity) {
        return false;
    }

    @Override
    public boolean update(Integer integer, UserEntity entity) {
        return false;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    @Override
    public List<UserEntity> getAll() {
        return List.of();
    }

    @Override
    public boolean authenticateUser(UserEntity user) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM users WHERE username='" + user.getUsername() + "' AND role='" + user.getRole() + "'");
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
