package controller.dashboard;

import model.Room;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AvailableRoomsController {
    private static AvailableRoomsController instance;
    private AvailableRoomsController(){}

    public static AvailableRoomsController getInstance(){
        return instance == null ? instance=new AvailableRoomsController():instance;
    }

    public boolean addNewRoom(Room room) {
        String sql = "INSERT INTO rooms (room_number,room_type,price_per_night,availability_status) VALUES (?,?,?,?)";
        try {
            return CrudUtil.execute(sql,
                    room.getRoomNumber(),
                    room.getRoomType(),
                    room.getPrice(),
                    room.getStatus());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Room> getAvailableRooms(){
        ArrayList<Room> availableRoomList = new ArrayList<>();
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM rooms");
            while (rst.next()){
                availableRoomList.add(new Room(
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

    public boolean updateAvailableRoom(Room room) {
        String sql = "UPDATE rooms SET room_type=?,availability_status=?,price_per_night=? WHERE room_number=?";
        try {
            return CrudUtil.execute(sql,
                    room.getRoomType(),
                    room.getStatus(),
                    room.getPrice(),
                    room.getRoomNumber());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isRoomNumberAlreadyExists(String roomNumber) {
        String sql = "SELECT room_number FROM rooms WHERE room_number='"+roomNumber+"'";
        try {
            ResultSet rst = CrudUtil.execute(sql);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
