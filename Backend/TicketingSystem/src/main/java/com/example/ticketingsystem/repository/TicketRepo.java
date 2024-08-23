package com.example.ticketingsystem.repository;

import java.util.List;
import java.util.Optional;


import com.example.ticketingsystem.entity.Ticket;
import com.example.ticketingsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Integer> {

    Optional<Ticket> getByTicketId(int id);
    Optional<List<Ticket>> getByCreatedByUser(User user);
}
