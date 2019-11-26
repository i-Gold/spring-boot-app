package com.company.application.repository;

import com.company.application.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Long, Authority> {
}
