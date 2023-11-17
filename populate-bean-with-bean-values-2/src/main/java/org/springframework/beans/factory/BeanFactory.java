package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

public interface BeanFactory {
    Object getBean(String name);
    Object getBean(String name, Object... args) throws BeansException;
}
