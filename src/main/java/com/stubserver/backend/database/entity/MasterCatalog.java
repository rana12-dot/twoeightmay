package com.stubserver.backend.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "configurable.masterCatalog")
public class MasterCatalog {

    @Id
    @Column(name = "VSNAME")
    private String vsname;

    @Column(name = "PORT")
    private String port;
}
