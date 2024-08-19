package com.example.ticketingsystem.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    int ticketId;

    @Column(name = "ticket_title")
    String ticketTitle;

    @Column(name = "ticket_description")
    String ticketDescription;

    @Column(name = "created_at")
    Timestamp createdAt;

    @Column(name = "expected_time")
    int expectedTime;


    @ManyToOne
    @JoinColumn(name = "current_department_id" ,referencedColumnName = "id")
    Department currentDepartment;


    @ManyToOne
    @JoinColumn(
            name = "created_by_user_id",
            referencedColumnName = "user_id"
    )
    private User createdByUser;

    @Column(name = "ticket_status")
    Boolean status;

    @ManyToOne
    @JoinColumn(name = "category_id" ,referencedColumnName = "id")
    CategoryMaster category;


    @OneToMany(
            mappedBy = "ticket",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    private List<UserTicketMapping> assignedtotickets;
}

