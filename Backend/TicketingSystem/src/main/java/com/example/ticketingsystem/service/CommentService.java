package com.example.ticketingsystem.service;

import com.example.ticketingsystem.dtos.request.CommentRequestDto;
import com.example.ticketingsystem.dtos.response.CommentResponseDto;

public interface CommentService {


    CommentResponseDto createComment(CommentRequestDto commentRequestDto) ;

    CommentResponseDto getComment(int commentId) ;

    CommentResponseDto updateComment(int id,CommentRequestDto commentRequestDto) ;

    void deleteComment(int id) throws Exception;




}
