package com.codemagic.magica.mapping.operation;

public class Lowercase implements Operation {

   public String operate(Object... args) {
      if (args != null && args.length > 0) {
         return args[0].toString().toLowerCase();
      }
      return null;
   }

}
