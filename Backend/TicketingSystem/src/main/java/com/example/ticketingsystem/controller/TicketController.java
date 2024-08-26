package com.example.ticketingsystem.controller;


import com.example.ticketingsystem.annotations.ValidateHeader;
import com.example.ticketingsystem.constants.Status;
import com.example.ticketingsystem.dtos.request.TicketRequestDTO;
import com.example.ticketingsystem.dtos.response.Response;
import com.example.ticketingsystem.dtos.response.TicketResponseDTO;
import com.example.ticketingsystem.entity.Ticket;
import com.example.ticketingsystem.entity.UserTicketMapping;
import com.example.ticketingsystem.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/api/v1/ticket"})
@ValidateHeader
public class TicketController {


    @Autowired
    TicketService ticketService;

    @PostMapping()
    public Response<TicketResponseDTO> createTicket(@Validated @RequestBody TicketRequestDTO createTicketRequestDTO)  {
        TicketResponseDTO createTicketResponseDTO=ticketService.createTicket(createTicketRequestDTO);
        return new Response<>(HttpStatus.OK.toString(),"Ticket Created Successfully",createTicketResponseDTO);
    }

    @GetMapping({"{id}"})
    public Response<TicketResponseDTO> getTicket(@PathVariable int id )  {

        TicketResponseDTO createTicketResponseDTO= ticketService.getTicket(id);
        return new Response<>(HttpStatus.OK.toString(),"Ticket found with id ",createTicketResponseDTO);
    }

    @PutMapping({"{id}"})
    public Response<TicketResponseDTO> updateTicket(@RequestBody TicketRequestDTO TicketRequestDTO,@PathVariable int id,@RequestParam Boolean owner )  {

        TicketResponseDTO TicketResponseDTO= ticketService.updateTicket(id, owner, TicketRequestDTO);
        return new Response<>(HttpStatus.OK.toString(),"Ticket updated Successfully",TicketResponseDTO);
    }

    @DeleteMapping({"{id}"})
    public Response<?> deleteTicket(@PathVariable int id ) throws Exception  {
        ticketService.deleteTicket(id);
        return new Response<>(HttpStatus.OK.toString(),"Ticket deleted Successfully",null);
    }


    @GetMapping("/filter")
    public Response<List< TicketResponseDTO>> filterTicket(
            @RequestParam(required = true) Boolean owner,
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer currentDepartmentId,
            @RequestParam(required = false) String mappingStatus

    ) {
        List< TicketResponseDTO>  ticketResponseDTOList= ticketService.filterTickets(status,categoryId,currentDepartmentId,owner, mappingStatus);
        return new Response<>(HttpStatus.OK.toString(), "Ticket found with provided filters", ticketResponseDTOList);
    }





    @GetMapping({"/reassign/{id}"})
    public Response<?> reassign(@RequestHeader int id )  {
        return new Response<>(HttpStatus.OK.toString(),"Ticket went to reassignment engine ",null);
    }


    @PutMapping({"/review/{id}"})
    public Response<?> review(@RequestHeader int id )  {
        return new Response<>(HttpStatus.OK.toString(),"Ticket went to review ",null);
    }





// review dont need i think
    // reassign ka meaning--> what will happen
    //

}
