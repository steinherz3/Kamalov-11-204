package org.example.model;

import lombok.*;
import org.example.model.enums.UserRole;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    Long id;

    String username;

    String email;

    String hashPassword;

    UserRole role;
}
