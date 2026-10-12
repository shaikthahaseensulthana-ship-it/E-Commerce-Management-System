package com.ecommerce.model;

public class CartItem {
	private final Product product;
	private int quantity;

	public CartItem(Product product, int quantity) {
		if (product == null)
			throw new IllegalArgumentException("Product is required.");
		this.product = product;
		setQuantity(quantity);
	}

	public Product getProduct() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		if (quantity <= 0)
			throw new IllegalArgumentException("Quantity must be at least 1.");
		if (quantity > product.getStock())
			throw new IllegalArgumentException("Quantity exceeds available stock.");
		this.quantity = quantity;
	}

	public double getSubtotal() {
		return product.getPrice() * quantity;
	}
}
