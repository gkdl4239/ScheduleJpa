package com.example.schedulejpa.repository;

import com.example.schedulejpa.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    long countByScheduleId(Long scheduleId);

    List<Comment> findByScheduleId(Long scheduleId);

    default Comment findByIdOrElseThrow(Long id){
        return findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "존재하지 않는 댓글 입니다."
                        ));
    }


}
