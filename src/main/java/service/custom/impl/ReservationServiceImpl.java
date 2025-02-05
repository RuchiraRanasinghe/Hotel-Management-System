package service.custom.impl;

import dto.Reservation;
import service.custom.ReservationService;
import util.CrudUtil;

import java.sql.SQLException;

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
}
