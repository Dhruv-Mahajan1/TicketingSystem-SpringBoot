package com.example.ticketingsystem.mapper;

import com.example.ticketingsystem.dtos.response.UserResponseDTO;
import com.example.ticketingsystem.entity.User;

import java.util.Date;

public class UserToUserResponseDto {

    public static UserResponseDTO convert(User user)
    {
        UserResponseDTO userResponseDTO =new UserResponseDTO();
        userResponseDTO.setUserId(user.getUserId());
        userResponseDTO.setUserName(user.getUserName());
        userResponseDTO.setDepartment(user.getDepartment().getName());
        userResponseDTO.setBusyTime(user.getBusyTime());
        userResponseDTO.setJoiningDate(user.getJoiningDate());

        return  userResponseDTO;

    }
}
