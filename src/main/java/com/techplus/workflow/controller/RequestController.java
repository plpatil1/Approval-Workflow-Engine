package com.techplus.workflow.controller;

import com.techplus.workflow.config.JwtUtil;
import com.techplus.workflow.dto.CreateRequestDTO;
import com.techplus.workflow.entity.ApprovalHistory;
import com.techplus.workflow.entity.ApprovalStep;
import com.techplus.workflow.entity.Request;
import com.techplus.workflow.repository.ApprovalHistoryRepository;
import com.techplus.workflow.repository.ApprovalStepRepository;
import com.techplus.workflow.repository.RequestRepository;
import com.techplus.workflow.service.RequestService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private ApprovalStepRepository approvalStepRepository;

    @Autowired
    private ApprovalHistoryRepository approvalHistoryRepository;

    // 1 CREATE REQUEST (REQUESTER)
    @PostMapping
    public ResponseEntity<Map<String, Object>> createRequest(
            @RequestBody CreateRequestDTO dto
    ) {
        // temporary hardcoded user (until login exists)
        String username = "pawan";
        String role = "REQUESTER";

        // create request
        Request request = requestService.create(dto, username);

        // generate token
        String token = JwtUtil.generateToken(username, role);

        // response
        Map<String, Object> response = new HashMap<>();
        response.put("request", request);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }



    // 2 GET REQUEST DETAILS + CURRENT STEP
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getRequest(
            @PathVariable Long id,
            HttpServletRequest httpRequest
    ) {
        Claims claims = (Claims) httpRequest.getAttribute("claims");

        if (claims == null) {
            return ResponseEntity.status(401).build();
        }

        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        List<ApprovalStep> steps =
                approvalStepRepository.findByRequestTypeOrderByStepOrder(request.getType());

        int completedSteps =
                approvalHistoryRepository.findByRequestId(id).size();

        ApprovalStep currentStep =
                completedSteps < steps.size() ? steps.get(completedSteps) : null;

        Map<String, Object> response = new HashMap<>();
        response.put("request", request);
        response.put("currentStep", currentStep);

        return ResponseEntity.ok(response);
    }

    // 3 APPROVE CURRENT STEP (APPROVER / ADMIN)
    @PostMapping("/{id}/approve")
    public ResponseEntity<String> approveRequest(
            @PathVariable Long id,
            HttpServletRequest httpRequest
    ) {
        Claims claims = (Claims) httpRequest.getAttribute("claims");

        if (claims == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String username = claims.getSubject();
        String role = claims.get("role", String.class);

        requestService.approve(id, username, role);

        return ResponseEntity.ok("Request approved successfully");
    }


    // 4 REJECT REQUEST (APPROVER / ADMIN)
    @PostMapping("/{id}/reject")
    public ResponseEntity<String> rejectRequest(
            @PathVariable Long id,
            HttpServletRequest httpRequest
    ) {
        Claims claims = (Claims) httpRequest.getAttribute("claims");

        if (claims == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String username = claims.getSubject();
        String role = claims.get("role", String.class);

        requestService.reject(id, username, role);

        return ResponseEntity.ok("Request rejected successfully");
    }


    // 5️ GET APPROVAL HISTORY (REQUESTER / ADMIN)
    @GetMapping("/history/{id}")
    public ResponseEntity<List<ApprovalHistory>> getHistory(
            @PathVariable Long id,
            HttpServletRequest httpRequest
    ) {
        Claims claims = (Claims) httpRequest.getAttribute("claims");

        if (claims == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(
                approvalHistoryRepository.findByRequestId(id)
        );
    }

}
