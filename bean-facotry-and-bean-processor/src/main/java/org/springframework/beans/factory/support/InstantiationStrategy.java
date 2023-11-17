package org.springframework.beans.factory.support;

import org.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

//实例化 策略
public interface InstantiationStrategy {


    Object instantiate(String name, BeanDefinition bd, Constructor constructor, Object ... args);

}
