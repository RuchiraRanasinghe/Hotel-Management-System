package repository;

import repository.custom.impl.*;
import util.DaoType;

public class DaoFactory {

    private static DaoFactory instance;
    private DaoFactory() {}

    public static DaoFactory getInstance(){return instance==null?instance=new DaoFactory():instance;}
    public <T extends SuperDao>T getDao(DaoType daoType){
        switch (daoType){
            case CUSTOMER:return (T) CustomerDaoImpl.getInstance();
            case RESERVATION:return (T) ReservationDaoImpl.getInstance();
            case AVAILABLEROOMS:return (T) AvailableRoomsDaoImpl.getInstance();
            case CHECKIN:return (T) CheckInDaoImpl.getInstance();
            case SIGNIN:return (T) SignInDaoImpl.getInstance();
        }
        return null;
    }
}
