package org.springframework.core.io;

import cn.hutool.core.util.ClassUtil;
import org.springframework.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassPathResource implements Resource{

    private final String path;
    private ClassLoader classLoader;

    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = classLoader!=null?classLoader: ClassUtils.getDefaultClassLoader();
    }

    public ClassPathResource(String path) {
        this(path,null);
    }

   @Override
    public InputStream getInputStream() throws IOException {
        //从类加载器中获取指定路径的输入流
        InputStream resourceAsStream = classLoader.getResourceAsStream(path);
        //如果输入流为空，抛出文件未找到异常
        if(resourceAsStream==null){
            throw new FileNotFoundException(this.path+" cannot be open because it does not exist");
        }
        //返回输入流
        return resourceAsStream;
    }
}
