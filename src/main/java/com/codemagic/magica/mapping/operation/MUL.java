package com.codemagic.magica.mapping.operation;

import java.util.Arrays;

public class MUL implements Operation {

	public Object operate(Object... args) {
		System.out.println("Invoking MUL");
		System.out.println("Args : " +  Arrays.toString(args));
		int result = 1;
		for (Object obj : args) {
			result *= Integer.parseInt((String) obj);
		}
		return result;
	}
}