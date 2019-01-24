package com.skylar.reflectiondemo.Proxy;

import android.util.Log;

import com.skylar.reflectiondemo.MainActivity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Skylar on 2019/1/24
 * Github : https://github.com/skylarliuu
 */
public class MyInvocationHandler implements InvocationHandler {

    private Animal mTarget;

    public void setTarget(Animal target){
        mTarget = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.i(MainActivity.TAG,"MyInvocationHandler add one");

        method.invoke(mTarget);

        Log.i(MainActivity.TAG,"MyInvocationHandler add two");
        return null;
    }
}
