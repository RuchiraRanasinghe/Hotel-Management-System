package service.custom.impl;

import service.custom.CheckInService;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckInServiceImpl implements CheckInService {
    private static CheckInServiceImpl instance;
    private CheckInServiceImpl(){}

    public static CheckInServiceImpl getInstance(){
        return instance == null ? instance=new CheckInServiceImpl():instance;
    }

    @Override
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
