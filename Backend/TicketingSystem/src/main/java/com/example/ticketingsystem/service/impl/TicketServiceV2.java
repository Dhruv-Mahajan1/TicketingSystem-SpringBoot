package com.example.ticketingsystem.service.impl;


import com.example.ticketingsystem.constants.Status;
import com.example.ticketingsystem.dtos.response.OtherUserResponseDto;
import com.example.ticketingsystem.dtos.response.TicketResponseDTO;
import com.example.ticketingsystem.entity.*;
import com.example.ticketingsystem.exceptions.NotFoundException;
import com.example.ticketingsystem.mapper.TicketToTicketResponse;
import com.example.ticketingsystem.mapper.UserToOtherUser;
import com.example.ticketingsystem.repository.*;
import com.example.ticketingsystem.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketServiceV2 {

    @Autowired
    TicketRepo ticketRepo;

    @Autowired
    CategoryMasterRepo categoryMasterRepo;

    @Autowired
    UserTicketMappingRepo userTicketMappingRepo;

    @Autowired
    DepartmentRepo departmentRepo;



    public Page<TicketResponseDTO> filterTickets(Boolean status, Integer categoryId, Integer currentDepartmentId, Boolean owner, String status1,int pageNo,int pageSize) {
        User user= UserContext.getUser();
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        List<TicketResponseDTO>ticketResponseDTOList = new ArrayList<>();
        if(owner)
        {
            List<Ticket> tickets = ticketRepo.getByCreatedByUser(user).orElseThrow(()-> new NotFoundException("tickets not found"));

            if (status != null) {
                tickets = tickets.stream()
                        .filter(ticket -> ticket.getStatus().equals(status))
                        .collect(Collectors.toList());
            }

            if (categoryId!=null) {
                System.out.print("Dsdsds");
                CategoryMaster categoryMaster=categoryMasterRepo.findById(categoryId).orElseThrow(()->  new NotFoundException("category not found"));
                tickets = tickets.stream()
                        .filter(ticket -> ticket.getCategory().equals(categoryMaster))
                        .collect(Collectors.toList());
            }

            if (currentDepartmentId != null) {
                Department department=departmentRepo.findById(currentDepartmentId).orElseThrow(()->new NotFoundException("Department Doesn't Exist"));
                tickets = tickets.stream()
                        .filter(ticket -> ticket.getCurrentDepartment().equals(department))
                        .collect(Collectors.toList());
            }


            for (Ticket ticket : tickets) {

                TicketResponseDTO ticketResponseDTO = TicketToTicketResponse.convert(ticket);
                Optional<List<UserTicketMapping>> users = userTicketMappingRepo.findByTicketId(ticket.getTicketId());
                if (users.isEmpty()) {
                    ticketResponseDTOList.add(ticketResponseDTO);
                    continue;
                }

                List<OtherUserResponseDto> otherUserResponseDtoList = new ArrayList<>();

                for (int j = 0; j < users.get().size(); j++) {
                    OtherUserResponseDto otherUserResponseDto = UserToOtherUser.convert(users.get().get(j).getCurrentAssignedUser());
                    otherUserResponseDto.setStatus(users.get().get(j).getStatus());
                    otherUserResponseDtoList.add(otherUserResponseDto);

                }
                ticketResponseDTO.setCurrentAssignedUsers(otherUserResponseDtoList);
                ticketResponseDTOList.add(ticketResponseDTO);
            }


        }
        else
        {
            List<UserTicketMapping> assignedTickets;
            if(status1!=null) {
                Status status2 = Status.valueOf(status1.toUpperCase());
                assignedTickets = userTicketMappingRepo.findByUserIdByAssignmentStatus(UserContext.getUser().getUserId(),status2).orElseThrow(()->
                        new NotFoundException("user ticket mapping doesn't exist"));

            }
            else {
                assignedTickets=userTicketMappingRepo.findByCurrentAssignedUser(user).orElseThrow(()->new NotFoundException("No such tasks"));

            }


            for( UserTicketMapping userTicketMapping: assignedTickets)
            {
                Ticket ticket=userTicketMapping.getTicket();
                TicketResponseDTO ticketResponseDTO = TicketToTicketResponse.convert(ticket);
                Optional<List<UserTicketMapping>> users = userTicketMappingRepo.findByTicketId(ticket.getTicketId());
                if (users.isEmpty()) {
                    ticketResponseDTOList.add(ticketResponseDTO);
                    continue;
                }

                List<OtherUserResponseDto> otherUserResponseDtoList = new ArrayList<>();
                for (int j = 0; j < users.get().size(); j++) {
                    OtherUserResponseDto otherUserResponseDto = UserToOtherUser.convert(users.get().get(j).getCurrentAssignedUser());
                    otherUserResponseDto.setStatus(users.get().get(j).getStatus());
                    otherUserResponseDtoList.add(otherUserResponseDto);

                }
                ticketResponseDTO.setCurrentAssignedUsers(otherUserResponseDtoList);
                ticketResponseDTOList.add(ticketResponseDTO);
            }


        }
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), ticketResponseDTOList.size());


        List<TicketResponseDTO> pageContent = ticketResponseDTOList.subList(start, end);

        return new PageImpl<>(pageContent, pageable, ticketResponseDTOList.size());

    }

}
