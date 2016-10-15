package com.e_buyad.marvin.e_buyad.business;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class Login {

    public static Map<String, String> isValid(Map<String, String> loginMap) {
        Map<String, String> detailsMap = new HashMap<>();
        String username = loginMap.get("username");
        String password = loginMap.get("password");

        if(TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
            detailsMap.put("status", "F");
            detailsMap.put("message", "Username and password field is empty");
        } else if(TextUtils.isEmpty(username)) {
            detailsMap.put("status", "F");
            detailsMap.put("message", "Username field is empty");
        } else if(TextUtils.isEmpty(password)) {
            detailsMap.put("status", "F");
            detailsMap.put("message", "Password field is empty");
        } else if(TextUtils.getTrimmedLength(password) < 4) {
            detailsMap.put("status", "F");
            detailsMap.put("message", "Password must be 4 digits");
        }else {
            detailsMap.put("status", "S");
            detailsMap.put("message", "Valid input");
        }

        return detailsMap;
    }
}
