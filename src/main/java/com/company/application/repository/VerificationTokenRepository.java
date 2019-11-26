package com.company.application.repository;

import com.company.application.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM verification_token WHERE NOW() > verification_token.expiry_date")
    void deleteAllOldTokens();

}
