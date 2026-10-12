package com.ecommerce.payment;

public class CardPayment implements Payment {
	private final String holder, lastFour;

	public CardPayment(String holder, String number) {
		if (holder == null || holder.trim().isEmpty())
			throw new IllegalArgumentException("Cardholder name is required.");
		if (number == null || !number.replaceAll("\\s", "").matches("[0-9]{12,19}"))
			throw new IllegalArgumentException("Card number must contain 12 to 19 digits.");
		this.holder = holder.trim();
		this.lastFour = number.replaceAll("\\s", "").substring(number.replaceAll("\\s", "").length() - 4);
	}

	public boolean pay(double amount) {
		System.out.printf("Card payment of ₹%.2f simulated successfully for %s (ending %s).%n", amount, holder,
				lastFour);
		return true;
	}

	public String getMethodName() {
		return "Card";
	}
}
