package com.example.login;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SMSService {
    private final RestTemplate restTemplate;

    public SMSService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean sendVerificationCode(String phoneNumber) {
        String url = "http://localhost:8080/sms/store/{key}/{value}";
        String code = generateVerificationCode();

        //메세지 전송 서비스에게 API 호출
        //성공했다고 가정

        String result =
                restTemplate.getForObject(url, String.class, phoneNumber, code);

        return true;
    }

    private String generateVerificationCode() {
        return "0000";
    }
}
