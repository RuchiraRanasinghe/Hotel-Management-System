package repository.custom;

import entity.ReservationEntity;
import repository.CrudDao;

public interface CheckInDao extends CrudDao<ReservationEntity,Integer> {
    String getNewReservationId();
}
