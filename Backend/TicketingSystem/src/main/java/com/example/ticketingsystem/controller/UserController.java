package com.example.ticketingsystem.controller;


import com.example.ticketingsystem.dtos.request.LoginRequestDto;
import com.example.ticketingsystem.dtos.request.NewUserRegisterationDto;
import com.example.ticketingsystem.dtos.response.UserResponseDTO;
import com.example.ticketingsystem.dtos.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/v1/user"})
public class UserController {
    @PostMapping({"/register"})
    public Response<UserResponseDTO> register(@RequestBody NewUserRegisterationDto userDetails)  {


        // some logic


        UserResponseDTO newUserResponseDTO = new UserResponseDTO();
        newUserResponseDTO.setUserName(userDetails.getUsername());
        return new Response<>(HttpStatus.OK.toString(),"User Created Successfully",newUserResponseDTO);
    }



    @PostMapping({"/login"})
    public Response<UserResponseDTO> login(@RequestBody LoginRequestDto loginRequestDto) {
        // some logic
        System.out.print(
                loginRequestDto.getUsername()
        );
        UserResponseDTO UserResponseDTO = new UserResponseDTO();
        return new Response<>(HttpStatus.OK.toString(),"User login successfully",UserResponseDTO);
    }


    
}
