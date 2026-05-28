package com.stubserver.backend.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "configurable.auditLogs", indexes = {
    @Index(name = "idx_audit_log_timestamp", columnList = "TIMESTAMP"),
    @Index(name = "idx_audit_log_action_type", columnList = "ACTION_TYPE")
})
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "SERVICE_NAME")
    private String serviceName;

    @Column(name = "ACTION_TYPE")
    private String actionType;

    @Column(name = "REMARK")
    private String remark;

    // Populated automatically by the database — not set on insert
    @Column(name = "TIMESTAMP", insertable = false, updatable = false)
    private Timestamp timestamp;
}
