package com.stubserver.backend.database.repository;

import com.stubserver.backend.database.entity.MasterCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterCatalogRepository extends JpaRepository<MasterCatalog, String> {

    boolean existsByVsname(String vsname);

    boolean existsByPort(String port);
}
