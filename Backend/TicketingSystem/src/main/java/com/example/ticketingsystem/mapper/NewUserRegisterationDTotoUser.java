package com.example.ticketingsystem.mapper;

import com.example.ticketingsystem.dtos.request.NewUserRegisterationDto;
import com.example.ticketingsystem.entity.User;

import java.sql.Timestamp;

public class NewUserRegisterationDTotoUser {


    public static User convert(NewUserRegisterationDto userdetails)
    {
        User user =new User();
        user.setUserName(userdetails.getUsername());
        user.setUserPassword(userdetails.getPassword());
        user.setJoiningDate(new Timestamp(System.currentTimeMillis()));
        return user;
    }

}
