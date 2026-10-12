package com.ecommerce.model;

public class Clothing extends Product {
	public Clothing(int id, String n, double p, int s) {
		super(id, n, p, s);
	}

	@Override
	public String getCategory() {
		return "Clothing";
	}
}
