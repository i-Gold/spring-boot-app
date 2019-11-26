package com.company.application.model;

import com.company.application.model.enumeration.AuthorityName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "authorities", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Authority implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthorityName name;

    @JsonIgnore
    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles;

    @Override
    public String getAuthority() {
        return getName().name();
    }

}
