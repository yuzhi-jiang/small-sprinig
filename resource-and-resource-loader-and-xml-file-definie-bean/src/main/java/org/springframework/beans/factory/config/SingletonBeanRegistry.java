package org.springframework.beans.factory.config;

public interface SingletonBeanRegistry {

    /**
     * 获取单例
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);
}
