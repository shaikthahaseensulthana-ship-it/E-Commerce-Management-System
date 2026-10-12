package com.ecommerce.payment;

public interface Payment {
	boolean pay(double amount);

	String getMethodName();
}
