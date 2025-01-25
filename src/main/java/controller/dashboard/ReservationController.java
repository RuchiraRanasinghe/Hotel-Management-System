package controller.dashboard;

import model.Reservation;
import util.CrudUtil;

import java.sql.SQLException;

public class ReservationController {
    private static ReservationController instance;
    private ReservationController(){}

    public static ReservationController getInstance(){
        return instance == null ? instance=new ReservationController() : instance;
    }

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
