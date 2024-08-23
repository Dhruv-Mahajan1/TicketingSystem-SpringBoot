package com.example.ticketingsystem.repository;

import com.example.ticketingsystem.constants.Status;
import com.example.ticketingsystem.entity.Ticket;
import com.example.ticketingsystem.entity.User;
import com.example.ticketingsystem.entity.UserTicketMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserTicketMappingRepo extends JpaRepository<UserTicketMapping, Integer> {

    @Query("SELECT utm FROM UserTicketMapping utm WHERE utm.currentAssignedUser.userId = :userId AND utm.ticket.ticketId = :ticketId")
    Optional<UserTicketMapping> findByUserIdAndTicketId(@Param("userId") int userId, @Param("ticketId") int ticketId);


    @Query("SELECT utm FROM UserTicketMapping utm WHERE  utm.ticket.ticketId = :ticketId")
    Optional<List<UserTicketMapping>> findByTicketId(@Param("ticketId") int ticketId);


    @Query("SELECT utm FROM UserTicketMapping utm WHERE utm.currentAssignedUser.userId = :userId AND utm.status= :status1")
    Optional<List<UserTicketMapping>> findByUserIdByAssignmentStatus(@Param("userId") int userId,@Param("status1") Status status1);


    Optional<List<UserTicketMapping>> findByCurrentAssignedUser(User user);


}
