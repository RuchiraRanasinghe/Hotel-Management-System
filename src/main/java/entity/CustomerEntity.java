package entity;

import jakarta.persistence.Entity;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class CustomerEntity {
    private Integer customerId;
    private String customerNIC;
    private String name;
    private String phoneNumber;
    private Integer loyaltyPoints;
}
