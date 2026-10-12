package com.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
	private String address;
	private final List<Order> orders = new ArrayList<>();

	public Customer(int id, String name, String email, String password, String address) {
		super(id, name, email, password, "CUSTOMER");
		this.address = address == null ? "Not provided" : address.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Order> getOrders() {
		return orders;
	}
}
