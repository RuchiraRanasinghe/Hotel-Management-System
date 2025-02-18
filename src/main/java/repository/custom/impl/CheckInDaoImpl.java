package repository.custom.impl;

import entity.ReservationEntity;
import repository.custom.CheckInDao;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CheckInDaoImpl implements CheckInDao {
    private static CheckInDaoImpl instance;

    public CheckInDaoImpl() {}

    public static CheckInDaoImpl getInstance(){return instance==null?instance=new CheckInDaoImpl():instance;}

    @Override
    public boolean save(ReservationEntity entity) {
        return false;
    }

    @Override
    public boolean update(Integer integer, ReservationEntity entity) {
        return false;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    @Override
    public List<ReservationEntity> getAll() {
        return List.of();
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
