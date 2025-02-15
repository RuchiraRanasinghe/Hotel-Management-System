package entity;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class RoomEntity {
    private String roomNumber;
    private String roomType;
    private String status;
    private Double price;
}
