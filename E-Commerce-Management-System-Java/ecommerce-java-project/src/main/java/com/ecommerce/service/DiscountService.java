package com.ecommerce.service;

public class DiscountService {
	public double calculate(double subtotal, double percent) {
		if (subtotal < 0 || percent < 0 || percent > 100)
			throw new IllegalArgumentException("Invalid discount or subtotal.");
		return subtotal * percent / 100.0;
	}
}
