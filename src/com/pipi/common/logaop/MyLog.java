
package com.pipi.common.logaop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自定义MyLog注解
 * Created by yahto on 07/05/2017.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyLog {

	/** 要执行的操作类型比如：add操作 **/
	public String operationType() default "";
	
	/** 要执行的具体操作比如：【添加仓库】 **/
	public String operationName() default "";
	
}
