package com.example.ticketingsystem.mapper;

import com.example.ticketingsystem.dtos.request.TicketRequestDTO;
import com.example.ticketingsystem.dtos.response.TicketResponseDTO;
import com.example.ticketingsystem.entity.Ticket;

public class TicketRequestDtoToTicket {
    public static Ticket convert(TicketRequestDTO ticketRequestDTO)
    {
        Ticket ticket=new Ticket();
        ticket.setTicketTitle(ticketRequestDTO.getTitle());
        ticket.setTicketDescription(ticketRequestDTO.getDescription());
        ticket.setExpectedTime(ticketRequestDTO.getExpectedTime());
        return ticket;
    }
}
