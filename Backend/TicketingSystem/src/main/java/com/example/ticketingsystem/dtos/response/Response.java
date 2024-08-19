package com.example.ticketingsystem.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Response<T> {

    String status;
    private String message;
    private T data;
}
