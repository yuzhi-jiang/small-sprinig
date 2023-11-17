package org.springframework;

import org.junit.Test;
import org.springframework.bean.TestService;
import org.springframework.bean.UserDao;
import org.springframework.bean.UserService;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

public class BeanTest {


    @Test
    public void testBean(){
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();


        //注入userDao
        factory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId","2"));
        propertyValues.addPropertyValue(new PropertyValue("userDao",new BeanReference("userDao")));


        BeanDefinition beanDefinition = new BeanDefinition(UserService.class,propertyValues);
        factory.registerBeanDefinition("userService", beanDefinition);



        //todo  单例会存在map中，但是key为name，没有参数，所以哪个先实例化，后面的也是之前的那个
        UserService userService = (UserService) factory.getBean("userService");

        userService.queryUserInfo();


    }
}
