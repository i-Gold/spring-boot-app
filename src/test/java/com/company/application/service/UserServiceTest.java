package com.company.application.service;

import com.company.application.controller.request.UserUpdateRequest;
import com.company.application.model.Authority;
import com.company.application.model.Role;
import com.company.application.model.User;
import com.company.application.model.enumeration.AuthorityName;
import com.company.application.model.enumeration.RoleName;
import com.company.application.repository.RoleRepository;
import com.company.application.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @InjectMocks
    private UserService userService;

    @Test
    public void createUserAccountTest() {
        User user = createUser();
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        userService.createUserAccount(user);

        User saved = userRepository.findByUsername(user.getUsername()).orElse(null);

        assertNotNull(saved);
        assertEquals(user.getEmail(), saved.getEmail());
    }

    @Test
    public void editProfile() {
        User user = createUser();
        String oldUsername = user.getUsername();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setUsername("SuperJohnny21");
        when(userRepository.save(user)).thenReturn(user);

        User updated = userService.editProfile(updateRequest, user::getUsername);
        assertNotEquals(oldUsername, updated.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    private User createUser() {
        Authority authority = new Authority();
        authority.setName(AuthorityName.USA);
        Role role = new Role(RoleName.USER);
        role.setAuthorities(Collections.singleton(authority));

        when(roleRepository.findByName(RoleName.USER)).thenReturn(role);

        User user = new User();
        user.setId(1L);
        user.setEmail("john.doe@example.com");
        user.setPassword("1234K5678s");
        user.setUsername("johnny77");
        user.setRoles(Collections.singleton(role));
        user.setCountry("State of California");

        return user;
    }


}