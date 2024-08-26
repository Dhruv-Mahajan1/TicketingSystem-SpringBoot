package com.example.ticketingsystem.controller;

import com.example.ticketingsystem.annotations.ValidateHeader;
import com.example.ticketingsystem.dtos.response.Response;
import com.example.ticketingsystem.dtos.response.TicketResponseDTO;

import com.example.ticketingsystem.service.TicketService;
import com.example.ticketingsystem.service.impl.TicketServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping({"/api/v2/ticket"})
@ValidateHeader
public class TicketControllerV2 {

    @Autowired
    TicketServiceV2 ticketServiceV2;


    @GetMapping("/filter")
    public Response<Page<TicketResponseDTO>> filterTicket(
            @RequestParam(required = true) Boolean owner,
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer currentDepartmentId,
            @RequestParam(required = false) String mappingStatus,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize

    ) {


        Page<TicketResponseDTO> ticketResponseDTOList= ticketServiceV2.filterTickets(status,categoryId,currentDepartmentId,owner, mappingStatus,pageNo, pageSize);
        return new Response<>(HttpStatus.OK.toString(), "Ticket found with provided filters", ticketResponseDTOList);
    }
}
