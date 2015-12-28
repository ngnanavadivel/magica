package com.codemagic.magica.mapping.operation;

import java.util.Arrays;

public class ToUpper implements Operation {
   public Object operate(Object... args) {
      System.out.println("Invoking ToUpper");
      System.out.println("Args : " + Arrays.toString(args));
      return args[0].toString().toUpperCase();
   }
}