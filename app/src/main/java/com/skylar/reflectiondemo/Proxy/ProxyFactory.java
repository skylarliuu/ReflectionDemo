package com.skylar.reflectiondemo.Proxy;

import java.lang.reflect.Proxy;

/**
 * Created by Skylar on 2019/1/24
 * Github : https://github.com/skylarliuu
 */
public class ProxyFactory {

    public static Animal getProxy(Animal target){
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler();
        myInvocationHandler.setTarget(target);
        return (Animal) Proxy.newProxyInstance(target.getClass().getClassLoader(),new Class[]{Animal.class},myInvocationHandler);
    }
}
