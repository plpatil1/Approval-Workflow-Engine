package com.techplus.workflow.service.impl;

import com.techplus.workflow.dto.CreateRequestDTO;
import com.techplus.workflow.entity.Request;
import com.techplus.workflow.repository.RequestRepository;
import com.techplus.workflow.service.RequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public Request create(CreateRequestDTO dto, String user) {

        Request request = new Request();

        //  REQUIRED FIELDS (DO NOT SKIP)
        request.setType(dto.getType());          // LEAVE / EXPENSE
        request.setCreatedBy(user);              // pawan
        request.setStatus("PENDING");             // initial status
        request.setCurrentStep(0);                // workflow starts at step 0
        request.setCreatedAt(LocalDateTime.now()); // VERY IMPORTANT

        return requestRepository.save(request);
    }

    @Override
    public void approve(Long requestId, String user, String role) {
        // will handle later
    }

    @Override
    public void reject(Long requestId, String user, String role) {
        // will handle later
    }
}
