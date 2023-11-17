package org.springframework.bean;

import java.util.HashMap;
import java.util.Map;

public class UserDao {

    private static Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("1", "a");
        hashMap.put("2", "b");
        hashMap.put("3", "c");
        hashMap.put("4", "d");
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }

}