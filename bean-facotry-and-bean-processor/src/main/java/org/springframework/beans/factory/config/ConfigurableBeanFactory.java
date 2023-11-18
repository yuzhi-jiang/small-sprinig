package org.springframework.beans.factory.config;

public interface ConfigurableBeanFactory extends SingletonBeanRegistry{
   // 单例模式
    String SCOPE_SINGLETON = "singleton";
    // 原型模式
    String SCOPE_PROTOTYPE = "prototype";

    // 添加Bean后处理器
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);


}
