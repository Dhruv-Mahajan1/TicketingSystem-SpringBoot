package com.example.ticketingsystem.exceptions;

public class NoAuthroityException extends RuntimeException {

    public NoAuthroityException (String message){
        super(message);
    }
}
