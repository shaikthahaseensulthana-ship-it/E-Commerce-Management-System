package com.ecommerce.model;

public abstract class Product {
	private final int id;
	private String name;
	private double price;
	private int stock;

	protected Product(int id, String name, double price, int stock) {
		if (id <= 0)
			throw new IllegalArgumentException("Product ID must be positive.");
		this.id = id;
		setName(name);
		setPrice(price);
		setStock(stock);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public int getStock() {
		return stock;
	}

	public abstract String getCategory();

	public void setName(String name) {
		if (name == null || name.trim().isEmpty())
			throw new IllegalArgumentException("Product name cannot be empty.");
		this.name = name.trim();
	}

	public void setPrice(double price) {
		if (!Double.isFinite(price) || price < 0)
			throw new IllegalArgumentException("Price cannot be negative.");
		this.price = price;
	}

	public void setStock(int stock) {
		if (stock < 0)
			throw new IllegalArgumentException("Stock cannot be negative.");
		this.stock = stock;
	}

	public void reduceStock(int quantity) {
		if (quantity <= 0)
			throw new IllegalArgumentException("Quantity must be positive.");
		if (quantity > stock)
			throw new IllegalArgumentException("Insufficient stock for " + name + ".");
		stock -= quantity;
	}

	public void increaseStock(int quantity) {
		if (quantity <= 0)
			throw new IllegalArgumentException("Quantity must be positive.");
		stock += quantity;
	}

	@Override
	public String toString() {
		return String.format("ID: %d | %-22s | %-12s | ₹%.2f | Stock: %d", id, name, getCategory(), price, stock);
	}
}
