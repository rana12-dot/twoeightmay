package com.stubserver.backend.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "configurable.vsDetails")
public class VsDetails {

    @Id
    @Column(name = "VSNAME")
    private String vsname;

    @Column(name = "`GROUP`")
    private String group;

    @Column(name = "TAGS")
    private String tags;

    @Column(name = "DATASOURCEENABLED")
    private String datasourceEnabled;
}
