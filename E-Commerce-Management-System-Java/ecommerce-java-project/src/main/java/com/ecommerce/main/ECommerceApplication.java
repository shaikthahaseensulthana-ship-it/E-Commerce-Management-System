package com.ecommerce.main;

import com.ecommerce.model.*;
import com.ecommerce.payment.*;
import com.ecommerce.service.*;
import java.util.*;

public class ECommerceApplication {
	private static final Scanner in = new Scanner(System.in);
	private final ProductService products = new ProductService();
	private final CustomerService customers = new CustomerService();
	private final OrderService orders = new OrderService();
	private final DiscountService discounts = new DiscountService();
	private final Admin admin = new Admin(1, "Administrator", "admin@shop.com", "admin123");
	private int nextProductId = 104;

	public static void main(String[] args) {
		new ECommerceApplication().run();
	}

	private ECommerceApplication() {
		seedProducts();
	}

	private void seedProducts() {
		products.add(new Electronics(101, "Laptop", 55000, 10));
		products.add(new Clothing(102, "T-Shirt", 999, 25));
		products.add(new Grocery(103, "Rice 5kg", 450, 30));
	}

	private void run() {
		System.out.println("========================================");
		System.out.println("       E-COMMERCE MANAGEMENT SYSTEM");
		System.out.println("========================================");
		boolean running = true;
		while (running) {
			System.out.println("\nMAIN MENU\n1. Customer Registration\n2. Customer Login\n3. Admin Login\n0. Exit");
			int c = readInt("Choose: ");
			try {
				switch (c) {
				case 1 -> register();
				case 2 -> customerLogin();
				case 3 -> adminLogin();
				case 0 -> running = false;
				default -> System.out.println("Choose a listed option.");
				}
			} catch (RuntimeException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
		System.out.println("Thank you for using E-Commerce Management System.");
	}

	private void register() {
		System.out.println("\nCUSTOMER REGISTRATION");
		Customer c = customers.register(readLine("Name: "), readLine("Email: "), readLine("Password: "),
				readLine("Address: "));
		System.out.println("Registration successful. Your customer ID is " + c.getUserId() + ". Please login.");
	}

	private void customerLogin() {
		Customer c = customers.login(readLine("Email: "), readLine("Password: "));
		System.out.println("Welcome, " + c.getName() + "!");
		customerMenu(c);
	}

	private void adminLogin() {
		String email = readLine("Admin email: "), password = readLine("Admin password: ");
		if (!admin.getEmail().equalsIgnoreCase(email) || !admin.getPassword().equals(password))
			throw new IllegalArgumentException("Invalid admin credentials.");
		System.out.println("Admin login successful.");
		adminMenu();
	}

	private void customerMenu(Customer customer) {
		Cart cart = new Cart();
		boolean active = true;
		while (active) {
			System.out.println(
					"\nCUSTOMER MENU\n1. View Products\n2. Search Product\n3. Add Product to Cart\n4. View Cart\n5. Remove Product from Cart\n6. Update Cart Quantity\n7. Apply Discount\n8. Checkout / Payment\n9. View Orders\n10. Cancel Order\n0. Logout");
			int choice = readInt("Choose: ");
			try {
				switch (choice) {
				case 1 -> showProducts();
				case 2 -> searchProducts();
				case 3 -> addToCart(cart);
				case 4 -> showCart(cart);
				case 5 -> removeFromCart(cart);
				case 6 -> updateCart(cart);
				case 7 -> applyDiscount(cart);
				case 8 -> checkout(customer, cart);
				case 9 -> showCustomerOrders(customer);
				case 10 -> cancelOrder(customer);
				case 0 -> active = false;
				default -> System.out.println("Choose a listed option.");
				}
			} catch (RuntimeException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	private void showProducts() {
		System.out.println("\nAVAILABLE PRODUCTS");
		if (products.all().isEmpty())
			System.out.println("No products available.");
		else
			products.all().forEach(System.out::println);
	}

	private void searchProducts() {
		List<Product> result = products.search(readLine("Enter product name or category: "));
		if (result.isEmpty())
			System.out.println("No matching products found.");
		else
			result.forEach(System.out::println);
	}

	private void addToCart(Cart cart) {
		showProducts();
		int id = readInt("Product ID: ");
		int qty = readInt("Quantity: ");
		cart.add(products.find(id), qty);
		System.out.println("Product added to cart.");
	}

	private void showCart(Cart cart) {
		System.out.println("\nYOUR CART");
		if (cart.isEmpty()) {
			System.out.println("Cart is empty.");
			return;
		}
		for (CartItem i : cart.getItems())
			System.out.printf("%s | %d x ₹%.2f = ₹%.2f%n", i.getProduct().getName(), i.getQuantity(),
					i.getProduct().getPrice(), i.getSubtotal());
		System.out.printf("Subtotal: ₹%.2f%n", cart.getSubtotal());
	}

	private void removeFromCart(Cart cart) {
		showCart(cart);
		cart.remove(readInt("Product ID to remove: "));
		System.out.println("Item removed.");
	}

	private void updateCart(Cart cart) {
		showCart(cart);
		cart.update(readInt("Product ID: "), readInt("New quantity: "));
		System.out.println("Cart quantity updated.");
	}

	private void applyDiscount(Cart cart) {
		if (cart.isEmpty())
			throw new IllegalArgumentException("Your cart is empty.");
		showCart(cart);
		double percent = readDouble("Discount percentage (0-10): ");
		if (percent < 0 || percent > 10)
			throw new IllegalArgumentException("This demo allows discounts from 0% to 10%.");
		double amount = discounts.calculate(cart.getSubtotal(), percent);
		System.out.printf("Discount: ₹%.2f%nFinal amount: ₹%.2f%n", amount, cart.getSubtotal() - amount);
		System.out.println("This is a preview. Enter the same percentage at checkout to apply it.");
	}

	private void checkout(Customer customer, Cart cart) {
		if (cart.isEmpty())
			throw new IllegalArgumentException("Cannot checkout an empty cart.");
		showCart(cart);
		double percent = readDouble("Discount percentage (0-10): ");
		if (percent < 0 || percent > 10)
			throw new IllegalArgumentException("Discount must be between 0 and 10.");
		double discount = discounts.calculate(cart.getSubtotal(), percent), total = cart.getSubtotal() - discount;
		System.out.printf("Discount: ₹%.2f | Amount due: ₹%.2f%n", discount, total);
		System.out.println("Payment method: 1. UPI  2. Card  3. Cash on Delivery");
		Payment payment = switch (readInt("Choose: ")) {
		case 1 -> new UpiPayment(readLine("UPI ID (e.g. name@upi): "));
		case 2 -> new CardPayment(readLine("Cardholder name: "), readLine("Card number (12-19 digits; demo only): "));
		case 3 -> new CashPayment();
		default -> throw new IllegalArgumentException("Invalid payment option.");
		};
		Order order = orders.checkout(customer, cart, discount, payment);
		System.out.println("Order placed successfully: " + order);
	}

	private void showCustomerOrders(Customer c) {
		List<Order> list = orders.forCustomer(c);
		if (list.isEmpty())
			System.out.println("No orders found.");
		else
			list.forEach(System.out::println);
	}

	private void cancelOrder(Customer c) {
		showCustomerOrders(c);
		int id = readInt("Order ID to cancel: ");
		Order order = orders.forCustomer(c).stream().filter(o -> o.getOrderId() == id).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("That order does not belong to your account."));
		orders.cancel(order);
		System.out.println("Order cancelled and stock restored.");
	}

	private void adminMenu() {
		boolean active = true;
		while (active) {
			System.out.println(
					"\nADMIN MENU\n1. Add Product\n2. View Products\n3. Search Product\n4. Update Product\n5. Update Stock\n6. Remove Product\n7. View All Orders\n8. Update Order Status\n9. View Customers\n0. Logout");
			int choice = readInt("Choose: ");
			try {
				switch (choice) {
				case 1 -> addProduct();
				case 2 -> showProducts();
				case 3 -> searchProducts();
				case 4 -> updateProduct();
				case 5 -> updateStock();
				case 6 -> removeProduct();
				case 7 -> showAllOrders();
				case 8 -> updateOrderStatus();
				case 9 -> showCustomers();
				case 0 -> active = false;
				default -> System.out.println("Choose a listed option.");
				}
			} catch (RuntimeException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	private void addProduct() {
		String name = readLine("Product name: ");
		System.out.println("Category: 1. Electronics 2. Clothing 3. Grocery");
		int cat = readInt("Choose category: ");
		double price = readDouble("Price: ");
		int stock = readInt("Initial stock: ");
		Product p = switch (cat) {
		case 1 -> new Electronics(nextProductId++, name, price, stock);
		case 2 -> new Clothing(nextProductId++, name, price, stock);
		case 3 -> new Grocery(nextProductId++, name, price, stock);
		default -> throw new IllegalArgumentException("Invalid category.");
		};
		products.add(p);
		System.out.println("Product added: " + p);
	}

	private void updateProduct() {
		showProducts();
		int id = readInt("Product ID: ");
		Product p = products.find(id);
		String name = readLine("New name [Enter to keep " + p.getName() + "]: ");
		double price = readDouble("New price [enter -1 to keep current price]: ");
		products.update(id, name.isBlank() ? p.getName() : name, price < 0 ? p.getPrice() : price);
		System.out.println("Product updated.");
	}

	private void updateStock() {
		showProducts();
		products.updateStock(readInt("Product ID: "), readInt("New stock quantity: "));
		System.out.println("Stock updated.");
	}

	private void removeProduct() {
		showProducts();
		products.remove(readInt("Product ID to remove: "));
		System.out.println("Product removed.");
	}

	private void showAllOrders() {
		if (orders.all().isEmpty())
			System.out.println("No orders found.");
		else
			orders.all().forEach(System.out::println);
	}

	private void updateOrderStatus() {
		showAllOrders();
		int id = readInt("Order ID: ");
		System.out.println("1. SHIPPED  2. DELIVERED  3. CANCELLED");
		Order.Status status = switch (readInt("New status: ")) {
		case 1 -> Order.Status.SHIPPED;
		case 2 -> Order.Status.DELIVERED;
		case 3 -> Order.Status.CANCELLED;
		default -> throw new IllegalArgumentException("Invalid status.");
		};
		orders.changeStatus(id, status);
		System.out.println("Order status updated.");
	}

	private void showCustomers() {
		if (customers.all().isEmpty())
			System.out.println("No registered customers.");
		else
			for (Customer c : customers.all())
				System.out.println(
						"ID: " + c.getUserId() + " | " + c.getName() + " | " + c.getEmail() + " | " + c.getAddress());
	}

	private static String readLine(String prompt) {
		System.out.print(prompt);
		return in.nextLine().trim();
	}

	private static int readInt(String prompt) {
		while (true) {
			try {
				return Integer.parseInt(readLine(prompt));
			} catch (NumberFormatException e) {
				System.out.println("Please enter a whole number.");
			}
		}
	}

	private static double readDouble(String prompt) {
		while (true) {
			try {
				double n = Double.parseDouble(readLine(prompt));
				if (!Double.isFinite(n))
					throw new NumberFormatException();
				return n;
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid number.");
			}
		}
	}
}
