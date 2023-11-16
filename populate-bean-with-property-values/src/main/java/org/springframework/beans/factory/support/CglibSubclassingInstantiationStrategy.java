package org.springframework.beans.factory.support;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy{

    @Override
   // 创建一个Enhancer对象
    public Object instantiate(String name, BeanDefinition beanDefinition, Constructor ctor, Object... args) {
        Enhancer enhancer = new Enhancer();
        // 设置父类
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        // 设置回调函数
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        // 如果ctor为空，则创建一个对象
        if (null == ctor) return enhancer.create();
        // 否则，根据ctor的参数类型，创建一个对象
        return enhancer.create(ctor.getParameterTypes(), args);
    }
}
