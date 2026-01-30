package com.techplus.workflow.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "approval_history")
public class ApprovalHistory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long requestId;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private String actionBy;

    @Column(nullable = false)
    private LocalDateTime actionAt;

    // getters & setters
}

