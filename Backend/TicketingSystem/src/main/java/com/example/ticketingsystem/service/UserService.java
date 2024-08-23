package com.example.ticketingsystem.service;

import com.example.ticketingsystem.dtos.request.LoginRequestDto;
import com.example.ticketingsystem.dtos.request.NewUserRegisterationDto;
import com.example.ticketingsystem.dtos.response.Response;
import com.example.ticketingsystem.dtos.response.UserResponseDTO;
import org.springframework.stereotype.Service;



public interface UserService {

    UserResponseDTO registerNewUser(NewUserRegisterationDto userDetails) throws Exception;

    UserResponseDTO authenticate(LoginRequestDto loginRequestDto) throws Exception;

}
