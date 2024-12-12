package com.example.schedulejpa.entity;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;

@Getter
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity{

    @Id
    @Generated
    private Long id;

    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

}
