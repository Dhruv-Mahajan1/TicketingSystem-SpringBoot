package com.example.ticketingsystem.dtos.request;


import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class NewUserRegisterationDto {


    @NotBlank(message = "Username is mandatory")
    String username;

    @NotBlank(message = "Password is mandatory")
    String password;
    @NotBlank(message = "Department is mandatory")
    String Department;
}
