package com.company.application.listener;

import com.company.application.event.OnRegistrationCompleteEvent;
import com.company.application.model.User;
import com.company.application.model.VerificationToken;
import com.company.application.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final VerificationTokenService verificationTokenService;
    private final JavaMailSender mailSender;

    @Autowired
    public RegistrationListener(VerificationTokenService verificationTokenService, JavaMailSender mailSender) {
        this.verificationTokenService = verificationTokenService;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        VerificationToken verificationToken = createVerificationToken(user);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = "http://localhost:5432/confirm/" + verificationToken.getToken();
        String message = "Please, run: ";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + confirmationUrl);
        mailSender.send(email);
    }

    private VerificationToken createVerificationToken(User user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(UUID.randomUUID().toString());
        return verificationTokenService.create(verificationToken);
    }

}