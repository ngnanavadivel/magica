package com.codemagic.magica.mapping.core;

import java.util.HashMap;

public class OperationsMap extends HashMap<String, String> {
   private static final OperationsMap _instance        = new OperationsMap();
   private static final long          serialVersionUID = 1L;

   public OperationsMap() {
      this.put("Concat", "com.codemagic.magica.Concat2");
      this.put("ToUpper", "com.codemagic.magica.ToUpper");
      this.put("ToLower", "com.codemagic.magica.ToLower");
      this.put("ADD", "com.codemagic.magica.ADD");
      this.put("SUB", "com.codemagic.magica.SUB");
      this.put("MUL", "com.codemagic.magica.MUL");
      this.put("DIV", "com.codemagic.magica.DIV");
   }

   public static OperationsMap instance() {
      return _instance;
   }
}