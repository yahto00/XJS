package com.pipi.util;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yahto on 21/05/2017.
 */
public class ObjectUtil {
    public static boolean objectIsEmpty(Object object){
        Method[] methods = object.getClass().getDeclaredMethods();
        List<Method> getMethodList = new ArrayList<Method>();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().contains("get")){
                getMethodList.add(methods[i]);
            }
        }
        for (Method getMethod :
                getMethodList) {
            try {
                Object vo = getMethod.invoke(object);
                if (!StringUtils.isBlank(vo.toString())){
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
