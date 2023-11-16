package org.springframework.bean;

import java.util.Arrays;

public class TestService {
    public void testPrint(String ...msg){
        System.out.println(Arrays.toString(msg));
    }
}
