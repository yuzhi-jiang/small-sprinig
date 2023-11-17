package org.springframework.core.io;

import cn.hutool.core.lang.Assert;

import java.net.MalformedURLException;
import java.net.URL;

public class DefaultResourceLoader implements ResourceLoader{
    @Override
    public Resource getResource(String location) {
        //location must not be null
        Assert.notNull(location, "Location must not be null");
        if(location.startsWith(CLASSPATH_URL_PREFIX)){
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        }
        else{
            //try load url
            try {
                return new UrlResource(new URL(location));
            } catch (MalformedURLException e) {
                //TODO add log
                return new FileSystemResource(location);
            }
        }
    }
}
