package com.skylar.reflectiondemo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by Skylar on 2019/1/23
 * Github : https://github.com/skylarliuu
 */
@SuppressWarnings(value = "test_annotation")//source
@TargetApi(value = Build.VERSION_CODES.LOLLIPOP)//class
@MyAnnotation("hello_annotation")//runtime
public class Test extends Base {

    public static int static_int = 1;


    public Test(String des){
        this.des = des;
    }

}
