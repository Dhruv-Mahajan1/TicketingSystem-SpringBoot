package com.example.ticketingsystem.entity.key;


import com.example.ticketingsystem.entity.Ticket;
import com.example.ticketingsystem.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class UserTicketMappingId implements Serializable {

    private User currentAssignedUser;
    private Ticket ticket;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTicketMappingId that = (UserTicketMappingId) o;
        return Objects.equals(currentAssignedUser, that.currentAssignedUser) &&
                Objects.equals(ticket, that.ticket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentAssignedUser, ticket);
    }

}
