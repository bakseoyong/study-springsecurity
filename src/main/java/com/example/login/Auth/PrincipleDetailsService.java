package com.example.login.Auth;

import com.example.login.User;
import com.example.login.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class PrincipleDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public PrincipleDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        if(user == null || !user.isEnabled()){
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new PrincipleDetails(user);
    }
}
