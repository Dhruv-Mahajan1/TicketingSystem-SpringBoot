package com.example.ticketingsystem.service.impl;

import com.example.ticketingsystem.constants.Status;
import com.example.ticketingsystem.dtos.request.TicketRequestDTO;
import com.example.ticketingsystem.dtos.response.OtherUserResponseDto;
import com.example.ticketingsystem.dtos.response.TicketResponseDTO;
import com.example.ticketingsystem.entity.*;
import com.example.ticketingsystem.exceptions.NoAuthroityException;
import com.example.ticketingsystem.exceptions.NotFoundException;
import com.example.ticketingsystem.mapper.TicketRequestDtoToTicket;
import com.example.ticketingsystem.mapper.TicketToTicketResponse;
import com.example.ticketingsystem.mapper.UserToOtherUser;
import com.example.ticketingsystem.repository.*;
import com.example.ticketingsystem.service.TicketService;
import com.example.ticketingsystem.util.AssignmentEngine;
import com.example.ticketingsystem.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service

public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketRepo ticketRepo;

    @Autowired
    CategoryMasterRepo categoryMasterRepo;

    @Autowired
    UserTicketMappingRepo userTicketMappingRepo;

    @Autowired
    DepartmentRepo departmentRepo;

    @Autowired
    UserRepo userRepo;


    @Override
    public TicketResponseDTO createTicket(TicketRequestDTO ticketRequestDTO) {

        Ticket ticket= TicketRequestDtoToTicket.convert(ticketRequestDTO);
        CategoryMaster categoryMaster = categoryMasterRepo.findById(ticketRequestDTO.getCategory()).orElseThrow(()->new NotFoundException("Category doesn't Exist"));


        ticket.setCategory(categoryMaster);
        ticket.setCurrentDepartment(categoryMaster.getDepartment());
        ticket.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        User user =UserContext.getUser();
        ticket.setCreatedByUser(user);

        ticketRepo.save(ticket);

        AssignmentEngine.assignTicket(ticket,userRepo,userTicketMappingRepo);

        List<UserTicketMapping> users=userTicketMappingRepo.findByTicketId(ticket.getTicketId()).orElseThrow(()-> new NotFoundException("assigned users not found"));
        List<OtherUserResponseDto> otherUserResponseDtoList=new ArrayList<>();
        TicketResponseDTO ticketResponseDTO= TicketToTicketResponse.convert(ticket);
        for (int i = 0; i < users.size(); i++) {
            OtherUserResponseDto otherUserResponseDto= UserToOtherUser.convert(users.get(i).getCurrentAssignedUser());
            otherUserResponseDto.setStatus(users.get(i).getStatus());
            otherUserResponseDtoList.add(otherUserResponseDto);

        }
        ticketResponseDTO.setCurrentAssignedUsers(otherUserResponseDtoList);
        ticketResponseDTO.setCreatedBy(ticket.getCreatedByUser().getUserId());


        return ticketResponseDTO;
    }

    @Override
    public TicketResponseDTO getTicket(int id) {

        Ticket ticket= ticketRepo.getByTicketId(id).orElseThrow(()->new NotFoundException(("Ticket not found")));
        if(ticket.getDeleted()) throw new NotFoundException("Ticket has been deleted");
        TicketResponseDTO ticketResponseDTO= TicketToTicketResponse.convert(ticket);
        List<UserTicketMapping> users=userTicketMappingRepo.findByTicketId(ticket.getTicketId()).orElseThrow(()-> new NotFoundException("assigned users not found"));
        List<OtherUserResponseDto> otherUserResponseDtoList=new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            OtherUserResponseDto otherUserResponseDto= UserToOtherUser.convert(users.get(i).getCurrentAssignedUser());
            otherUserResponseDto.setStatus(users.get(i).getStatus());
            otherUserResponseDtoList.add(otherUserResponseDto);

        }
        ticketResponseDTO.setCurrentAssignedUsers(otherUserResponseDtoList);
        return ticketResponseDTO;
    }


    @Override
    public TicketResponseDTO updateTicket(int id,Boolean owner,TicketRequestDTO ticketRequestDTO) {
        Ticket ticket=ticketRepo.getByTicketId(id).orElseThrow(()->new NotFoundException("Ticket not found"));
//        if(ticket.getDeleted()) throw new NotFoundException("Ticket has been deleted");
        User user=UserContext.getUser();
        if(owner) {
            if(ticket.getCreatedByUser()!=user) throw new NoAuthroityException("User has no authority to change the task");
            ticket.setTicketTitle(ticketRequestDTO.getTitle());
            ticket.setTicketDescription(ticketRequestDTO.getDescription());
            ticket.setExpectedTime(ticketRequestDTO.getExpectedTime());
            ticket.setStatus(ticketRequestDTO.getStatus());
            if(!Objects.equals(ticketRequestDTO.getCategory(), ticket.getCategory().getId())){


                CategoryMaster categoryMaster=categoryMasterRepo.findById(ticketRequestDTO.getCategory()).orElseThrow(()->  new NotFoundException("category not found"));
                ticket.setCategory(categoryMaster);

                Optional<List<UserTicketMapping>> userTicketMappingList=userTicketMappingRepo.findByTicketId(id);

                userTicketMappingList.ifPresent(userTicketMappings -> userTicketMappings.forEach(mapping -> {
                    mapping.setStatus(Status.DELETED_BY_OWNER);
                    userTicketMappingRepo.save(mapping);
                }));
                ticketRepo.save(ticket);
                AssignmentEngine.assignTicket(ticket,userRepo,userTicketMappingRepo);

            }
            else {
                ticketRepo.save(ticket);

            }

        }
        else{

            UserTicketMapping userTicketMapping=userTicketMappingRepo.findByUserIdAndTicketId(user.getUserId(), ticket.getTicketId())
                    .orElseThrow(()-> new NotFoundException("there is no such assigned task"));
            Status status = Status.valueOf(ticketRequestDTO.getTicketMappingStatus().toUpperCase());

            if(status==Status.DELETED_BY_OWNER) throw new NoAuthroityException("User has no permission to do so");
            userTicketMapping.setStatus(status);
            userTicketMappingRepo.save(userTicketMapping);
        }

        TicketResponseDTO ticketResponseDTO= TicketToTicketResponse.convert(ticket);
        List<UserTicketMapping> users=userTicketMappingRepo.findByTicketId(ticket.getTicketId()).orElseThrow(()-> new NotFoundException("assigned users not found"));
        List<OtherUserResponseDto> otherUserResponseDtoList=new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            OtherUserResponseDto otherUserResponseDto= UserToOtherUser.convert(users.get(i).getCurrentAssignedUser());
            otherUserResponseDto.setStatus(users.get(i).getStatus());
            otherUserResponseDtoList.add(otherUserResponseDto);

        }
        ticketResponseDTO.setCurrentAssignedUsers(otherUserResponseDtoList);
        return ticketResponseDTO;




    }

    @Override
    public void deleteTicket(int id) throws Exception {

        Ticket ticket=ticketRepo.getByTicketId(id).orElseThrow(()->new NotFoundException("Ticket not found"));
        User user=UserContext.getUser();
        if(ticket.getCreatedByUser()!=user) throw new NoAuthroityException("User has no authority to change the task");

        if(ticket.getDeleted()) throw new Exception("Task is already deleted");
        ticket.setDeleted(true);
        Optional<List<UserTicketMapping>> userTicketMappingList=userTicketMappingRepo.findByTicketId(id);
        userTicketMappingList.ifPresent(userTicketMappings -> userTicketMappings.forEach(mapping -> {
            mapping.setStatus(Status.DELETED_BY_OWNER);
            userTicketMappingRepo.save(mapping);

        }));




    }


    @Override
    public List<TicketResponseDTO> filterTickets(Boolean status, Integer categoryId, Integer currentDepartmentId,Boolean owner,String status1) {
        User user=UserContext.getUser();

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
        return  ticketResponseDTOList;
    }}



// FINDBYID; AVOID search by string /index  done
// paggination
// concurrent  req
// comments   done
