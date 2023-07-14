package com.rita.wxx.aop.springaop;

import org.springframework.aot.hint.annotation.Reflective;

import java.lang.annotation.*;

/**
 * @author lhbac
 * @create 2023/6/11 16:35
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WxxLog {
}
