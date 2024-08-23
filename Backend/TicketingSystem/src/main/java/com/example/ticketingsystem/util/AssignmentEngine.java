package com.example.ticketingsystem.util;

import com.example.ticketingsystem.constants.Status;
import com.example.ticketingsystem.entity.Department;
import com.example.ticketingsystem.entity.Ticket;
import com.example.ticketingsystem.entity.User;
import com.example.ticketingsystem.entity.UserTicketMapping;
import com.example.ticketingsystem.exceptions.NotFoundException;
import com.example.ticketingsystem.repository.UserRepo;
import com.example.ticketingsystem.repository.UserTicketMappingRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AssignmentEngine {





    public static void assignTicket(Ticket ticket, UserRepo userRepo, UserTicketMappingRepo userTicketMappingRepo)
    {

        Department department=ticket.getCurrentDepartment();
        List<User> users= userRepo.findByDepartment(department).orElseThrow(()->
                new NotFoundException("No users are there in this department"));


        users = users.stream()
                .filter(user -> !user.equals(ticket.getCreatedByUser()))
                .collect(Collectors.toList());


        if (users.isEmpty()) {
            throw new NotFoundException("No eligible users to assign the ticket in this department");
        }

        users.sort(Comparator.comparing(User::getBusyTime));


        List<User> assignedUsers = users.stream().limit(2).toList();


        for (User user : assignedUsers) {
            UserTicketMapping userTicketMapping = new UserTicketMapping();
            userTicketMapping.setCurrentAssignedUser(user);
            userTicketMapping.setTicket(ticket);
            userTicketMapping.setAssignedDate(new Date());
            userTicketMapping.setStatus(Status.PROGRESS);
            userTicketMappingRepo.save(userTicketMapping);
            user.setBusyTime(user.getBusyTime()+(ticket.getExpectedTime()));
            userRepo.save(user);
        }


    }
}
