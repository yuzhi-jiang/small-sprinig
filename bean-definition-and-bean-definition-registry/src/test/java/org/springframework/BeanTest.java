package org.springframework;

import org.junit.Test;
import org.springframework.bean.TestService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

public class BeanTest {


    @Test
    public void testBean(){
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        BeanDefinition beanDefinition = new BeanDefinition(TestService.class);
        factory.registerBeanDefinition("testService", beanDefinition);


        TestService testService = (TestService) factory.getBean("testService");

        testService.testPrint("sdf");

        TestService testService1 = (TestService) factory.getBean("testService");
        testService1.testPrint("hello","anything");


    }
}
