package com.example.ticketingsystem.controller;


import com.example.ticketingsystem.dtos.request.LoginRequestDto;
import com.example.ticketingsystem.dtos.request.NewUserRegisterationDto;
import com.example.ticketingsystem.dtos.response.UserResponseDTO;
import com.example.ticketingsystem.dtos.response.Response;
import com.example.ticketingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/v1/user"})
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping({"/register"})
    public Response<UserResponseDTO> register(@Validated @RequestBody NewUserRegisterationDto userDetails) throws Exception {
        UserResponseDTO userResponseDTO=userService.registerNewUser(userDetails);
        return new Response<>(HttpStatus.OK.toString(),"User Created Successfully",userResponseDTO);
    }



    @PostMapping({"/login"})
    public Response<UserResponseDTO> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {

        UserResponseDTO UserResponseDTO = userService.authenticate(loginRequestDto);
        return new Response<>(HttpStatus.OK.toString(),"User login successfully",UserResponseDTO);
    }



}
