package org.springframework.beans;

public class BeansException extends RuntimeException {
    public BeansException(String errMsg) {
        super(errMsg);
    }

    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }
}
