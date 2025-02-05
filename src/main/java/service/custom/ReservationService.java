package service.custom;

import dto.Reservation;
import service.SuperService;

public interface ReservationService extends SuperService {
    boolean createNewReservation(Reservation reservation);
}
