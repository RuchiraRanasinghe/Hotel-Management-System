package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Room {
    private String roomNumber;
    private String roomType;
    private String status;
    private Double price;
}
