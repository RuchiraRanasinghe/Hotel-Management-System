package repository.custom;

import dto.User;
import entity.UserEntity;
import repository.CrudDao;

public interface SignInDao extends CrudDao<UserEntity, Integer> {
    boolean authenticateUser(UserEntity user);
}
