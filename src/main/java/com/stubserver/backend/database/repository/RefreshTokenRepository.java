package com.stubserver.backend.database.repository;

import com.stubserver.backend.database.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByJti(String jti);

    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken r WHERE r.username = :username")
    void deleteAllByUsername(@Param("username") String username);
}
