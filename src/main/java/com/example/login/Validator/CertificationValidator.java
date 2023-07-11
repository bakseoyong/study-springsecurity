package com.example.login.Validator;

import com.example.login.VO.CertificationForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CertificationValidator implements Validator {
    private static final String PHONE_PATTERN = "\\d{3}-\\d{3,4}-\\d{4}";

    @Override
    public boolean supports(Class<?> clazz) {
        return CertificationForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CertificationForm certificationForm = (CertificationForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Blank.certificationForm.name", "이름을 입력해주세요.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "Blank.certificationForm.phone", "전화번호를 입력해주세요.");

        if (!errors.hasFieldErrors("phone")) {
            if (!certificationForm.getPhone().matches(PHONE_PATTERN)) {
                errors.rejectValue("phone", "Pattern.certificationForm.phone", "유효한 전화번호 형식이 아닙니다.");
            }
        }
    }
}