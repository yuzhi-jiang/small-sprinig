package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 简单的实例化策略
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy{
    @Override
    public Object instantiate(String name, BeanDefinition bd,Constructor ctor, Object... args) {
        Class clazz = bd.getBeanClass();
        try {
            if(ctor!=null){
                 return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            }
            else {
                return clazz.newInstance();
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new BeansException("Failed to instantiate [" + clazz.getName() + "]", e);
        }
    }
}
