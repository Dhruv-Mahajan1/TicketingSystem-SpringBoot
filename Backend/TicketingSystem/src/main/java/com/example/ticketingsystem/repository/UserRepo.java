package com.example.ticketingsystem.repository;


import java.util.List;
import java.util.Optional;

import com.example.ticketingsystem.entity.Department;
import com.example.ticketingsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {


    Optional<User> findByUserName(String username);

    Optional<User> findById(int userId);

    Optional<List<User>> findByDepartment(Department department);
}

