package com.stubserver.backend.database.repository;

import com.stubserver.backend.database.entity.AssignedService;
import com.stubserver.backend.database.entity.AssignedServiceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AssignedServiceRepository extends JpaRepository<AssignedService, AssignedServiceId> {

    List<AssignedService> findByIdUsername(String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM AssignedService a WHERE a.id.username = :username")
    void deleteByIdUsername(@Param("username") String username);
}
