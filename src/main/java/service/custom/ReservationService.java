package service.custom;

import dto.Reservation;
import service.SuperService;

import java.util.List;

public interface ReservationService extends SuperService {
    boolean createNewReservation(Reservation reservation);

    Reservation getLastReservationData();

    List<Reservation> getAllReservations();
}
