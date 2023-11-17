package org.springframework;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class ResourceLoadTest {

    private DefaultResourceLoader loader;
    private InputStream inputStream;

    @Before
    public void init() {
        loader = new DefaultResourceLoader();
    }

    @Test
    public void testLoadResourceWithClassPath() throws IOException {
        Resource resource = loader.getResource("classpath:test.properties");
        inputStream = resource.getInputStream();
    }


    @Test
    public void testLoadResourceWithXml() throws IOException, ClassNotFoundException {
        Resource resource = loader.getResource("classpath:spring.xml");
        inputStream = resource.getInputStream();
        Document doc = XmlUtil.readXML(inputStream);
        //获取root
        Element root = doc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        int length = childNodes.getLength();
        for (int i = 0; i < length; i++) {
            Node item = childNodes.item(i);
            if (!(item instanceof Element)) {
                continue;
            }
            if (!"bean".equals(item.getNodeName())) {
                continue;
            }
            //获取item中的元素
            Element bean = (Element) item;
            //获取元素的id属性
            String id = bean.getAttribute("id");
            //获取元素的name属性
            String name = bean.getAttribute("name");
            //获取元素的class属性
            String className = bean.getAttribute("class");

            Class<?> clazz = Class.forName(className);

            //优先使用id
            String beanName = StrUtil.isNotEmpty(id) ? id : name;

            //如果beanName为空，则将clazz的简单名称转换为小写并赋值给beanName
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            BeanDefinition beanDefinition = new BeanDefinition(clazz);

            //设置beanDefinition 属性
            NodeList childNodes1 = bean.getChildNodes();
            for (int j = 0; j < childNodes1.getLength(); j++) {
                Node property = childNodes1.item(j);
                if(!(property instanceof  Element))continue;
                if(!"property".equals(property.getNodeName())){
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


            System.out.println(Arrays.toString(beanDefinition.getPropertyValues().getPropertyValues()));
        }
    }
    @Test
    public void testLoadResourceWithFile() throws IOException {
        Resource resource = loader.getResource("src/main/resources/test.properties");
        inputStream = resource.getInputStream();
    }



    public void testLoadResourceWithUrl() throws IOException, ClassNotFoundException {
        Resource resource = loader.getResource("http://ip/");
        inputStream = resource.getInputStream();



    }



    public void printRes() {
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }
}
