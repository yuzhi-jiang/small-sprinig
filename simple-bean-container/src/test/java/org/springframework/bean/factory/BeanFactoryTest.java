package org.springframework.bean.factory;

import org.junit.Test;
import org.springframework.bean.BeanDefinition;


public class BeanFactoryTest {



    @Test
    public void getBean() {
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.registerBeanDefinition("testBean",new BeanDefinition(new TestBean()) );
        TestBean testBean = (TestBean) beanFactory.getBean("testBean");
        testBean.test();
    }

}


class TestBean {
    public void test(){
        System.out.println("test");
    }
}