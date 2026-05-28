package com.stubserver.backend.database.repository;

import com.stubserver.backend.database.entity.LiveUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LiveUrlRepository extends JpaRepository<LiveUrl, Long> {
    List<LiveUrl> findByVsid(Long vsid);
    boolean existsByVsidAndHost(Long vsid, String host);
    Optional<LiveUrl> findTopByOrderByVsurlIdDesc();
}
