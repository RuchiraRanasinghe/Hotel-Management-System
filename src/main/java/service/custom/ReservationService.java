package service.custom;

import dto.Reservation;
import javafx.scene.chart.XYChart;
import service.SuperService;

import java.util.List;

public interface ReservationService extends SuperService {
    boolean createNewReservation(Reservation reservation);

    Reservation getLastReservationData();

    List<Reservation> getAllReservations();

    int getCountBookToday();

    Double getIncomeToday();

    Double getTotalIncome();

    XYChart.Series getChartData();

    boolean cancelReservation(Integer reservation);
}
