package com.ecommerce.model;

public class Electronics extends Product {
	public Electronics(int id, String n, double p, int s) {
		super(id, n, p, s);
	}

	@Override
	public String getCategory() {
		return "Electronics";
	}
}
