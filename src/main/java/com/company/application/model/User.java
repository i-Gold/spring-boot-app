package com.company.application.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "email"}))
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "native", strategy = "native")
    @JsonView({View.Full.class, View.Part.class})
    private Long id;

    @JsonView({View.Full.class, View.Part.class})
    private String email;

    @NotNull
    @JsonView({View.Full.class, View.Part.class})
    private String username;

    private String password;

    @NotNull
    @JsonView({View.Full.class, View.Part.class})
    private String country;

    @Column(columnDefinition = "SMALLINT")
    private boolean accountExpired;

    @Column(columnDefinition = "SMALLINT")
    private boolean accountLocked;

    @Column(columnDefinition = "SMALLINT")
    private boolean credentialsExpired;

    @Column(columnDefinition = "SMALLINT")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    @JsonView({View.Full.class})
    private Set<Role> roles;

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .flatMap(role -> role.getAuthorities().stream())
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired();
    }

    public interface View {
        interface Full {
        }

        interface Part {
        }
    }

}
