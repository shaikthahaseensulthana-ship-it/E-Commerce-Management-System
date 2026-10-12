package com.ecommerce.payment;

public class UpiPayment implements Payment {
	private final String upiId;

	public UpiPayment(String id) {
		if (id == null || !id.matches("[A-Za-z0-9._-]+@[A-Za-z0-9.-]+"))
			throw new IllegalArgumentException("Enter a valid UPI ID, e.g. name@upi.");
		upiId = id;
	}

	public boolean pay(double amount) {
		System.out.printf("UPI payment of ₹%.2f simulated successfully for %s.%n", amount, upiId);
		return true;
	}

	public String getMethodName() {
		return "UPI";
	}
}
