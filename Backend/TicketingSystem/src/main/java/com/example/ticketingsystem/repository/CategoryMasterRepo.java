package com.example.ticketingsystem.repository;


import com.example.ticketingsystem.entity.CategoryMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryMasterRepo extends JpaRepository<CategoryMaster,Integer> {

    Optional<CategoryMaster> findByName(String category);
}
