package com.example.login.Util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

@Component
public class JsonUtil {
    public String findValueByKey(String body, String key) {
        JsonObject jsonObject = (JsonObject) JsonParser.parseString(body);
        return jsonObject.get(key).getAsString();
    }
}
