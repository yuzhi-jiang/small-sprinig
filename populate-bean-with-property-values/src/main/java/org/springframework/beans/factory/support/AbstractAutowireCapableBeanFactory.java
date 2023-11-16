package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{


   //创建一个CglibSubclassingInstantiationStrategy实例
    InstantiationStrategy instantiationStrategy=new CglibSubclassingInstantiationStrategy();
    @Override
    protected Object createBean(String name, BeanDefinition beanDefinition,Object ...args) {
        Object bean;
        try {
            bean = createBeanInstance(beanDefinition, name, args);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        addSingleton(name,bean);
        return bean;
    }

  private Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Class<?> beanClass = beanDefinition.getBeanClass();

        Constructor<?> resConstructor=null;

        //获取beanClass中的构造函数
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            //如果参数个数和参数类型一致，则赋值给resConstructor
            if(args!=null&&declaredConstructor.getParameterTypes().length==args.length){
                resConstructor=declaredConstructor;
                break;
            }
        }
        //调用getInstantiationStrategy()方法，传入beanName，beanDefinition，resConstructor，args参数，返回实例化结果
        return getInstantiationStrategy().instantiate(beanName, beanDefinition, resConstructor, args);
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
