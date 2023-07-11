package com.example.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
public class SMSController {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/sms/store/{key}/{value}")
    public String store(@PathVariable String key, @PathVariable String value){
        redisTemplate.opsForValue().set(key, value, 300, TimeUnit.SECONDS);
        return "Stored " + value + " with key " + key;
    }

    @GetMapping("/sms/retrieve/{key}")
    public Object retrieve(@PathVariable String key) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            throw new IllegalArgumentException();
        }
        return value;
    }

    @PostMapping("/sms/verify")
    public boolean verify(@RequestParam String key, @RequestParam String inputCode) {
        String value = (String) redisTemplate.opsForValue().get(key);
        return value != null && value.equals(inputCode);
    }
}
