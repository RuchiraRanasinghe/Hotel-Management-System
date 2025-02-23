package repository.custom.impl;

import entity.RoomEntity;
import repository.custom.AvailableRoomsDao;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AvailableRoomsDaoImpl implements AvailableRoomsDao {
    private static AvailableRoomsDaoImpl instance;

    public AvailableRoomsDaoImpl() {}

    public static AvailableRoomsDaoImpl getInstance(){return instance==null?instance=new AvailableRoomsDaoImpl():instance;}

    @Override
    public ArrayList<String> getAvailableRoomNumbers() {
        ArrayList<String> roomNumberList = new ArrayList<>();
        try {
            ResultSet rst = CrudUtil.execute("SELECT room_number FROM rooms WHERE availability_status='Available'");
            while (rst.next()){
                roomNumberList.add(rst.getString("room_number"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roomNumberList;
    }

    @Override
    public boolean isRoomNumberAlreadyExists(String roomNumber) {
        String sql = "SELECT room_number FROM rooms WHERE room_number='"+roomNumber+"'";
        try {
            ResultSet rst = CrudUtil.execute(sql);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Double getPricePerNight(String roomNumber) {
        String sql = "SELECT price_per_night FROM rooms WHERE room_number='"+roomNumber+"'";
        try {
            ResultSet rst = CrudUtil.execute(sql);
            if (rst.next()){
                return rst.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0.0;
    }

    @Override
    public boolean setRoomStatusOccupied(String roomNumber) {
        String sql = "UPDATE rooms SET availability_status = 'Occupied' WHERE room_number = '"+roomNumber+"'";
        try {
            return CrudUtil.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean setRoomStatusAvailable(String roomNumber) {
        String sql = "UPDATE rooms SET availability_status = 'Available' WHERE room_number = '"+roomNumber+"'";
        try {
            return CrudUtil.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean save(RoomEntity entity) {
        String sql = "INSERT INTO rooms (room_number,room_type,price_per_night,availability_status) VALUES (?,?,?,?)";
        try {
            return CrudUtil.execute(sql,
                    entity.getRoomNumber(),
                    entity.getRoomType(),
                    entity.getPrice(),
                    entity.getStatus());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(String s, RoomEntity entity) {
        String sql = "UPDATE rooms SET room_type=?,availability_status=?,price_per_night=? WHERE room_number=?";
        try {
            return CrudUtil.execute(sql,
                    entity.getRoomType(),
                    entity.getStatus(),
                    entity.getPrice(),
                    entity.getRoomNumber());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String roomNumber) {
        String sql = "DELETE FROM rooms WHERE room_number='"+roomNumber+"'";
        try {
            return CrudUtil.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RoomEntity> getAll() {
        ArrayList<RoomEntity> availableRoomList = new ArrayList<>();
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM rooms");
            while (rst.next()){
                availableRoomList.add(new RoomEntity(
                        rst.getString("room_number"),
                        rst.getString("room_type"),
                        rst.getString("availability_status"),
                        rst.getDouble("price_per_night")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return availableRoomList;
    }
}
