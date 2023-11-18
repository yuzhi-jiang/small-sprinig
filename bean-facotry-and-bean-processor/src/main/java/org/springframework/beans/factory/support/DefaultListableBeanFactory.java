package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {


    private Map<String,BeanDefinition> beanDefinitionMap=new HashMap<>();

    @Override
    protected BeanDefinition getBeanDefinition(String name) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (beanDefinition==null){
            throw new BeansException("No bean named: '"+name+"'");
        }
        return beanDefinition;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName,beanDefinition);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
   //获取指定类型的Bean
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        //创建一个Map，用来存放结果
        Map<String,T> res=new HashMap<>();
        //遍历beanDefinitionMap，取出每一个beanName和beanDefinition
        beanDefinitionMap.forEach((beanName,beanDefinition)->{
            //判断beanDefinition的beanClass是否与type相同
            if (type.isAssignableFrom(beanDefinition.getBeanClass())){
                //如果相同，将beanName和getBean(beanName)的结果放入res中
                res.put(beanName,(T)getBean(beanName));
        }});

        //返回结果
        return res;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }
}
