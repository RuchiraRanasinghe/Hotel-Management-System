package dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private int userId;
    private String username;
    private String password;
    private String role;
}
