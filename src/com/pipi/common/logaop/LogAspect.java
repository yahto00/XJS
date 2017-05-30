package com.pipi.common.logaop;

import com.pipi.common.constant.SystemConstant;
import com.pipi.entity.Order;
import com.pipi.entity.admin.User;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
//import com.pipi.entity.Order;
import com.pipi.controller.BaseController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;


/**
 * 系统日志AOP
 * Created by yahto on 07/05/2017.
 */
@Aspect
@Component
public class LogAspect extends BaseController {
    /**
     * log4j 日志处理对象
     */
    private static final Logger log = Logger.getLogger(LogAspect.class);

    @AfterReturning(value = "execution(public * com.pipi.service..*.*(..))")
    public void doSystemLog(JoinPoint point) throws Throwable {
        /** 从web.xml中取得request对象 */
        ServletRequestAttributes sa = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = null;
        if (sa != null) {
            request = sa.getRequest();
        }
        if (request == null)
            return;
        /** 取得全局变量中保存的当前登录用户，如果没有，返回为null */
        Object userO = request.getSession().getAttribute(
                SystemConstant.CURRENT_USER);
        /** 只有已登录的顾客或员工执行非查询操作时才记录日志 */
        if (userO == null || !(userO instanceof User))
            return;

        /** 取得切面方法上的参数对象、方法所在类和方法名 */
        Object[] param = point.getArgs();
        Class<?> targetClass = point.getTarget().getClass();
        String methodName = point.getSignature().getName();

        if (!methodName.equals("queryUserForLogin") && (methodName.startsWith("query")
                || methodName.startsWith("find") || methodName.startsWith("list")
                || methodName.startsWith("load") || methodName.startsWith("get")
                || methodName.startsWith("count") || methodName.startsWith("validate")))
            return;

        /** 将当前访问的类和方法名打印到后台 */
        //System.out.println(targetClass.getName() + ":" + methodName);
        log.info(targetClass.getName() + ":" + methodName);

        /** 得到切面拦截的方法对象 */
        Method method = null;
        for (Method m : targetClass.getMethods()) {
            if (m.getName().equals(methodName)) {
                method = m;
                break;
            }
        }

        if (method != null) {
            /** 如果存在日志annotation则取得方法上的日志annotation */
            boolean hasAnnotation = method.isAnnotationPresent(MyLog.class);
            if (hasAnnotation) {
                MyLog annotation = method.getAnnotation(MyLog.class);
                String methodDescp = annotation.operationType()
                        + annotation.operationName();
                if (log.isDebugEnabled()) {
                    log.debug("Action method:" + method.getName()
                            + " Description:" + methodDescp);
                }
                try {
                    /** 得到当前登录的员工或用户 */
                    User appUser = (User) userO;

					/*if(appUser == null && methodName.equals("queryUserForLogin")){
                        appUser = new User();
						appUser.setLoginName(param[0].toString());
					}*/

                    String content = "员工" + annotation.operationType() + "操作,"
                            + appUser.getLoginName() + "执行【"
                            + annotation.operationName()
                            + "】操作,影响数据的ID集合为[" + getID(param[0]) + "]";
                    this.addLog(content, request);
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }
            }
        }
    }

    /**
     * 通过java反射来从传入的参数object里取出我们需要记录的id,name等属性， 此处我取出的是id
     *
     * @param obj
     * @return String
     */
    public String getID(Object obj) {
        String result = "";
        if (obj instanceof String || obj instanceof Integer) {
            result = obj.toString();
        } else if (obj instanceof String[]) {
            String[] arr = (String[]) obj;
            for (String value : arr)
                result += value + ",";
            if (result.length() > 1)
                result = result.substring(0, result.length() - 1);
        } else if (obj instanceof Integer[]) {
            Integer[] arr = (Integer[]) obj;
            for (Integer value : arr)
                result += value + ",";
            if (result.length() > 1)
                result = result.substring(0, result.length() - 1);
        } else if (obj instanceof Order[]) {
            Order[] arr = (Order[]) obj;
            PropertyDescriptor pd = null;
            Method method = null;
            try {
                pd = new PropertyDescriptor("id", Order.class);
                method = pd.getReadMethod();
                for (Order o : arr) {
                    result += String.valueOf(method.invoke(o)) + ",";
                }
                if (result.length() > 1)
                    result = result.substring(0, result.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (obj instanceof List) {
            PropertyDescriptor pd = null;
            Method method = null;
            List<?> list = (List<?>) obj;
            for (int i = 0; i < list.size(); i++) {
                Object obj1 = list.get(i);
                try {
                    pd = new PropertyDescriptor("id", obj1.getClass());
                    method = pd.getReadMethod();
                    result += String.valueOf(method.invoke(obj1)) +",";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            PropertyDescriptor pd = null;
            Method method = null;
            try {
                pd = new PropertyDescriptor("id", obj.getClass());
                method = pd.getReadMethod();
                result = String.valueOf(method.invoke(obj));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
