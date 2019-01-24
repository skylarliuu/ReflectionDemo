package com.skylar.reflectiondemo;

import android.util.Log;

/**
 * Created by Skylar on 2019/1/23
 * Github : https://github.com/skylarliuu
 */
public class Base {
    public String des;
    private int type;

    public Base(){

    }

    public Base(String des,int type){
        this.des = des;
        this.type = type;
    }

    public void printDes(){
        Log.i(MainActivity.TAG,"print base des = "+des);
    }

    private void printType(){
        Log.i(MainActivity.TAG,"print base type = "+type);
    }
}
