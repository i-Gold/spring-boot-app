package com.company.application.service;

import com.company.application.event.OnRegistrationCompleteEvent;
import com.company.application.model.Authority;
import com.company.application.model.Role;
import com.company.application.model.User;
import com.company.application.model.enumeration.AuthorityName;
import com.company.application.model.enumeration.RoleName;
import com.company.application.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Test
    public void createUserAccountTest() {
        User user = createUser();
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        userRepository.save(user);

        User saved = userRepository.findByUsername(user.getUsername()).orElse(null);

        OnRegistrationCompleteEvent event = new OnRegistrationCompleteEvent(user);
        doNothing().when(eventPublisher).publishEvent(event);
        eventPublisher.publishEvent(event);

        assertNotNull(saved);
        assertEquals(user.getEmail(), saved.getEmail());
    }

    private User createUser() {
        Authority authority = new Authority();
        authority.setName(AuthorityName.USA);
        Role role = new Role(RoleName.USER);
        role.setAuthorities(Collections.singleton(authority));

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