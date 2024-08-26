package com.example.ticketingsystem.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    int userId;


    @Column(name = "userName", unique = true)
    String userName;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "joining_date")
    Date joiningDate;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;



    @Column(name = "busy_time")
    int busyTime;



    @OneToMany(
            mappedBy = "createdByUser",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    private List<Ticket> createdTickets;



    @OneToMany(
            mappedBy = "currentAssignedUser",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    private List<UserTicketMapping> assignedTickets;



    @OneToMany(
            mappedBy = "commentCreator",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    private List<Comments> comments;






}