package service;

import service.custom.impl.*;
import util.ServiceType;

public class ServiceFactory {
    private static ServiceFactory instance;
    private ServiceFactory() {}

    public static ServiceFactory getInstance(){return instance==null?instance=new ServiceFactory():instance;}

    public <T extends SuperService>T getServiceType(ServiceType serviceType){
        switch (serviceType){
            case CUSTOMER:return (T) CustomerServiceImpl.getInstance();
            case AVAILABLEROOMS:return (T) AvailableRoomsServiceImpl.getInstance();
            case CHECKIN:return (T) CheckInServiceImpl.getInstance();
            case SIGNIN:return (T) SignInServiceImpl.getInstance();
            case RESERVATION:return (T) ReservationServiceImpl.getInstance();
        }
        return null;
    }
}
