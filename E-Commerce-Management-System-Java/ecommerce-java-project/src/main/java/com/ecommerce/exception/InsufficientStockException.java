package com.ecommerce.exception;
@SuppressWarnings("serial")
public class InsufficientStockException extends RuntimeException { 
	public InsufficientStockException(String message){
		super(message);
		} 
	}
