package com.sn.org.spboot3.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    MODER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
