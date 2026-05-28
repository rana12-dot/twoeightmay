package com.stubserver.backend.service;

import com.stubserver.backend.database.entity.AuditLog;
import com.stubserver.backend.exception.BadRequestException;
import com.stubserver.backend.database.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuditService {

    private static final Set<String> REMARK_ACTIONS = Set.of(
            "deploy", "re-deploy", "response delay", "dataset upload", "dataset delete"
    );

    private final AuditLogRepository auditLogRepo;

    public Map<String, Object> getAuditLogs() {
        List<AuditLog> rows = auditLogRepo.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
        List<Map<String, Object>> logs = rows.stream().map(a -> Map.<String, Object>of(
                "id", a.getId(),
                "user", a.getUsername() != null ? a.getUsername() : "",
                "serviceName", a.getServiceName() != null ? a.getServiceName() : "",
                "action", a.getActionType() != null ? a.getActionType() : "",
                "remark", a.getRemark() != null ? a.getRemark() : "",
                "timestamp", a.getTimestamp() != null ? a.getTimestamp() : ""
        )).toList();
        return Map.of("logs", logs);
    }

    public Map<String, String> logAudit(String username, String serviceName, String action, String remark) {
        if (username == null || serviceName == null || action == null) {
            throw new BadRequestException("Missing required fields");
        }
        String finalRemark = REMARK_ACTIONS.contains(action.toLowerCase()) ? (remark != null ? remark : "-") : "-";
        AuditLog log = new AuditLog();
        log.setUsername(username);
        log.setServiceName(serviceName);
        log.setActionType(action);
        log.setRemark(finalRemark);
        auditLogRepo.save(log);
        return Map.of("message", "Audit log inserted");
    }
}
