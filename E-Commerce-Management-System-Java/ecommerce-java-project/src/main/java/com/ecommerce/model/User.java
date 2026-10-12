package com.ecommerce.model;

public abstract class User {
	private final int userId;
	private String name;
	private final String email;
	private String password;
	private final String role;

	protected User(int userId, String name, String email, String password, String role) {
		this.userId = userId;
		setName(name);
		this.email = require(email, "Email");
		this.password = require(password, "Password");
		this.role = role;
	}

	private static String require(String value, String field) {
		if (value == null || value.trim().isEmpty())
			throw new IllegalArgumentException(field + " cannot be empty.");
		return value.trim();
	}

	public int getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}

	public void setName(String name) {
		this.name = require(name, "Name");
	}

	public void setPassword(String password) {
		this.password = require(password, "Password");
	}
}
