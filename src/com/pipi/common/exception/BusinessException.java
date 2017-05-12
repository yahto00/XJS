package com.pipi.common.exception;


/**
 * 自定义异常
 * Created by yahto on 07/05/2017.
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -8332364897978441217L;
	public BusinessException(String message){
		super(message);
	}
	public BusinessException() {
	}
}
