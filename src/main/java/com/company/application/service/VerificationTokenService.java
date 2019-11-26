package com.company.application.service;

import com.company.application.controller.exception.TokenDoesNotMatchException;
import com.company.application.model.VerificationToken;
import com.company.application.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public VerificationToken create(VerificationToken verificationToken) {
        return verificationTokenRepository.save(verificationToken);
    }

    @Transactional(readOnly = true)
    public VerificationToken getByToken(String token) {
        return verificationTokenRepository.findByToken(token).orElseThrow(TokenDoesNotMatchException::new);
    }

    @Scheduled(cron = "0 0 * ? * *")
    public void deleteAllOldTokens() {
        verificationTokenRepository.deleteAllOldTokens();
    }

}
