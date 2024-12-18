package com.example.schedulejpa.repository;

import com.example.schedulejpa.dto.ScheduleResponseDto;
import com.example.schedulejpa.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    default Schedule findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "존재하지 않는 일정입니다."
                        ));
    }

    @Query( value= "SELECT new com.example.schedulejpa.dto.ScheduleResponseDto" +
            "(s.id,s.user.username,s.title,s.contents,s.createdAt,s.updatedAt, COUNT(c)) " +
            "FROM Schedule s " +
            "LEFT JOIN Comment c ON c.schedule.id = s.id " +
            "GROUP BY s")
    Page<ScheduleResponseDto> findAllScheduleAndCommentCount(Pageable pageable);
}
