package com.example.ticketingsystem.entity;

import com.example.ticketingsystem.constants.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class UserTicketMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userTicketMappingId;

    @ManyToOne
    @JoinColumn(name = "current_assigned_user_id", referencedColumnName = "user_id")
    private User currentAssignedUser;

    @ManyToOne
    @JoinColumn(name = "ticket_id", referencedColumnName = "ticket_id")
    private Ticket ticket;

    @Column(name = "assigned_date")
    private Date assignedDate;

    @Enumerated(EnumType.STRING) // Maps the enum to a string column
    @Column(name = "status")
    private Status status;



}
