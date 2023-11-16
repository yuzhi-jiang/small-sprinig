package org.springframework.beans.factory.support;

import org.springframework.beans.factory.config.BeanDefinition;

public interface BeanBeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
