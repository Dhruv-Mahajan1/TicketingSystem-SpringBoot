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


    @Column(name = "deleted")
    Boolean deleted=false;


    @ManyToOne
    @JoinColumn(name = "comment_created_by_user_id", referencedColumnName = "user_id")
    private User commentCreator;

    @ManyToOne
    @JoinColumn(name = "ticket_id", referencedColumnName = "ticket_id")
    private Ticket ticket;





}
