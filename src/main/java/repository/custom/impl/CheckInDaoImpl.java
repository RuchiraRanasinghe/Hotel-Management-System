package repository.custom.impl;

public class CheckInDaoImpl {
    private static CheckInDaoImpl instance;

    public CheckInDaoImpl() {}

    public static CheckInDaoImpl getInstance(){return instance==null?instance=new CheckInDaoImpl():instance;}
}
