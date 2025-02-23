package service.custom;

import dto.Room;
import service.SuperService;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface AvailableRoomsService extends SuperService {
    boolean addNewRoom(Room room);
    ArrayList<Room> getAvailableRooms();
    boolean updateAvailableRoom(Room room);
    boolean isRoomNumberAlreadyExists(String roomNumber);
    boolean deleteAvailableRoom(String roomNumber);
    ArrayList<String> getAvailableRoomNumbers();
    Double getPricePerNight(String roomNumber);
    boolean setRoomStatusOccupied(String roomNumber);
    boolean setRoomStatusAvailable(String roomNumber);
}
