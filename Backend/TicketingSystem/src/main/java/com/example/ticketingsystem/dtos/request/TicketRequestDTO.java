package com.example.ticketingsystem.dtos.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TicketRequestDTO {


    private String title;

    private String description;

    private int expectedTime;

    private int currentDepartment;

    private int category;


    private String ticketMappingStatus;

    private Boolean status;


}
