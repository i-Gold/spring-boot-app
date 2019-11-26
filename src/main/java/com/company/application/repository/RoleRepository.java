package com.company.application.repository;

import com.company.application.model.Role;
import com.company.application.model.enumeration.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(RoleName name);

}
