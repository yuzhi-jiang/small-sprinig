package org.springframework.bean.factory;

import org.springframework.bean.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {
    private Map<String, BeanDefinition> beanDefinitionMap=new ConcurrentHashMap<>();


    //get Bean
    public Object getBean(String beanName){
        return beanDefinitionMap.get(beanName).getBean();
    }

    //register bean
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition){
        beanDefinitionMap.put(beanName,beanDefinition);
    }
}
