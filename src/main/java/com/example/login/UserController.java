package com.example.login;

import com.example.login.Dto.AuthUserDto;
import com.example.login.Dto.UserDto;
import com.example.login.Request.UserAuthRequest;
import com.example.login.VO.SignUpForm;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users/username-duplication/{username}")
    public ResponseEntity<Void> checkUsernameDuplication(@PathVariable String username){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/create")
    public ResponseEntity<UserDto> createUser(@RequestBody SignUpForm form){
        UserDto userDto = userService.createUser(form);

        return ResponseEntity
                .created(URI.create("/users/" + userDto.getId()))
                .body(userDto);
    }

    @PostMapping("/api/v1/users/auth")
    public ResponseEntity<AuthUserDto> authUser(@RequestBody UserAuthRequest request){
        AuthUserDto authUserDto = userService.authUser(request.getId(), request.getPassword());

        if(authUserDto.getId() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(authUserDto);
        }
        return ResponseEntity
                .ok()
                .body(authUserDto);
    }
}
