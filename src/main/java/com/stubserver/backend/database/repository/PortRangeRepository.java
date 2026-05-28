package com.stubserver.backend.database.repository;

import com.stubserver.backend.database.entity.PortRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortRangeRepository extends JpaRepository<PortRange, Long> {
    boolean existsByAppName(String appName);
    Optional<PortRange> findTopByOrderByPortIdDesc();
}
