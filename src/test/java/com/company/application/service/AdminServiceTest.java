package com.company.application.service;

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

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @Test
    public void deleteUser() {
        User user = createUser();
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(1L);

        userRepository.save(user);

        User saved = userRepository.findById(user.getId()).get();
        userRepository.deleteById(saved.getId());
        verify(userRepository, times(1)).deleteById(1L);
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
