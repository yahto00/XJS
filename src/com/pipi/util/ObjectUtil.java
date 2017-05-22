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
    public static boolean objectIsEmpty(Object object,String[] params){
        Method[] methods = object.getClass().getDeclaredMethods();
        List<Method> getMethodList = new ArrayList<Method>();
        for (int i = 0; i < methods.length; i++) {
            for (int j = 0; j < params.length; j++) {
                if (methods[i].getName().toUpperCase().contains("GET"+params[j].toUpperCase()) && methods[i].getReturnType().toString().equals("class java.lang.String")){
                    getMethodList.add(methods[i]);
                }
            }
        }
        for (Method getMethod : getMethodList) {
            try {
                Object vo = getMethod.invoke(object);
                if (vo == null || StringUtils.isBlank(vo.toString())){
                    return true;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
