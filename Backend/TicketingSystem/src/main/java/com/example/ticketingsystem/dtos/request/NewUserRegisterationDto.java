package com.example.ticketingsystem.dtos.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserRegisterationDto {

    String username;
    String password;
    String Department;
}
