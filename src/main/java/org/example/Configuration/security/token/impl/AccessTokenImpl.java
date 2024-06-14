package org.example.Configuration.security.token.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.Configuration.security.token.AccessToken;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode
@Getter
public class AccessTokenImpl implements AccessToken {
    private final String subject;
    private final Long studentId;
    private final Set<String> roles;

    public AccessTokenImpl(String subject, Long studentId, List<String> roles) {
        this.subject = subject;
        this.studentId = studentId;
        this.roles = roles != null ? Set.copyOf(roles) : Collections.emptySet();
    }

    @JsonIgnore
    public boolean hasRole(String roleName) {
        if (roles == null) {
            return false;
        }
        return roles.contains(roleName);
    }
}
