package org.springframework.bean;

import java.util.Arrays;

public class TestService {

    String arg1;
    String arg2;
    Integer arg3;

    public TestService() {
    }

    public TestService(String arg1) {
        this.arg1 = arg1;
    }

    public TestService(String arg1, String arg2, Integer arg3) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
    }

    public void testPrint(String ...msg){
        System.out.println(Arrays.toString(msg));
    }

    @Override
    public String toString() {
        return "TestService{" +
                "arg1='" + arg1 + '\'' +
                ", arg2='" + arg2 + '\'' +
                ", arg3=" + arg3 +
                '}';
    }
}
