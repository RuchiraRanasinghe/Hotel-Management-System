package model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {
    private Integer customerId;
    private String customerNIC;
    private String name;
    private String phoneNumber;
    private Integer loyaltyPoints;
}
