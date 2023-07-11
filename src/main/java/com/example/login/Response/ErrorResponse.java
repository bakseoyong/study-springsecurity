package com.example.login.Response;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {
    private Map<String, String> errors = new HashMap<>();

    public void addError(String field, String message){
        errors.put(field, message);
    }

    public Map<String, String> getErrors(){
        return errors;
    }
}
