package com.example.ticketingsystem.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    int commentId;

    @Column(name = "comment_description")
    String commentDescription;


    @ManyToOne
    @JoinColumn(name = "user_ticket_mapping_id" ,referencedColumnName = "userTicketMappingId")
    UserTicketMapping userTicketMapping;






}
