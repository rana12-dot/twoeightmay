package com.stubserver.backend.database.repository;

import com.stubserver.backend.database.entity.VsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VsDetailsRepository extends JpaRepository<VsDetails, String> {

    Optional<VsDetails> findByVsname(String vsname);

    @Query("SELECT v FROM VsDetails v WHERE v.datasourceEnabled IS NOT NULL AND UPPER(v.datasourceEnabled) IN ('1', 'Y', 'TRUE')")
    List<VsDetails> findDatasourceEnabled();
}
