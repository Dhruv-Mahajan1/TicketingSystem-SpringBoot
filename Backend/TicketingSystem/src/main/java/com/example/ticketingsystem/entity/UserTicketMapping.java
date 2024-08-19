package com.example.ticketingsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@IdClass(UserTicketMapping.class)
public class UserTicketMapping {


    @Id
    @ManyToOne
    @JoinColumn(name = "current_assigned_user_id" , referencedColumnName = "user_id")
    private User currentAssignedUser;

    @Id
    @ManyToOne
    @JoinColumn(name = "ticket_id" ,referencedColumnName = "ticket_id")
    Ticket ticket;

    @Column(name = "assigned_date")
    Date assignedDate;

    @Column(name = "active")
    Boolean active;



}
