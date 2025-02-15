package repository.custom.impl;

public class AvailableRoomsDaoImpl {
    private static AvailableRoomsDaoImpl instance;

    public AvailableRoomsDaoImpl() {}

    public static AvailableRoomsDaoImpl getInstance(){return instance==null?instance=new AvailableRoomsDaoImpl():instance;}
}
