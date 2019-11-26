package com.company.application.service;

import com.company.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminService {

    private final UserRepository userRepository;

    @Autowired
    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
