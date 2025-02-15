package service.custom.impl;

import dto.Reservation;
import service.custom.ReservationService;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {
    private static ReservationServiceImpl instance;
    private ReservationServiceImpl(){}

    public static ReservationServiceImpl getInstance(){
        return instance == null ? instance=new ReservationServiceImpl() : instance;
    }

    @Override
    public boolean createNewReservation(Reservation reservation) {
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
        }
    }

    @Override
    public Reservation getLastReservationData() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM reservations ORDER BY reservation_id DESC LIMIT 1");
            rst.next();
            return new Reservation(
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
    public List<Reservation> getAllReservations() {
        List<Reservation> reservationList = new ArrayList<>();
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM reservations");
            while (rst.next()){
                reservationList.add(new Reservation(
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
}
