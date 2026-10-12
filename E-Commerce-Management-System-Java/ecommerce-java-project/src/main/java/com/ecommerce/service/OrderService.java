package com.ecommerce.service;

import com.ecommerce.model.*;
import com.ecommerce.payment.Payment;
import java.util.*;

public class OrderService {
	private final List<Order> orders = new ArrayList<>();
	private int nextId = 1001;

	public Order checkout(Customer customer, Cart cart, double discount, Payment payment) {
		if (customer == null)
			throw new IllegalArgumentException("Please login before checkout.");
		if (cart == null || cart.isEmpty())
			throw new IllegalArgumentException("Cannot checkout an empty cart.");
		double subtotal = cart.getSubtotal();
		if (discount < 0 || discount > subtotal)
			throw new IllegalArgumentException("Invalid discount amount.");
		double total = subtotal - discount;
		for (CartItem i : cart.getItems())
			if (i.getQuantity() > i.getProduct().getStock())
				throw new IllegalArgumentException("Insufficient stock for " + i.getProduct().getName() + ".");
		if (!payment.pay(total))
			throw new IllegalArgumentException("Payment failed; order was not created.");
		Order order = new Order(nextId++, customer, cart, discount, payment.getMethodName());
		for (CartItem i : cart.getItems())
			i.getProduct().reduceStock(i.getQuantity());
		order.setStatus(Order.Status.CONFIRMED);
		orders.add(order);
		customer.getOrders().add(order);
		cart.clear();
		return order;
	}

	public List<Order> all() {
		return Collections.unmodifiableList(orders);
	}

	public List<Order> forCustomer(Customer c) {
		return Collections.unmodifiableList(c.getOrders());
	}

	public void cancel(Order order) {
		if (order.getStatus() == Order.Status.DELIVERED)
			throw new IllegalArgumentException("Delivered orders cannot be cancelled.");
		if (order.getStatus() == Order.Status.CANCELLED)
			throw new IllegalArgumentException("Order is already cancelled.");
		if (order.getStatus() == Order.Status.SHIPPED)
			throw new IllegalArgumentException("Shipped orders cannot be cancelled.");
		for (Map.Entry<Product, Integer> e : order.getItems().entrySet())
			e.getKey().increaseStock(e.getValue());
		order.setStatus(Order.Status.CANCELLED);
	}

	public void changeStatus(int id, Order.Status status) {
		Order o = orders.stream().filter(x -> x.getOrderId() == id).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Order not found."));
		if (o.getStatus() == Order.Status.CANCELLED && status == Order.Status.SHIPPED)
			throw new IllegalArgumentException("Cancelled orders cannot be shipped.");
		if (o.getStatus() == Order.Status.DELIVERED && status == Order.Status.CANCELLED)
			throw new IllegalArgumentException("Delivered orders cannot be cancelled.");
		o.setStatus(status);
	}
}
