package com.example.ticketingsystem.dtos.response;


import com.example.ticketingsystem.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class TicketResponseDTO {

    private int id;
    private String title;
    private String description;
    private Timestamp createdAt;
    private int expectedTime;
    private String currentDepartment;
    private int createdBy;
    private Boolean status;
    private String category;
    private List<OtherUserResponseDto> currentAssignedUsers ;
}
