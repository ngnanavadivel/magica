package com.codemagic.magica.mapping.core;

import java.util.Stack;

public class Parser {
	private Stack<Node<Expression>> operations = new Stack<Node<Expression>>();

	private StringBuilder holder = new StringBuilder();

	public Node<?> parse(String expr) {
		Node<?> root = null;
		if (expr != null) {
			int size = expr.length();
			for (int i = 0; i < size; ++i) {
				char token = expr.charAt(i);
				if (token == '(') {
					Node<Expression> child = new Node<Expression>(new Expression(holder.toString()));
					if (!operations.isEmpty()) {
						operations.peek().getContent().getOperands().add(child);
					} else {
						root = child;
					}
					operations.push(child);
					holder.setLength(0);
				} else if (token == ')') {
					addAsOperand();
					operations.pop();
				} else if (token == ',') {
					addAsOperand();
				} else {
					holder.append(token);
				}

			}
		}
		return root;
	}

	private void addAsOperand() {
		if (holder.length() > 0) {
			operations.peek().getContent().getOperands()
					.add(new Node<Property>(new Property(holder.toString())));
			holder.setLength(0);
		}
	}
}