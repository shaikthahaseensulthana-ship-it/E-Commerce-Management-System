package com.ecommerce.model;

import java.util.*;

public class Cart {
	private final Map<Integer, CartItem> items = new LinkedHashMap<>();

	public Collection<CartItem> getItems() {
		return Collections.unmodifiableCollection(items.values());
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public void add(Product product, int quantity) {
		if (quantity <= 0)
			throw new IllegalArgumentException("Quantity must be positive.");
		if (quantity > product.getStock())
			throw new IllegalArgumentException("Only " + product.getStock() + " item(s) in stock.");
		CartItem old = items.get(product.getId());
		int total = quantity + (old == null ? 0 : old.getQuantity());
		if (total > product.getStock())
			throw new IllegalArgumentException("Cart quantity exceeds stock.");
		if (old == null)
			items.put(product.getId(), new CartItem(product, quantity));
		else
			old.setQuantity(total);
	}

	public void remove(int productId) {
		if (items.remove(productId) == null)
			throw new IllegalArgumentException("Product is not in your cart.");
	}

	public void update(int productId, int quantity) {
		CartItem item = items.get(productId);
		if (item == null)
			throw new IllegalArgumentException("Product is not in your cart.");
		item.setQuantity(quantity);
	}

	public double getSubtotal() {
		return items.values().stream().mapToDouble(CartItem::getSubtotal).sum();
	}

	public void clear() {
		items.clear();
	}
}
