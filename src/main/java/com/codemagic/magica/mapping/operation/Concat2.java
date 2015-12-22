package com.codemagic.magica.mapping.operation;

import java.util.Arrays;

public class Concat2 implements Operation {

	public Object operate(Object... args) {
		System.out.println("Invoking Concat");
		System.out.println("Args : " +  Arrays.toString(args));
		StringBuilder value = new StringBuilder();
		int i = 1;
		for (Object obj : args) {
			value.append("\"" + obj + "\"");
			if (i++ < args.length) {
				value.append(", ");
			}
		}
		return value.toString();
	}
}