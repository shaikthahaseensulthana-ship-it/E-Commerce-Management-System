package com.ecommerce.payment;

public class CashPayment implements Payment {
	public boolean pay(double amount) {
		System.out.printf("Cash on Delivery selected. ₹%.2f is payable on delivery.%n", amount);
		return true;
	}

	public String getMethodName() {
		return "Cash on Delivery";
	}
}
