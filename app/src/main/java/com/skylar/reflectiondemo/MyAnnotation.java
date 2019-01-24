package com.skylar.reflectiondemo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Skylar on 2019/1/24
 * Github : https://github.com/skylarliuu
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String value();
}
