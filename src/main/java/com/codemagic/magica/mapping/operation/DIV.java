package com.codemagic.magica.mapping.operation;

import java.util.Arrays;

public class DIV implements Operation {

	public Object operate(Object... args) {
		System.out.println("Invoking DIV");
		System.out.println("Args : " +  Arrays.toString(args));
		return Integer.parseInt((String) args[0]) / Integer.parseInt((String) args[1]);
	}
}