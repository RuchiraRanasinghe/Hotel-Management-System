package controller.checkIn;

import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckInController {
    private static CheckInController instance;
    private CheckInController(){}

    public static CheckInController getInstance(){
        return instance == null ? instance=new CheckInController():instance;
    }

    public String getNewReservationId() {
        String sql = "SELECT reservation_id FROM reservations order by reservation_id desc limit 1";
        try {
            ResultSet rst = CrudUtil.execute(sql);
            return rst.next() ? rst.getString("reservation_id") : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
