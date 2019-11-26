package com.company.application.converter;

import com.company.application.controller.request.UserRegistrationRequest;
import com.company.application.model.Role;
import com.company.application.model.User;
import com.company.application.model.enumeration.RoleName;
import org.modelmapper.AbstractConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class FromUserRegistrationRequestToUserConverter extends AbstractConverter<UserRegistrationRequest, User> {

    private final PasswordEncoder userPasswordEncoder;

    @Autowired
    public FromUserRegistrationRequestToUserConverter(PasswordEncoder userPasswordEncoder) {
        this.userPasswordEncoder = userPasswordEncoder;
    }

    @Override
    protected User convert(UserRegistrationRequest userRegistrationRequest) {
        User user = new User();
        user.setEmail(userRegistrationRequest.getEmail());
        user.setPassword(userPasswordEncoder.encode(userRegistrationRequest.getPassword()));
        RoleName roleName = RoleName.valueOf(userRegistrationRequest.getRoleName());
        Role role = new Role(roleName);
        user.setRoles(Collections.singleton(role));
        user.setUsername(userRegistrationRequest.getUsername());
        user.setCountry(userRegistrationRequest.getCountry());
        return user;
    }

}
