package service.custom.impl;

import repository.DaoFactory;
import repository.custom.CheckInDao;
import service.custom.CheckInService;
import util.CrudUtil;
import util.DaoType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckInServiceImpl implements CheckInService {
    private static CheckInServiceImpl instance;
    private CheckInServiceImpl(){}

    public static CheckInServiceImpl getInstance(){
        return instance == null ? instance=new CheckInServiceImpl():instance;
    }

    CheckInDao checkInDao = DaoFactory.getInstance().getDao(DaoType.CHECKIN);

    @Override
    public String getNewReservationId() {
        return checkInDao.getNewReservationId();
    }
}
