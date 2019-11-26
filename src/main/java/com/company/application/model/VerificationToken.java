package com.company.application.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "verification_token")
@Data
public class VerificationToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @NotNull
    private String token;

    @NotNull
    private LocalDateTime expiryDate = LocalDateTime.now().plusDays(1);

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private User user;

}