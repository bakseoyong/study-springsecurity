package com.example.login;

import com.example.login.Dto.UserDto;
import com.example.login.Exception.SignUpFailedException;
import com.example.login.Exception.SignUpFormNotUniqueException;
import com.example.login.Request.CheckEmailVerificationCodeRequest;
import com.example.login.Request.CheckPhoneVerificationCodeRequest;
import com.example.login.Request.UserCreateRequest;
import com.example.login.Util.JsonUtil;
import com.example.login.VO.SignUpForm;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class SignUpService {
    RestTemplate restTemplate;
    JsonUtil jsonUtil;

    public SignUpService(RestTemplate restTemplate, JsonUtil jsonUtil) {
        this.restTemplate = restTemplate;
        this.jsonUtil = jsonUtil;
    }

    public boolean checkIdDuplication(String id){
        String url = "http://localhost:8080/users/id-duplication/{id}";

        return restTemplate.getForObject(url, Boolean.class, id);
    }

    public boolean checkPhoneAuthKey(String phone, String code){
        String url = "http://localhost:8080/sms/verify";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        CheckPhoneVerificationCodeRequest requestBody = new CheckPhoneVerificationCodeRequest(phone, code);

        HttpEntity<CheckPhoneVerificationCodeRequest> requestHttpEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestHttpEntity, Boolean.class);

        return responseEntity.getBody();
    }

    public boolean checkEmailAuthKey(String email, String code){
        String url = "http://localhost:8080/email/verify";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        CheckEmailVerificationCodeRequest requestBody = new CheckEmailVerificationCodeRequest(email, code);

        HttpEntity<CheckEmailVerificationCodeRequest> requestHttpEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestHttpEntity, Boolean.class);

        return responseEntity.getBody();
    }

    public boolean requestUserCreate(SignUpForm form){
        String url = "http://localhost:8080/users/create";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UserCreateRequest requestBody = new UserCreateRequest(form.getUsername(), form.getPassword(), form.getEmail());

        HttpEntity<UserCreateRequest> requestHttpEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, requestHttpEntity, Object.class);
        }catch (HttpClientErrorException e){
            if(e.getStatusCode() == HttpStatus.BAD_REQUEST){
                String bodyAsString = e.getResponseBodyAsString();

                String errorMessage = jsonUtil.findValueByKey(bodyAsString, "errorResponse");

                throw new SignUpFailedException(errorMessage);
            }
        }

        return true;
    }

}
