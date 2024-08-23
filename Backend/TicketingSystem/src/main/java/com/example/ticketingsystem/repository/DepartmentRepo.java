package com.example.ticketingsystem.repository;

import com.example.ticketingsystem.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepo extends JpaRepository<Department,Integer> {


    Optional<Department> findByName(String name);
    Optional<Department> findById(int id);
}
