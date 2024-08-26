package com.example.ticketingsystem.service;


import com.example.ticketingsystem.constants.Status;
import com.example.ticketingsystem.dtos.request.TicketRequestDTO;
import com.example.ticketingsystem.dtos.response.TicketResponseDTO;
import com.example.ticketingsystem.entity.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TicketService {

    TicketResponseDTO createTicket(TicketRequestDTO ticketRequestDTO) ;

    TicketResponseDTO getTicket(int ticketId) ;

    TicketResponseDTO updateTicket(int id,Boolean assigned,TicketRequestDTO TicketRequestDTO) ;


    void deleteTicket(int id) throws Exception;


    List<TicketResponseDTO>  filterTickets(Boolean status, Integer categoryId, Integer currentDepartmentId,Boolean owner,String status1);




}
