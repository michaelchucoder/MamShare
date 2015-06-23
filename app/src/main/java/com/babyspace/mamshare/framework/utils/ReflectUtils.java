package com.babyspace.mamshare.framework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectUtils {
    /**
     * 反射泛型
     *
     * @param obj
     * @param index
     * @return
     */
    public static Class getGenericType(Object obj, int index) {
        Type genType = obj.getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            throw new RuntimeException("Index outof bounds");
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    public static Object getFieldValue(Object aObject, String aFieldName) {
        Field field = getClassField(aObject.getClass(), aFieldName);// get the
        // field in
        // this
        // object
        if (field != null) {
            field.setAccessible(true);
            try {
                return field.get(aObject);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 这个方法，是最重要的，关键的实现在这里面
     *
     * @param aClazz
     * @param aFieldName
     * @return
     */
    private static Field getClassField(Class aClazz, String aFieldName) {
        Field[] fields = aClazz.getDeclaredFields();
        for (Field field : fields) {
            // 注意：这里判断的方式，是用字符串的比较。很傻瓜，但能跑。要直接返回Field。我试验中，尝试返回Class，然后用getDeclaredField(String
            // fieldName)，但是，失败了
            if (field.getName().equals(aFieldName)) {
                return field;// define in this class
            }
        }

        Class superclass = aClazz.getSuperclass();
        if (superclass != null) {// 简单的递归一下
            return getClassField(superclass, aFieldName);
        }
        return null;
    }
}
