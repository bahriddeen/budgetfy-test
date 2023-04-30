package com.budgetfy.app.model.base;

import com.budgetfy.app.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role extends BaseEntity implements GrantedAuthority {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleType role;

    @Column(nullable = false)
    private boolean active;

    @Override
    public String getAuthority() {
        return role.name();
    }
}
