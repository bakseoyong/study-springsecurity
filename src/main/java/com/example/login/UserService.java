package com.example.login;

import com.example.login.Dto.AuthUserDto;
import com.example.login.Dto.UserDto;
import com.example.login.Exception.SignUpFormNotUniqueException;
import com.example.login.VO.SignUpForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger("UserService");
    @Autowired
    private UserRepository userRepository;

    public boolean checkUsernameDuplication(String username){
        return !userRepository.findByUsername(username).isPresent();
    }

    public boolean checkEmailDuplication(String email){
        return !userRepository.findByEmail(email).isPresent();
    }

    public UserDto createUser(SignUpForm form){
        User user = new User(form.getUsername(), form.getPassword(), form.getEmail());

        try{
            userRepository.save(user);
        }catch (DataIntegrityViolationException e){
            logger.warn("회원가입 중 유니크 제약 조건 username : {}, email : {}", form.getUsername(), form.getEmail());
            throw new SignUpFormNotUniqueException("아이디 혹은 이메일이 중복됩니다.");
        }

        return new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getEmail());
    }

    public AuthUserDto authUser(String username, String password){
        Optional<User> user = userRepository.findByUsername(username);

        if(!user.isPresent()){
            return AuthUserDto.builder()
                    .isSuccess(false)
                    .message("유저가 존재하지 않습니다.").build();
        }else{
            if(!user.get().getPassword().equals(password)){
                return AuthUserDto.builder()
                    .isSuccess(false)
                    .message("비밀번호가 일치하지 않습니다.").build();
            }
        }
        return AuthUserDto.builder()
                .isSuccess(true)
                .message("로그인에 성공했습니다.").build();
    }

}
