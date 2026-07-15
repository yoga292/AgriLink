package com.cognizant.agrilink.iam.identityAccess.controller;

import com.cognizant.agrilink.iam.identityAccess.model.AuditLog;
import com.cognizant.agrilink.iam.identityAccess.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agriLink/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditLogRepository auditLogRepository;

    // GET /agriLink/audit  — list all audit logs
    // Secured (AgriLinkAdmin, ComplianceAnalyst) in SecurityConfig.
    @GetMapping
    public ResponseEntity<List<AuditLog>> getAllAuditLogs() {
        return ResponseEntity.ok(auditLogRepository.findAll());
    }
}
