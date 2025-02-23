package repository.custom;

import entity.ReservationEntity;
import javafx.scene.chart.XYChart;
import repository.CrudDao;

public interface ReservationDao extends CrudDao<ReservationEntity,Integer> {
    ReservationEntity getLastReservationData();
    int getCountBookToday();
    Double getIncomeToday();
    Double getTotalIncome();
    XYChart.Series getChartData();
    boolean cancelReservation(Integer reservationId);
}
