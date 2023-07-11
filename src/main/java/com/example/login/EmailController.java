package com.example.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
public class EmailController {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/email/store/{key}/{value}")
    public String store(@PathVariable String key, @PathVariable String value){
        redisTemplate.opsForValue().set(key, value, 300, TimeUnit.SECONDS);
        return "Stored " + value + " with key " + key;
    }

    @GetMapping("/email/retrieve/{key}")
    public String retrieve(@PathVariable String key){
        String value = (String) redisTemplate.opsForValue().get(key);
        if (value == null) {
            throw new IllegalArgumentException();
        }
        return value;
    }

    @PostMapping("/email/verify")
    public ResponseEntity<Boolean> verify(@RequestParam String key, @RequestParam String inputCode) {
        String value = (String) redisTemplate.opsForValue().get(key);
        boolean isValid = value != null && value.equals(inputCode);

        return ResponseEntity.ok(isValid);
    }
}
