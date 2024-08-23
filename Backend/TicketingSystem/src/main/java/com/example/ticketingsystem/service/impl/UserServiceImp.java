package com.example.ticketingsystem.service.impl;

import com.example.ticketingsystem.dtos.request.LoginRequestDto;
import com.example.ticketingsystem.dtos.request.NewUserRegisterationDto;
import com.example.ticketingsystem.dtos.response.Response;
import com.example.ticketingsystem.dtos.response.UserResponseDTO;
import com.example.ticketingsystem.entity.Department;
import com.example.ticketingsystem.entity.User;
import com.example.ticketingsystem.exceptions.NotFoundException;
import com.example.ticketingsystem.mapper.NewUserRegisterationDTotoUser;
import com.example.ticketingsystem.mapper.UserToUserResponseDto;
import com.example.ticketingsystem.repository.DepartmentRepo;
import com.example.ticketingsystem.repository.UserRepo;
import com.example.ticketingsystem.service.UserService;
import com.example.ticketingsystem.util.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    DepartmentRepo departmentRepo;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserResponseDTO registerNewUser(NewUserRegisterationDto userDetails) throws Exception {

        String hashedPassword = passwordEncoder.encode(userDetails.getPassword());
        userDetails.setPassword(hashedPassword);
        User user = NewUserRegisterationDTotoUser.convert(userDetails);
        Department department=departmentRepo.findByName(userDetails.getDepartment()).orElseThrow(()->new NotFoundException("Department Doesn't Exist"));
        user.setDepartment(department);
        User savedUser =userRepo.save(user);
        UserResponseDTO userResponseDTO=UserToUserResponseDto.convert(savedUser);
        userResponseDTO.setToken(Token.generateToken(savedUser));
        return userResponseDTO;
    }

    @Override
    public UserResponseDTO authenticate(LoginRequestDto loginRequestDto) throws Exception {
        User user = userRepo.findByUserName(loginRequestDto.getUsername()).orElseThrow(() -> {
            return new NotFoundException("User not found");
        });
        if (passwordEncoder.matches(loginRequestDto.getPassword(), user.getUserPassword())) {
            UserResponseDTO userResponseDTO = UserToUserResponseDto.convert(user);
            userResponseDTO.setToken(Token.generateToken(user));
            return userResponseDTO;
        }
        else {
            throw new NotFoundException("Invalid Credentials");
        }
    }

}
