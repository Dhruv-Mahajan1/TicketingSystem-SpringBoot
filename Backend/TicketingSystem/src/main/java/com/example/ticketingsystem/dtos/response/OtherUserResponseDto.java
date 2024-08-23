package com.example.ticketingsystem.dtos.response;

import com.example.ticketingsystem.constants.Status;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OtherUserResponseDto {

    int  userId;
    String userName;
    String department;
    Status status;
}
