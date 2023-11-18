package org.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {


   //创建一个CglibSubclassingInstantiationStrategy实例
    InstantiationStrategy instantiationStrategy=new SimpleInstantiationStrategy();
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition,Object ...args) {
        Object bean;
        try {
            bean = createBeanInstance(beanDefinition, beanName, args);
            //todo addpropertyValues
            applyPropertyValues(beanName,bean,beanDefinition);
            bean= initializeBean(beanName,bean,beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        addSingleton(beanName,bean);
        return bean;
    }

   private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
       // 调用bean后处理器，在初始化之前应用bean
       Object wrappedBean= applyBeanPostProcessorsBeforeInitialization(bean,beanName);

        // 调用bean的初始化方法
        invokeInitMethods(beanName, wrappedBean, beanDefinition);

       // 调用bean后处理器，在初始化之后应用bean
       wrappedBean=applyBeanPostProcessorsAfterInitialization(bean,beanName);
       return wrappedBean;
    }

    private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) {
        //todo invokeInitMethods
    }


    @Override
   public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) {
        Object res=existingBean;

        // 遍历所有的BeanPostProcessor，对res进行处理
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
           //调用postProcessBeforeInitialization方法，处理res对象
            Object current = processor.postProcessBeforeInitialization(res, beanName);
            //如果返回null，则返回res
            if(current==null){
                return res;
            }
            res=current;
        }
        return res;
    }


    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) {
        Object res=existingBean;

        // 遍历所有的BeanPostProcessor，对res进行处理
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            //调用postProcessBeforeInitialization方法，处理res对象
            Object current = processor.postProcessAfterInitialization(res, beanName);
            //如果返回null，则返回res
            if(current==null){
                return res;
            }
            res=current;
        }
        return res;
    }



    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                //如果属性值是一个Bean引用，则获取该Bean的实例
                if(value instanceof BeanReference){
                    BeanReference beanReference=(BeanReference)value;

                    value = getBean(beanReference.getBeanName());
                }
                //将属性值设置到bean中
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values：" + beanName,e);
        }
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
