package dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reservation {
    private Integer reservationId;
    private String customerNIC;
    private String roomNumber;
    private String checkInDate;
    private String checkOutDate;
    private Double totalAmount;
    private String reservationStatus;
}
