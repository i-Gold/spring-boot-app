package com.company.application.service;

import com.company.application.controller.request.UserUpdateRequest;
import com.company.application.event.OnRegistrationCompleteEvent;
import com.company.application.model.Role;
import com.company.application.model.User;
import com.company.application.model.enumeration.RoleName;
import com.company.application.repository.RoleRepository;
import com.company.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Collections;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.eventPublisher = eventPublisher;
    }

    public User createUserAccount(User user) {
        RoleName roleName = user.getRoles().stream()
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getName();
        addRoleToUser(user, roleName);
        user = userRepository.save(user);
        OnRegistrationCompleteEvent event = new OnRegistrationCompleteEvent(user);
        eventPublisher.publishEvent(event);
        return user;
    }

    private void addRoleToUser(User user, RoleName roleName) {
        Role role = roleRepository.findByName(roleName);
        user.setRoles(Collections.singleton(role));
    }

    public User enableUserByToken(String token) {
        User user = userRepository.findByVerificationToken(token);
        user.setEnabled(true);
        return user;
    }

    public User editProfile(UserUpdateRequest userUpdateRequest, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();
        if (userUpdateRequest.getUsername() != null) {
            user.setUsername(userUpdateRequest.getUsername());
        }
        if (userUpdateRequest.getCountry() != null) {
            user.setCountry(userUpdateRequest.getCountry());
        }
        return userRepository.save(user);
    }

}
