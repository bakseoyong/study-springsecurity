package com.example.login;

import com.example.login.Auth.Role;
import com.example.login.Auth.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserCreateTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void ROLE_USER_캐시_들어갔는지_확인(){
        Role role = roleService.getUserRole();

        Assertions.assertEquals(role.getName(), "ROLE_USER");
        Assertions.assertEquals(role.getId(), 1L);
    }

}

