package com.company.application.controller.request;

import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class UserUpdateRequest implements Serializable {

    @Size(min = 8, max = 16)
    private String username;

    @Size(min = 4)
    private String country;

}
