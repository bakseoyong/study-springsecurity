package com.example.login.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void init(){
        Role role = roleRepository.findByName("ROLE_USER");
        if(role == null){
//            throw new IllegalStateException("ROLE_USER가 존재하지 않습니다.");
        }

        RoleContextHolder.setRole(role);
    }
}
