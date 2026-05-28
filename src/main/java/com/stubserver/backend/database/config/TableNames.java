package com.stubserver.backend.database.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "table")
@PropertySource(value = "classpath:db-tables.properties", ignoreResourceNotFound = true)
public class TableNames {

    // Deployment-specific table names — update db-tables.properties per server before deploying
    private String auditLogs = "STUBSERVERAUDITLOGS_QA";
    private String assignedServices = "STUBASSIGNEDSERVICES_QA";
    private String vsDetails = "STUBSERVERQA_VSDETAILS";
    private String masterCatalog = "STUBSERVER_MASTER_CATALOG_QA";
}
