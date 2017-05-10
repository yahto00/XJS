package com.pipi.common.exception;

/**
 * @author liuyang
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -8332364897978441217L;
	public BusinessException(String message){
		super(message);
	}
	public BusinessException() {
	}
}
