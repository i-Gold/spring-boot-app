package com.company.application.repository;


import com.company.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT user FROM User user JOIN VerificationToken verificationToken ON verificationToken.user.id = user.id WHERE verificationToken.token = ?1")
    User findByVerificationToken(String token);

    boolean existsByEmail(String email);

}
