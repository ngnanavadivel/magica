package com.codemagic.magica.mapping.operation;

import java.util.Arrays;

public class Concat implements Operation {
   // n args :)
   public Object operate(Object... args) {
      String[] stringArr = Arrays.copyOf(args, args.length, String[].class);
      return String.join(" ", stringArr);
   }

   public static void main(String[] args) {
      System.out.println(new Concat().operate("1", "2"));
   }
}
