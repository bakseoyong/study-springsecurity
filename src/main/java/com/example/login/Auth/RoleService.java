package com.example.login.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    //Cached
    private Role userRole;

    public Role getUserRole(){
        if(userRole == null){
            Role role = roleRepository.findByName("ROLE_USER");
            if(role == null){
                throw new IllegalStateException("ROLE_USER가 존재하지 않습니다.");
            }
            this.userRole = role;
        }

        return userRole;
    }
}
