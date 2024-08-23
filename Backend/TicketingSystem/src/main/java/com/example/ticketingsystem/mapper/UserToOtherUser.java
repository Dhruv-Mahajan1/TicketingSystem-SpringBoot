package com.example.ticketingsystem.mapper;

import com.example.ticketingsystem.dtos.response.OtherUserResponseDto;
import com.example.ticketingsystem.dtos.response.UserResponseDTO;
import com.example.ticketingsystem.entity.User;

public class UserToOtherUser {

    public static OtherUserResponseDto convert(User user)
    {
        OtherUserResponseDto otherUserResponseDto =new OtherUserResponseDto();
        otherUserResponseDto.setUserId(user.getUserId());
        otherUserResponseDto.setUserName(user.getUserName());
        otherUserResponseDto.setDepartment(user.getDepartment().getName());

        return  otherUserResponseDto;

    }
}
