package com.skylar.reflectiondemo.Proxy;

import android.util.Log;

import com.skylar.reflectiondemo.MainActivity;

/**
 * Created by Skylar on 2019/1/24
 * Github : https://github.com/skylarliuu
 */
public class Cat implements Animal {
    @Override
    public void sleep() {
        Log.i(MainActivity.TAG,"cat sleep");
    }

    @Override
    public void run() {
        Log.i(MainActivity.TAG,"cat run");

    }
}
