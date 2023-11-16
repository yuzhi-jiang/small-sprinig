package org.springframework.beans.factory.support;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) {
        //从单例中尝试获取
        Object singleton = getSingleton(name);
        if(singleton!=null){
            return singleton;
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition);
        return bean;
    }

    protected abstract BeanDefinition getBeanDefinition(String name);
    protected abstract Object createBean(String name,BeanDefinition beanDefinition);
}
