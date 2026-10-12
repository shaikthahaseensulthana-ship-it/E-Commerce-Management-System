package com.ecommerce.service;

import com.ecommerce.model.*;
import com.ecommerce.exception.ProductNotFoundException;
import java.util.*;

public class ProductService {
	private final Map<Integer, Product> products = new LinkedHashMap<>();

	public void add(Product p) {
		if (products.containsKey(p.getId()))
			throw new IllegalArgumentException("Product ID already exists.");
		products.put(p.getId(), p);
	}

	public Collection<Product> all() {
		return Collections.unmodifiableCollection(products.values());
	}

	public Product find(int id) {
		Product p = products.get(id);
		if (p == null)
			throw new ProductNotFoundException("No product found with ID " + id + ".");
		return p;
	}

	public List<Product> search(String term) {
		String q = term.toLowerCase(Locale.ROOT);
		List<Product> result = new ArrayList<>();
		for (Product p : products.values())
			if (p.getName().toLowerCase(Locale.ROOT).contains(q)
					|| p.getCategory().toLowerCase(Locale.ROOT).contains(q))
				result.add(p);
		return result;
	}

	public void update(int id, String name, double price) {
		Product p = find(id);
		p.setName(name);
		p.setPrice(price);
	}

	public void updateStock(int id, int stock) {
		find(id).setStock(stock);
	}

	public void remove(int id) {
		if (products.remove(id) == null)
			throw new ProductNotFoundException("No product found with ID " + id + ".");
	}
}
