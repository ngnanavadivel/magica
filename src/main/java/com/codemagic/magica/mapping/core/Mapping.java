package com.codemagic.magica.mapping.core;

public class Mapping {
   private Node<?> source;
   private String  target;

   public Node<?> getSource() {
      return source;
   }

   public String getTarget() {
      return target;
   }

   public void setSource(Node<?> source) {
      this.source = source;
   }

   public void setTarget(String target) {
      this.target = target;
   }

}
