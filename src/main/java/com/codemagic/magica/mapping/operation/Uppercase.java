package com.codemagic.magica.mapping.operation;

public class Uppercase implements Operation {

   public String operate(Object... args) {
      if (args != null && args.length > 0) {
         return args[0].toString().toUpperCase();
      }
      return null;
   }

}
