package repository.custom.impl;

public class ReservationDaoImpl {
    private static ReservationDaoImpl instance;

    public ReservationDaoImpl() {}

    public static ReservationDaoImpl getInstance(){return instance==null?instance=new ReservationDaoImpl():instance;}
}
