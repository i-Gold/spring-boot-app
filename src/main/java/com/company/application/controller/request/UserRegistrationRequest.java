package com.company.application.controller.request;

import com.company.application.model.enumeration.RoleName;
import com.company.application.validation.RetypePassword;
import com.company.application.validation.UniqueEmail;
import com.company.application.validation.ValidationType;
import com.company.application.validation.Enum;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@RetypePassword(groups = ValidationType.RegistrationValidation.class)
public class UserRegistrationRequest implements Serializable {

    @UniqueEmail(groups = ValidationType.RegistrationValidation.class)
    @Size(min = 10, groups = ValidationType.RegistrationValidation.class, message = "123")
    private String email;

    @Size(min = 8, max = 16)
    private String password;

    @Size(min = 8, max = 16)
    private String retypePassword;

    @Enum(enumClass = RoleName.class, groups = ValidationType.RegistrationValidation.class)
    private String roleName;

    @Size(min = 8, max = 16)
    private String username;

    @Size(min = 4)
    private String country;

}
