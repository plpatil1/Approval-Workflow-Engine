package com.techplus.workflow.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;       // LEAVE, EXPENSE
    private String status;     // PENDING, APPROVED, REJECTED

    private String createdBy;
    private LocalDateTime createdAt;

    private Integer currentStep;
}
