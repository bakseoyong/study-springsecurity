package com.example.login.Validator;

import com.example.login.UserService;
import com.example.login.VO.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpValidator implements Validator {

    private final UserService userService;
    private static final String EMAIL_PATTERN = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpForm signUpForm = (SignUpForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Blank.signupForm.username", "아이디를 입력해주세요.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Blank.signupForm.password", "비밀번호를 입력해주세요.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Blank.signupForm.email", "이메일을 입력해주세요.");

        if (!errors.hasFieldErrors("username")) {
            if(!userService.checkUsernameDuplication(signUpForm.getUsername())){
               errors.rejectValue("username", "Duplication.signUpForm.username", "중복된 아이디 입니다.");
            }
            if (signUpForm.getUsername().length() < 4 || signUpForm.getUsername().length() > 16) {
                errors.rejectValue("username", "Size.signUpForm.username", "유효하지 않은 아이디 형식입니다.");
            }
        }

        if (!errors.hasFieldErrors("password")) {
            if (!signUpForm.getPassword().matches(PASSWORD_PATTERN)) {
                errors.rejectValue("password", "Pattern.signUpForm.password", "유효하지 않은 비밀번호 형식입니다.");
            }
        }

        if (!errors.hasFieldErrors("email")) {
            if (!userService.checkEmailDuplication(signUpForm.getEmail())) {
                errors.rejectValue("email", "Duplication.signUpForm.email", "중복된 이메일 입니다.");
            }
            if (!signUpForm.getEmail().matches(EMAIL_PATTERN)) {
                errors.rejectValue("email", "Pattern.signUpForm.email", "유효하지 않은 이메일 형식입니다.");
            }
        }
    }
}
