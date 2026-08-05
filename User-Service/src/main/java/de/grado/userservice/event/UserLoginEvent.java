package de.grado.userservice.event;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class UserLoginEvent
{
    private String email;
    private String phone;
}
