package org.springframework;

import org.junit.Test;
import org.springframework.bean.TestService;
import org.springframework.bean.UserService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanTest {

    @Test
    public void testBean(){


        ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:springPostProcessor.xml");

//        TestService testService = (TestService) factory.getBean("testService");
//
//        testService.testPrint("sdf");
//        System.out.println( testService.toString());



        //todo  单例会存在map中，但是key为name，没有参数，所以哪个先实例化，后面的也是之前的那个
        UserService testService1 = (UserService) applicationContext.getBean("userService","123","234",3);
        testService1.queryUserInfo();
        System.out.println( testService1.getuId());
        System.out.println( testService1.getUserDao().queryUserName("2"));
        System.out.println( testService1.toString());

    }
}
