package com.stubserver.backend.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "configurable.assignedServices")
public class AssignedService {

    @EmbeddedId
    private AssignedServiceId id;

    public AssignedService(String username, String serviceName) {
        this.id = new AssignedServiceId(username, serviceName);
    }
}
