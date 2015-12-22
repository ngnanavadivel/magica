package com.codemagic.magica.mapping.core;

public class Property {
	private String name;

	public Property(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Property [name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

}