package com.example.ticketingsystem.repository;

import com.example.ticketingsystem.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.stream.events.Comment;
import java.util.Optional;

public interface CommentRepo extends JpaRepository<Comments,Integer> {
    Optional<Comments> findByCommentId(int commentId);
}
