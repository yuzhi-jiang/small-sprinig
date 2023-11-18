package org.springframework.context.support;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.support.XmlBeanDefinitionReader;

public abstract class AbstractXmlApplicationContext  extends AbstractRefreshableApplicationContext{
    @Override
   protected void loadBeanDefinition(DefaultListableBeanFactory beanFactory) {
        //创建XmlBeanDefinitionReader对象，用于读取XML文件中的Bean定义
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        //获取配置文件的位置
        String[] configLocations=getConfigLocations();
        //如果配置文件的位置不为空，则读取配置文件
        if(null!=configLocations){
            xmlBeanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();
}
