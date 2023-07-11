package com.example.login;

import com.example.login.Exception.SignUpFailedException;
import com.example.login.Request.UserAuthRequest;
import com.example.login.Request.UserCreateRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService {
    private RestTemplate restTemplate;

    public LoginService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendAuthenticationRequest(String id, String password){
        String url = Constant.API_SERVER_ADDR + "/api/v1/users/auth";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UserAuthRequest requestBody = new UserAuthRequest(id, password);

        HttpEntity<UserAuthRequest> requestHttpEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, requestHttpEntity, Object.class);
        }catch (HttpClientErrorException e){
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                String bodyAsString = e.getResponseBodyAsString();

                String errorMessage = jsonUtil.findValueByKey(bodyAsString, "errorResponse");

                throw new SigninFailedException(errorMessage);
            }
        }

        return true;
    }
}
