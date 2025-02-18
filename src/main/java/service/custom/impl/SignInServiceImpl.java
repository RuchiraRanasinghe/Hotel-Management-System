package service.custom.impl;

import DBConnection.DBConnection;
import dto.User;
import entity.UserEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.SignInDao;
import service.custom.SignInService;
import util.DaoType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SignInServiceImpl implements SignInService {
    public static SignInServiceImpl instance;
    private SignInServiceImpl(){}

    public static SignInServiceImpl getInstance(){return instance == null?instance=new SignInServiceImpl():instance;}

    SignInDao signInDao = DaoFactory.getInstance().getDao(DaoType.SIGNIN);

    @Override
    public boolean authenticateUser(User user) {
        return signInDao.authenticateUser(new ModelMapper().map(user, UserEntity.class));
    }
}
