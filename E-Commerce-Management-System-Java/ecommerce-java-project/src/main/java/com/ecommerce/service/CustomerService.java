package com.ecommerce.service;

import com.ecommerce.model.*;
import java.util.*;

public class CustomerService {
	private final Map<String, Customer> customers = new LinkedHashMap<>();
	private int nextId = 1;

	public CustomerService() {
		customers.put("admin@shop.com", null);
	}

	public Customer register(String name, String email, String password, String address) {
		String key = email.trim().toLowerCase(Locale.ROOT);
		if (customers.containsKey(key))
			throw new IllegalArgumentException("Email is already registered.");
		Customer c = new Customer(nextId++, name, key, password, address);
		customers.put(key, c);
		return c;
	}

	public Customer login(String email, String password) {
		Customer c = customers.get(email.trim().toLowerCase(Locale.ROOT));
		if (c == null || !c.getPassword().equals(password))
			throw new IllegalArgumentException("Invalid customer email or password.");
		return c;
	}

	public Collection<Customer> all() {
		List<Customer> list = new ArrayList<>();
		for (Customer c : customers.values())
			if (c != null)
				list.add(c);
		return list;
	}
}
