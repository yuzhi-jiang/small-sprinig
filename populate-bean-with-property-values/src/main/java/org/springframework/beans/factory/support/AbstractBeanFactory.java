package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        //从单例中尝试获取

        return doGetBean(name, args);
    }

    protected <T> T doGetBean(final String name, final Object[] args) {
        Object bean = getSingleton(name);
        if (bean != null) {
            return (T) bean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return (T) createBean(name, beanDefinition, args);
    }

    @Override
    public Object getBean(String name) {
        return doGetBean(name, null);
    }


    protected abstract BeanDefinition getBeanDefinition(String name);
    protected abstract Object createBean(String name,BeanDefinition beanDefinition,Object ...args);
}
