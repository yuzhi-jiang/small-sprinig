package org.springframework.support;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * 定义BeanDefinitionReader接口，用于读取bean的定义
 */
public interface BeanDefinitionReader {
    BeanDefinitionRegistry getRegistry();
    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(String location);
    void loadBeanDefinitions(String... location);


    void loadBeanDefinitions(Resource... resources);
    void loadBeanDefinitions(Resource resource);

}