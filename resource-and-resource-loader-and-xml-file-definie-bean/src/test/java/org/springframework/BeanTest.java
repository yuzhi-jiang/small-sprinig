package org.springframework;

import org.junit.Test;
import org.springframework.bean.UserDao;
import org.springframework.bean.UserService;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.support.XmlBeanDefinitionReader;

public class BeanTest {


    @Test
    public void testBean(){
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();



        // 2. 读取配置文件&注册Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions("classpath:spring.xml");




        //todo  单例会存在map中，但是key为name，没有参数，所以哪个先实例化，后面的也是之前的那个
        UserService userService = (UserService) factory.getBean("userService");

        userService.queryUserInfo();


    }


}
