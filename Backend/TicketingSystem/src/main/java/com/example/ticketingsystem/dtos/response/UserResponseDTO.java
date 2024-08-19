package com.example.ticketingsystem.dtos.response;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class UserResponseDTO {
    int  userId;
    String userName;
    String department;
    Date joiningDate;
    int busyTime;
    String token;

}
