package com.company.application.model;

import com.company.application.model.enumeration.RoleName;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Data
@EqualsAndHashCode(of = "name")
public class Role implements Serializable {

    private static final long serialVersionUID = 4846673203936802463L;

    @Id
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonView({User.View.Full.class})
    private RoleName name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_authorities",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
    private Set<Authority> authorities = new HashSet<>();

    public Role() {
    }

    public Role(RoleName roleName) {
        this.name = roleName;
    }

}