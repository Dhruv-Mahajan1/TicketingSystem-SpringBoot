package com.example.ticketingsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class CategoryMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "category_name")
    String name;

    @ManyToOne
    @JoinColumn(name = "department_id" ,referencedColumnName = "id")
    Department department;

    @OneToMany(mappedBy = "category" ,fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    List<Ticket> ticket;

}
