package repository.custom;

import entity.RoomEntity;
import repository.CrudDao;

import java.util.ArrayList;

public interface AvailableRoomsDao extends CrudDao<RoomEntity,String> {
    ArrayList<String> getAvailableRoomNumbers();
    boolean isRoomNumberAlreadyExists(String roomNumber);
    Double getPricePerNight(String roomNumber);
    boolean setRoomStatusOccupied(String roomNumber);
    boolean setRoomStatusAvailable(String roomNumber);
}
