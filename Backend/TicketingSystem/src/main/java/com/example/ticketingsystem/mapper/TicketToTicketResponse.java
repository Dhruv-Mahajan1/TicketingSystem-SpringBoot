package com.example.ticketingsystem.mapper;

import com.example.ticketingsystem.dtos.response.TicketResponseDTO;
import com.example.ticketingsystem.entity.Ticket;

public class TicketToTicketResponse {

    public static TicketResponseDTO convert(Ticket ticket)
    {
        TicketResponseDTO ticketResponseDTO=new TicketResponseDTO();
        ticketResponseDTO.setId(ticket.getTicketId());
        ticketResponseDTO.setCategory(ticket.getCategory().getName());
        ticketResponseDTO.setCreatedAt(ticket.getCreatedAt());
        ticketResponseDTO.setDescription(ticket.getTicketDescription());
        ticketResponseDTO.setTitle(ticket.getTicketTitle());
        ticketResponseDTO.setStatus(ticket.getStatus());
        ticketResponseDTO.setExpectedTime(ticket.getExpectedTime());
        ticketResponseDTO.setCurrentDepartment(ticket.getCurrentDepartment().getName());
        return ticketResponseDTO;

    }
}
