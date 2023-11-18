package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        //从单例中尝试获取

        return doGetBean(beanName, args);
    }

    protected <T> T doGetBean(final String beanName, final Object[] args) {
        Object bean = getSingleton(beanName);
        if (bean != null) {
            return (T) bean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return (T) createBean(beanName, beanDefinition, args);
    }


    // 创建一个ArrayList类型的beanPostProcessorList变量，用于存放BeanPostProcessor类型的对象
    private final List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessorList;
    }

    //添加BeanPostProcessor
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        //移除已有的BeanPostProcessor
        this.beanPostProcessorList.remove(beanPostProcessor);
        //添加新的BeanPostProcessor
        this.beanPostProcessorList.add(beanPostProcessor);
    }


    @Override
    public Object getBean(String beanName) {
        return doGetBean(beanName, null);
    }


    //todo 待做
    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return doGetBean(name,null);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName);

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object... args);
}
