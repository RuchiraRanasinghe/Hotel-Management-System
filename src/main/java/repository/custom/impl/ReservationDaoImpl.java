package repository.custom.impl;

import dto.Reservation;
import entity.ReservationEntity;
import javafx.scene.chart.XYChart;
import repository.custom.ReservationDao;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationDaoImpl implements ReservationDao {
    private static ReservationDaoImpl instance;

    public ReservationDaoImpl() {}

    public static ReservationDaoImpl getInstance(){return instance==null?instance=new ReservationDaoImpl():instance;}

    @Override
    public boolean save(ReservationEntity reservation) {
        String sql = "INSERT INTO reservations (customer_NIC,room_number,check_in_date,check_out_date,total_amount) VALUES (?,?,?,?,?)";
        try {
            return CrudUtil.execute(sql,
                    reservation.getCustomerNIC(),
                    reservation.getRoomNumber(),
                    reservation.getCheckInDate(),
                    reservation.getCheckOutDate(),
                    reservation.getTotalAmount());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }    }

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
        List<ReservationEntity> reservationList = new ArrayList<>();
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM reservations");
            while (rst.next()){
                reservationList.add(new ReservationEntity(
                        rst.getInt("reservation_id"),
                        rst.getString("customer_NIC"),
                        rst.getString("room_number"),
                        rst.getString("check_in_date"),
                        rst.getString("check_out_date"),
                        rst.getDouble("total_amount"),
                        rst.getString("reservation_status")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservationList;
    }

    @Override
    public ReservationEntity getLastReservationData() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM reservations ORDER BY reservation_id DESC LIMIT 1");
            rst.next();
            return new ReservationEntity(
                    rst.getInt("reservation_id"),
                    rst.getString("customer_NIC"),
                    rst.getString("room_number"),
                    rst.getString("check_in_date"),
                    rst.getString("check_out_date"),
                    rst.getDouble("total_amount"),
                    rst.getString("reservation_status"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getCountBookToday() {
        int count = 0;
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String sql = "SELECT COUNT(reservation_id) FROM reservations WHERE check_in_date = '"+sqlDate+"'";
        try {
            ResultSet rst = CrudUtil.execute(sql);
            while (rst.next()) {
                count = rst.getInt("COUNT(reservation_id)");
            }
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Double getIncomeToday() {
        double incomeToday = 0.0;
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String sql = "SELECT SUM(total_amount) FROM reservations WHERE check_in_date = '"+sqlDate+"'";
        try {
            ResultSet rst = CrudUtil.execute(sql);
            while (rst.next()){
                incomeToday = rst.getDouble("SUM(total_amount)");
            }
            return incomeToday;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Double getTotalIncome() {
        double totalIncome = 0.0;
        String sql = "SELECT SUM(total_amount) FROM reservations";
        try {
            ResultSet rst = CrudUtil.execute(sql);
            while (rst.next()){
                totalIncome = rst.getDouble("SUM(total_amount)");
            }
            return totalIncome;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public XYChart.Series getChartData() {
        String sql = "SELECT check_in_date, SUM(total_amount) AS total_amount FROM reservations GROUP BY check_in_date ORDER BY TIMESTAMP(check_in_date) ASC LIMIT 8";
        XYChart.Series chart = new XYChart.Series();
        try {
            ResultSet rst = CrudUtil.execute(sql);
            while (rst.next()) {
                chart.getData().add(new XYChart.Data(rst.getString(1), rst.getInt(2)));
            }
            return chart;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
