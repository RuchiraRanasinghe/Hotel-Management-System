package service.custom.impl;

import dto.Reservation;
import entity.ReservationEntity;
import javafx.scene.chart.XYChart;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.ReservationDao;
import service.custom.ReservationService;
import util.DaoType;

import java.util.ArrayList;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {
    private static ReservationServiceImpl instance;
    private ReservationServiceImpl(){}

    public static ReservationServiceImpl getInstance(){
        return instance == null ? instance=new ReservationServiceImpl() : instance;
    }

    ReservationDao reservationDao = DaoFactory.getInstance().getDao(DaoType.RESERVATION);

    @Override
    public boolean createNewReservation(Reservation reservation) {
        return reservationDao.save(new ModelMapper().map(reservation, ReservationEntity.class));
    }

    @Override
    public Reservation getLastReservationData() {
        return new ModelMapper().map(reservationDao.getLastReservationData(), Reservation.class);
    }

    @Override
    public List<Reservation> getAllReservations() {
        List<Reservation> reservationList = new ArrayList<>();
        for (ReservationEntity reservationEntity : reservationDao.getAll()) {
            reservationList.add(new ModelMapper().map(reservationEntity, Reservation.class));
        }
        return reservationList;
    }

    @Override
    public int getCountBookToday() {
        return reservationDao.getCountBookToday();
    }

    @Override
    public Double getIncomeToday() {
        return reservationDao.getIncomeToday();
    }

    @Override
    public Double getTotalIncome() {
        return reservationDao.getTotalIncome();
    }

    @Override
    public XYChart.Series getChartData() {
        return reservationDao.getChartData();
    }

    @Override
    public boolean cancelReservation(Integer reservationId) {
        return reservationDao.cancelReservation(reservationId);
    }
}
