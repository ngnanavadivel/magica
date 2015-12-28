package com.codemagic.magica.mapping.operation;

import java.util.Arrays;

public class ADD implements Operation {

   public Object operate(Object... args) {
      System.out.println("Invoking ADD");
      System.out.println("Args : " + Arrays.toString(args));
      int sum = 0;
      for (Object obj : args) {
         sum += Integer.parseInt((String) obj);
      }
      return sum;
   }
}