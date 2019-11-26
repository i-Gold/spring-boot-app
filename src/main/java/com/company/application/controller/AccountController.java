package com.company.application.controller;

import com.company.application.controller.exception.TokenIsExpiredException;
import com.company.application.controller.exception.ValidationException;
import com.company.application.controller.request.UserRegistrationRequest;
import com.company.application.controller.request.UserUpdateRequest;
import com.company.application.model.User;
import com.company.application.model.VerificationToken;
import com.company.application.service.UserService;
import com.company.application.service.VerificationTokenService;
import com.company.application.validation.ValidationType;
import com.fasterxml.jackson.annotation.JsonView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private final ModelMapper modelMapper;

    @Autowired
    public AccountController(UserService userService, VerificationTokenService verificationTokenService, ModelMapper modelMapper) {
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/current")
    public String getPrincipal(Principal principal) {
        return principal.getName();
    }

    @PostMapping("/registration")
    @JsonView(User.View.Full.class)
    public ResponseEntity<User> registration(@RequestBody @Validated(ValidationType.RegistrationValidation.class) UserRegistrationRequest userRegistrationRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getFieldErrors());
        }
        User user = modelMapper.map(userRegistrationRequest, User.class);
        return ResponseEntity.ok(userService.createUserAccount(user));
    }

    @GetMapping("/confirm/{token}")
    @JsonView(User.View.Full.class)
    public ResponseEntity<User> confirm(@PathVariable String token) {
        VerificationToken verificationToken = verificationTokenService.getByToken(token);
        if (LocalDateTime.now().isAfter(verificationToken.getExpiryDate())) {
            throw new TokenIsExpiredException();
        }
        User enabledUser = userService.enableUserByToken(token);
        return ResponseEntity.ok(enabledUser);
    }

    @PutMapping("/edit-profile")
    @JsonView(User.View.Full.class)
    public ResponseEntity<User> editProfile(@RequestBody @Valid UserUpdateRequest userUpdateRequest, Principal principal) {
        return ResponseEntity.ok(userService.editProfile(userUpdateRequest, principal));
    }

}
