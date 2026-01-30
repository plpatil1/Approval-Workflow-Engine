package com.techplus.workflow.service;


import com.techplus.workflow.dto.CreateRequestDTO;
import com.techplus.workflow.entity.Request;

public interface RequestService {
    Request create(CreateRequestDTO dto, String user);
    void approve(Long requestId, String user, String role);
    void reject(Long requestId, String user, String role);


}
