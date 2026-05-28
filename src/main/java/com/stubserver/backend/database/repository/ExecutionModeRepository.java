package com.stubserver.backend.database.repository;

import com.stubserver.backend.database.entity.ExecutionMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExecutionModeRepository extends JpaRepository<ExecutionMode, Long> {
    Optional<ExecutionMode> findByMasterIdAndVirtServer(Long masterId, String virtServer);
}
