package org.springframework.support;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileInputStream;
import java.io.InputStream;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader{

    private static final String PROPERTY="property";
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }


    @Override
    public void loadBeanDefinitions(Resource resource) {
        try (InputStream inputStream = resource.getInputStream()) {
            doLoadBeanDefinitions(inputStream);
        }catch (Exception e){
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }




    @Override
    public void loadBeanDefinitions(String location) {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }
    protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException {
        //1.读取xml
        Document doc = XmlUtil.readXML(inputStream);
        //获取root
        Element root = doc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        int length = childNodes.getLength();
        for (int i = 0; i < length; i++) {
            Node item = childNodes.item(i);
            if(!(item instanceof Element )){
                continue;
            }
            if(!"bean".equals(item.getNodeName())){
                continue;
            }
           //获取item中的元素
            Element bean = (Element) item;
            //获取元素的id属性
            String id=bean.getAttribute("id");
            //获取元素的name属性
            String name=bean.getAttribute("name");
            //获取元素的class属性
            String className=bean.getAttribute("class");

            Class<?> clazz = Class.forName(className);

            //优先使用id
            String beanName= StrUtil.isNotEmpty(id)?id:name;

            //如果beanName为空，则将clazz的简单名称转换为小写并赋值给beanName
            if(StrUtil.isEmpty(beanName)){
                beanName= StrUtil.lowerFirst(clazz.getSimpleName());
            }

            BeanDefinition beanDefinition=new BeanDefinition(clazz);

            //设置beanDefinition 属性
            NodeList childNodes1 = bean.getChildNodes();

            for (int j = 0; j < childNodes1.getLength(); j++) {
                Node property = childNodes1.item(j);
                if(!(property instanceof  Element))continue;
                if(!PROPERTY.equals(property.getNodeName())){
                    continue;
                }
                String attrName=((Element) property).getAttribute("name");
                String attrValue=((Element) property).getAttribute("value");
                String attrRef=((Element) property).getAttribute("ref");


                //如果属性引用不为空，则将属性引用转换为BeanReference对象，否则将属性值赋值给value
                Object value=StrUtil.isNotEmpty(attrRef)?new BeanReference(attrRef):attrValue;
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            // 检查beanName是否已经存在
            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }



            getRegistry().registerBeanDefinition(beanName,beanDefinition);
        }


    }

}
