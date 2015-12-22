package com.codemagic.magica.mapping.core;

public class Node<T> {
	private T content;

	public Node(T content) {
		this.content = content;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Node [content=");
		builder.append(content);
		builder.append("]");
		return builder.toString();
	}

}