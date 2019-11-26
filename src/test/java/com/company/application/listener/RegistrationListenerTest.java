package com.company.application.listener;

import com.company.application.event.OnRegistrationCompleteEvent;
import com.company.application.model.Authority;
import com.company.application.model.Role;
import com.company.application.model.User;
import com.company.application.model.VerificationToken;
import com.company.application.model.enumeration.AuthorityName;
import com.company.application.model.enumeration.RoleName;
import com.company.application.service.VerificationTokenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Collections;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationListenerTest {

    @Mock
    private VerificationTokenService verificationTokenService;
    @Mock
    private JavaMailSender mailSender;
    @Mock
    private VerificationToken testToken;
    @Mock
    private User user;
    @Spy
    @InjectMocks
    private RegistrationListener registrationListener;

    @Before
    public void setUp() {
        user = createUser();
        testToken.setUser(user);
        testToken.setToken(UUID.randomUUID().toString());
        doReturn(testToken).when(registrationListener).createVerificationToken(user);
        doNothing().when(mailSender).send(createTestMessage(user));
    }

    @Test
    public void onApplicationEventTest() {
        registrationListener.onApplicationEvent(new OnRegistrationCompleteEvent(user));

        verify(mailSender).send(createTestMessage(user));
        verify(registrationListener).createVerificationToken(user);
    }

    private User createUser() {
        Authority authority = new Authority();
        authority.setName(AuthorityName.USA);
        Role role = new Role(RoleName.USER);
        role.setAuthorities(Collections.singleton(authority));

        user = new User();
        user.setId(1L);
        user.setEmail("john.doe@example.com");
        user.setPassword("1234K5678s");
        user.setUsername("johnny77");
        user.setRoles(Collections.singleton(role));
        user.setCountry("State of California");

        return user;
    }

    public SimpleMailMessage createTestMessage(User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Registration Confirmation");
        email.setText("Please, run: http://localhost:5432/confirm/null");
        return email;
    }

}
