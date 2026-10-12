package com.ecommerce.model;

public class Grocery extends Product {
	public Grocery(int id, String n, double p, int s) {
		super(id, n, p, s);
	}

	@Override
	public String getCategory() {
		return "Grocery";
	}
}
