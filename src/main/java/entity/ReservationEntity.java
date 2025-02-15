package entity;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class ReservationEntity {
    private Integer reservationId;
    private String customerNIC;
    private String roomNumber;
    private String checkInDate;
    private String checkOutDate;
    private Double totalAmount;
    private String reservationStatus;
}
