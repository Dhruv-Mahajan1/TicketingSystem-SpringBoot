package com.example.ticketingsystem.controller;


import com.example.ticketingsystem.annotations.ValidateHeader;
import com.example.ticketingsystem.dtos.request.CommentRequestDto;
import com.example.ticketingsystem.dtos.request.TicketRequestDTO;
import com.example.ticketingsystem.dtos.response.CommentResponseDto;
import com.example.ticketingsystem.dtos.response.Response;
import com.example.ticketingsystem.dtos.response.TicketResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/v1/comment"})
@ValidateHeader
public class CommentController {

    @PostMapping()
    public Response<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto)  {
        CommentResponseDto commentResponseDto=new CommentResponseDto();
        return new Response<>(HttpStatus.OK.toString(),"Comment Created Successfully",commentResponseDto);
    }

    @GetMapping({"{commentId}"})
    public Response<CommentResponseDto> getComment(@PathVariable int commentId)  {
        CommentResponseDto commentResponseDto=new CommentResponseDto();
        return new Response<>(HttpStatus.OK.toString(),"Comment fetched Successfully",commentResponseDto);
    }

    @PutMapping({"{commentId}"})
    public Response<CommentResponseDto> updateComment(@PathVariable int commentId)  {
        CommentResponseDto commentResponseDto=new CommentResponseDto();
        return new Response<>(HttpStatus.OK.toString(),"Comment updated Successfully",commentResponseDto);
    }

    @DeleteMapping({"{commentId}"})
    public Response<CommentResponseDto> deleteComment(@PathVariable int commentId)  {
        CommentResponseDto commentResponseDto=new CommentResponseDto();
        return new Response<>(HttpStatus.OK.toString(),"Comment deleted Successfully",commentResponseDto);
    }






}
