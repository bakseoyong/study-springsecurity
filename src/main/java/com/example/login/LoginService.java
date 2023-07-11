package com.example.login;

import com.example.login.Dto.AuthUserDto;
import com.example.login.Exception.SignUpFailedException;
import com.example.login.Request.UserAuthRequest;
import com.example.login.Request.UserCreateRequest;
import com.example.login.Util.JsonUtil;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService {
    private RestTemplate restTemplate;
    private JsonUtil jsonUtil;

    public LoginService(RestTemplate restTemplate, JsonUtil jsonUtil) {
        this.restTemplate = restTemplate;
        this.jsonUtil = jsonUtil;
    }

    public AuthUserDto sendAuthenticationRequest(String id, String password){
        AuthUserDto authUserDto = new AuthUserDto(null, null);
        String url = Constant.API_SERVER_ADDR + "/api/v1/users/auth";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UserAuthRequest requestBody = new UserAuthRequest(id, password);

        HttpEntity<UserAuthRequest> requestHttpEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<AuthUserDto> responseEntity = restTemplate.postForEntity(url, requestHttpEntity, AuthUserDto.class);

            authUserDto = responseEntity.getBody();
            return authUserDto;
        }catch (HttpClientErrorException e){
            if(e.getStatusCode() == HttpStatus.BAD_REQUEST){
                String bodyAsString = e.getResponseBodyAsString();

                String errorMessage = jsonUtil.findValueByKey(bodyAsString, "message");

                authUserDto =  new AuthUserDto(null, errorMessage);
                return authUserDto;
            }
        }
        return authUserDto;
    }
}
