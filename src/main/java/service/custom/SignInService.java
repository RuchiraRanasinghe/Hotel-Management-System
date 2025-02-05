package service.custom;

import dto.User;
import service.SuperService;

public interface SignInService extends SuperService {
    boolean authenticateUser(User user);
}
