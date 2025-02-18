package service.custom.impl;

import dto.Room;
import entity.RoomEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.matcher.StringMatcher;
import repository.DaoFactory;
import repository.custom.AvailableRoomsDao;
import service.custom.AvailableRoomsService;
import util.CrudUtil;
import util.DaoType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AvailableRoomsServiceImpl implements AvailableRoomsService {
    private static AvailableRoomsServiceImpl instance;
    private AvailableRoomsServiceImpl(){}

    public static AvailableRoomsServiceImpl getInstance(){
        return instance == null ? instance=new AvailableRoomsServiceImpl():instance;
    }

   AvailableRoomsDao availableRoomsDao = DaoFactory.getInstance().getDao(DaoType.AVAILABLEROOMS) ;

    @Override
    public boolean addNewRoom(Room room) {
        return availableRoomsDao.save(new ModelMapper().map(room, RoomEntity.class));
    }

    @Override
    public ArrayList<Room> getAvailableRooms(){
        ArrayList<Room> roomArrayList = new ArrayList<>();
        for (RoomEntity roomEntity : availableRoomsDao.getAll()) {
            roomArrayList.add(new ModelMapper().map(roomEntity, Room.class));
        }
        return roomArrayList;
    }

    @Override
    public boolean updateAvailableRoom(Room room) {
        return availableRoomsDao.update(room.getRoomNumber(),new ModelMapper().map(room, RoomEntity.class));
    }

    @Override
    public boolean isRoomNumberAlreadyExists(String roomNumber) {
        return availableRoomsDao.isRoomNumberAlreadyExists(roomNumber);
    }

    @Override
    public boolean deleteAvailableRoom(String roomNumber) {
        return availableRoomsDao.delete(roomNumber);
    }

    @Override
    public ArrayList<String> getAvailableRoomNumbers() {
        return availableRoomsDao.getAvailableRoomNumbers();
    }

    @Override
    public Double getPricePerNight(String roomNumber) {
        return availableRoomsDao.getPricePerNight(roomNumber);
    }

    @Override
    public boolean setRoomStatusOccupied(String roomNumber) {
        return availableRoomsDao.setRoomStatusOccupied(roomNumber);
    }
}
