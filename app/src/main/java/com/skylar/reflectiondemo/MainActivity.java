package com.skylar.reflectiondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.skylar.reflectiondemo.Proxy.Animal;
import com.skylar.reflectiondemo.Proxy.Cat;
import com.skylar.reflectiondemo.Proxy.ProxyFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "reflection_log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getClassByReflection();
        getClassInfomationByReflection();
        getClassMemberByReflection();
        getArrayByReflection();
        getProxyByReflection();
    }


    private void getClassByReflection() {

        //1. 获取Class对象
        Log.i(TAG,"=============获取Class对象=============");
        Class clazz = Test.class;
        Log.i(TAG,"clazz = "+clazz);

        Test test = new Test("");
        Class clazz1 = test.getClass();//这种方法不适用基本类型
        Log.i(TAG,"clazz1 = "+clazz1);

        try {
            Class clazz2 = Class.forName("com.skylar.reflectiondemo.Test");//这种方法不适用基本类型
            Log.i(TAG,"clazz2 = "+clazz2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Class clazz3 = clazz1.getSuperclass();//获取父类
        Log.i(TAG,"clazz3 = "+clazz3);

        try {
            Class clazz4 = Class.forName("[[I");//获取二维数组类，跟JNI中申明一样
            Log.i(TAG,"clazz4 = "+clazz4);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //基本类型和 void 类型的包装类可以使用 TYPE 字段获取
        Class clazz5 = Double.TYPE;//等价于 double.class.
        Class clazz6 = Void.TYPE;//等价于 void.class.
        Log.i(TAG,"clazz5 = "+clazz5);
        Log.i(TAG,"clazz6 = "+clazz6);

    }

    private void getClassInfomationByReflection() {

        //2.获取类的修饰符和类型
        Log.i(TAG,"=============获取HashMap类的修饰符和类型=============");
        Class clazz = HashMap.class;
        Log.i(TAG,"限定符："+Modifier.toString(clazz.getModifiers()));
        Log.i(TAG,"类名："+clazz.getCanonicalName());
        TypeVariable[] tv = clazz.getTypeParameters();
        for(TypeVariable typeVariable:tv){
            Log.i(TAG,"泛型参数："+typeVariable.getName());
        }

        Log.i(TAG,"直接父类："+clazz.getSuperclass());//直接父类
        printAllSuperClass(clazz);//所有父类

//        Class[] interfaces = clazz.getInterfaces();//不带泛型
//        for(Class c:interfaces){
//            Log.i(TAG,"接口："+c);
//        }

        Type[] types = clazz.getGenericInterfaces();//带泛型参数
        for(Type t:types){
            Log.i(TAG,"接口："+t);
        }

        //获取类的注解(只能获取到 RUNTIME 类型的注解)
        Class testClazz = Test.class;
        Annotation[] annotations = testClazz.getAnnotations();
        for(Annotation annotation :annotations){
            Log.i(TAG,"注解："+annotation);
        }
    }

    private void printAllSuperClass(Class clazz) {
        Class superClass = clazz.getSuperclass();
        if(superClass!=null){
            Log.i(TAG,"所有父类："+superClass);
            printAllSuperClass(superClass);
        }
    }

    private void getClassMemberByReflection(){
        //3.获取类的成员 Constructor Method Field
        Log.i(TAG,"=============获取类的成员=============");
        Class clazz = Base.class;
        try {
            Constructor constructor = clazz.getConstructor(String.class,int.class);
            Object object = constructor.newInstance("this is a test",33);//生成对象
            Method method = clazz.getMethod("printDes");
            method.invoke(object);

//            Field field = clazz.getField("type");//只能获得 public 的
            Field field = clazz.getDeclaredField("type");//只要是声明的变量都能获得，包括 private
            field.setAccessible(true);
            field.set(object,55);
            Method method1 = clazz.getDeclaredMethod("printType");
            method1.setAccessible(true);
            method1.invoke(object);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    private void getArrayByReflection() {
        //3.操作数组
        Log.i(TAG,"=============操作数组=============");
        //新建一个二维数组
        Object object = Array.newInstance(int.class,2,3);

        //第一种方式
        Object object0 = Array.get(object,0);
        Array.set(object0,0,1);
        Array.set(object0,1,2);
        Array.set(object0,2,3);

        //第二种方式
        Object object1 = Array.newInstance(int.class,3);
        Array.set(object1,0,4);
        Array.set(object1,1,5);
        Array.set(object1,2,6);
        Array.set(object,1,object1);

        int[][] arr = (int[][])object;

        Log.i(TAG,Arrays.toString(arr[0]));
        Log.i(TAG,Arrays.toString(arr[1]));

    }


    private void getProxyByReflection() {
        //4.动态代理
        Log.i(TAG,"=============动态代理=============");
        //目的：通过代理对象去调用实际对象的方法（隐藏真实对象），能够方便地在执行方法前后插入一些处理（充分解耦）
        //主要用到的类：Proxy,InvocationHandler接口
        //Proxy:通过newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)生成代理类，该类实现类interfaces指定的多个接口
        //InvocationHandler接口：执行动态代理对象的任意方法都调用到InvocationHandler的invoke（）方法，可以在invoke方法插入一些处理

        Cat cat = new Cat();
        Animal animal = ProxyFactory.getProxy(cat);
        animal.sleep();
        animal.run();
    }

}
