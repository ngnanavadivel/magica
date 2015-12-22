package com.codemagic.magica.mapping.core;

import java.util.ArrayList;
import java.util.List;

import com.codemagic.magica.mapping.operation.Operation;

public class Expression {
	private String			name;
	private List<Node<?>>	operands;
	private List<String>	values	= new ArrayList<String>();

	public String evaluate() throws Exception {
		for (Node<?> operand : operands) {
			if (operand.getContent() instanceof Expression) {
				Expression expr = (Expression) operand.getContent();
				values.add(expr.evaluate());
			} else {
				values.add(((Property) operand.getContent()).getName().trim());
			}
		}
		System.out.println("Looking for class :" + name.trim());
		return ((Operation) Class.forName(OperationsMap.instance().get(name.trim())).newInstance())
				.operate(values.toArray()).toString();
	}

	public Expression(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Node<?>> getOperands() {
		if (operands == null) {
			operands = new ArrayList<Node<?>>();
		}
		return operands;
	}

	public void setOperands(List<Node<?>> operands) {
		this.operands = operands;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Operation [name=");
		builder.append(name);
		builder.append(", operands=");
		builder.append(operands);
		builder.append("]");
		return builder.toString();
	}

}