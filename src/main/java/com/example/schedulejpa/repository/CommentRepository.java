package com.example.schedulejpa.repository;

import com.example.schedulejpa.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    long countByScheduleId(Long scheduleId);

    List<Comment> findByScheduleIdOrderByUpdatedAtDesc(Long scheduleId);

}
