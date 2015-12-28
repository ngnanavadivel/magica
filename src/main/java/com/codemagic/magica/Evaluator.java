package com.codemagic.magica;

import org.codehaus.jackson.map.ObjectMapper;

import com.codemagic.magica.mapping.core.Expression;
import com.codemagic.magica.mapping.core.Node;
import com.codemagic.magica.mapping.core.Parser;

public class Evaluator {

   public static void main(String[] args) throws Exception {

      // Node<?> node = new Parser().parse("Concat( ToUpper(prop1), Concat(prop2, prop3, ToLower(prop4)))");
      // Node<?> node = new Parser().parse("SUB(DIV(MUL( ADD(5,5), MUL(2,2, ADD(3,5))), 5), 10)");
      // Node<?> node = new Parser().parse("MUL(10,20,5,15,3)");
      Node<?> node = new Parser().parse("DIV(MUL(300000,1,8),100)");
      System.out.println(node);
      System.out.println(((Expression) node.getContent()).evaluate());
      ObjectMapper mapper = new ObjectMapper();
      System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node));
   }
}