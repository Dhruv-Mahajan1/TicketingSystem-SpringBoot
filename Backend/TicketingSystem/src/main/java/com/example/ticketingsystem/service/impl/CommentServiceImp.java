package com.example.ticketingsystem.service.impl;

import com.example.ticketingsystem.dtos.request.CommentRequestDto;
import com.example.ticketingsystem.dtos.response.CommentResponseDto;
import com.example.ticketingsystem.dtos.response.TicketResponseDTO;
import com.example.ticketingsystem.entity.Comments;
import com.example.ticketingsystem.entity.Ticket;
import com.example.ticketingsystem.entity.User;
import com.example.ticketingsystem.entity.UserTicketMapping;
import com.example.ticketingsystem.exceptions.NoAuthroityException;
import com.example.ticketingsystem.exceptions.NotFoundException;
import com.example.ticketingsystem.repository.CommentRepo;
import com.example.ticketingsystem.repository.TicketRepo;
import com.example.ticketingsystem.repository.UserTicketMappingRepo;
import com.example.ticketingsystem.service.CommentService;
import com.example.ticketingsystem.util.UserContext;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    TicketRepo ticketRepo;

    @Autowired
    CommentRepo commentRepo;

    @Override
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        int ticketId=commentRequestDto.getTicketId();
        Ticket ticket =ticketRepo.getByTicketId(ticketId).orElseThrow(()-> new NotFoundException("Ticket not found"));

        Comments comments=new Comments();
        comments.setCommentDescription(commentRequestDto.getDescription());
        comments.setTicket(ticket);
        comments.setCommentCreator(UserContext.getUser());

        commentRepo.save(comments);
        return new CommentResponseDto(UserContext.getUser().getUserName(),comments.getCommentDescription());
    }

    @Override
    public CommentResponseDto getComment(int commentId) {
        Comments comments=commentRepo.findByCommentId(commentId).orElseThrow(()->
                new NotFoundException("Comment is not available"));
        return new CommentResponseDto(UserContext.getUser().getUserName(),comments.getCommentDescription());
    }

    @Override
    public CommentResponseDto updateComment(int commentId, CommentRequestDto commentRequestDto) {


        User user=UserContext.getUser();
        Comments comments=commentRepo.findByCommentId(commentId).orElseThrow(()->
                new NotFoundException("Comment doesn't exit"));

        if(comments.getCommentCreator()!=user) throw new NoAuthroityException("User is not the owner of the comment");
        comments.setCommentDescription(commentRequestDto.getDescription());
        commentRepo.save(comments);

        return new CommentResponseDto(UserContext.getUser().getUserName(),comments.getCommentDescription());
    }

    @Override
    public void deleteComment(int commentId) throws Exception {
        User user=UserContext.getUser();
        Comments comments=commentRepo.findByCommentId(commentId).orElseThrow(()->
                new NotFoundException("Comment doesn't exit"));

        if(comments.getCommentCreator()!=user) throw new NoAuthroityException("User is not the owner of the comment");
        comments.setDeleted(true);
        commentRepo.save(comments);
    }
}
