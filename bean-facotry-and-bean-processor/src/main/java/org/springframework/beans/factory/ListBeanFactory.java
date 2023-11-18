package org.springframework.beans.factory;

import java.util.Map;

public interface ListBeanFactory extends BeanFactory
{
    // 获取指定类型的Bean
    <T> Map<String,T> getBeansOfType(Class<T> type);

    // 获取所有Bean的定义名称
    String[] getBeanDefinitionNames();
}