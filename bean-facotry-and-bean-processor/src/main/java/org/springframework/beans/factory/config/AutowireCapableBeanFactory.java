package org.springframework.beans.factory.config;

import org.springframework.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {


    /**
     * 在bean初始化之前应用后处理器
     * @param existingBean 已经存在的bean
     * @param beanName bean的名称
     * @return 返回处理后的bean
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName);


    /**
     * 在bean初始化之后应用后处理器
     * @param existingBean 已经存在的bean
     * @param beanName bean的名称
     * @return 返回处理后的bean
     */
    Object applyBeanPostProcessorsAfterInitialization(Object  existingBean, String beanName);
}
