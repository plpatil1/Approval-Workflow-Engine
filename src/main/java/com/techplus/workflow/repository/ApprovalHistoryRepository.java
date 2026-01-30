package com.techplus.workflow.repository;

import com.techplus.workflow.entity.ApprovalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalHistoryRepository extends JpaRepository<ApprovalHistory, Long> {
    List<ApprovalHistory> findByRequestId(Long requestId);
}
