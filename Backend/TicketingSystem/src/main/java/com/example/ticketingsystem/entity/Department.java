package com.example.ticketingsystem.entity;




import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name" ,unique = true)
    String name;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    List<User> user;



    @OneToMany(
            mappedBy = "department",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})

    List<CategoryMaster> categories;



    @OneToMany(
            mappedBy = "currentDepartment",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    List<Ticket> tickets;

}


// think about controller
// endpoints --> request and response
