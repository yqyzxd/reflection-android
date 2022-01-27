package com.github.yqyzxd.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class Meta {

    public static Method getDeclaredMethod(Class<?> cls, String name, Class<?>... parameterTypes) {
        try {
            Method dMethod = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);

            return (Method) dMethod.invoke(cls, name, parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method getMethod(Class<?> cls, String name, Class<?>... parameterTypes) {
        try {
            Method dMethod = Class.class.getDeclaredMethod("getMethod", String.class, Class[].class);

            return (Method) dMethod.invoke(cls, name, parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Field getDeclaredField(Class<?> cls, String name) {
        try {
            Method dMethod = Class.class.getDeclaredMethod("getDeclaredField", String.class);

            return (Field) dMethod.invoke(cls, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Field getField(Class<?> cls, String name) {
        try {
            Method dMethod = Class.class.getDeclaredMethod("getField", String.class);

            return (Field) dMethod.invoke(cls, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
