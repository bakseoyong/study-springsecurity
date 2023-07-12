package com.example.login.Auth;

public class RoleContextHolder {
    private static Role ROLE_USER;

    public static Role getRole() {
        return ROLE_USER;
    }

    public static void setRole(Role role) {
        ROLE_USER = role;
    }
}
