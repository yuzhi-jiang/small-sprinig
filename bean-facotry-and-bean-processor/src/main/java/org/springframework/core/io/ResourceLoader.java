package org.springframework.core.io;

public interface ResourceLoader {

   // 用于从类路径加载的伪 url 前缀
    String CLASSPATH_URL_PREFIX = "classpath:";

    Resource getResource(String location);

}