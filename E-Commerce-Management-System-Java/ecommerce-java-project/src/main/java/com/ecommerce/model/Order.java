package com.ecommerce.model;

import java.time.LocalDateTime;
import java.util.*;

public class Order {
	public enum Status {
		CREATED, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
	}

	private final int orderId;
	private final int customerId;
	private final String customerName;
	private final Map<Product, Integer> items;
	private final double subtotal, discount, total;
	private final String paymentMethod;
	private Status status;
	private final LocalDateTime createdAt;

	public Order(int id, Customer customer, Cart cart, double discount, String paymentMethod) {
		orderId = id;
		customerId = customer.getUserId();
		customerName = customer.getName();
		this.discount = discount;
		this.subtotal = cart.getSubtotal();
		this.total = subtotal - discount;
		this.paymentMethod = paymentMethod;
		items = new LinkedHashMap<>();
		for (CartItem item : cart.getItems())
			items.put(item.getProduct(), item.getQuantity());
		status = Status.CREATED;
		createdAt = LocalDateTime.now();
	}

	public int getOrderId() {
		return orderId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public Map<Product, Integer> getItems() {
		return Collections.unmodifiableMap(items);
	}

	public double getSubtotal() {
		return subtotal;
	}

	public double getDiscount() {
		return discount;
	}

	public double getTotal() {
		return total;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status s) {
		status = s;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String toString() {
		return String.format("Order #%d | Customer: %s | Total: ₹%.2f | Payment: %s | Status: %s | %s", orderId,
				customerName, total, paymentMethod, status, createdAt);
	}
}
