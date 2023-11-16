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


//        TestService testService = (TestService) factory.getBean("testService");
//
//        testService.testPrint("sdf");
//        System.out.println( testService.toString());



        //todo  单例会存在map中，但是key为name，没有参数，所以哪个先实例化，后面的也是之前的那个
        TestService testService1 = (TestService) factory.getBean("testService","123","234",3);
        testService1.testPrint("hello","anything");
        System.out.println( testService1.toString());

    }
}
