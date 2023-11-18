package org.springframework.context.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    @Override
   public void refresh() throws BeansException {
        // 刷新BeanFactory
        refreshBeanFactory();

        // 获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 调用BeanFactory后处理器
        invokeBeanFactoryPostProcessors(beanFactory);

        // 注册Bean后处理器
        registerBeanPostProcessors(beanFactory);


        //todo 初始化bean
        // 初始化bean
        //initBeans(beanFactory);

        // 初始化singleton bean
        //initSingletonBeans(beanFactory);
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanFactoryPostProcessor> beanFactoryBeansOfType = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);

        for (BeanFactoryPostProcessor value : beanFactoryBeansOfType.values()) {
            value.postProcessBeanFactory(beanFactory);
        }

    }

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessor = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor value : beanPostProcessor.values()) {
            beanFactory.addBeanPostProcessor(value);
        }
    }


    //refresh beanFactory
    protected abstract void refreshBeanFactory();

    protected abstract ConfigurableListableBeanFactory  getBeanFactory();


    @Override
    public Object getBean(String name) {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name,args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name,requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }
}
