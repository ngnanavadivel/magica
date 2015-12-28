package com.codemagic.magica;

import java.io.File;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;

import com.codemagic.magica.AutomatorRequest.FORMAT;
import com.codemagic.magica.mapping.operation.Operation;
import com.sun.tools.xjc.Driver;

public class Automator {
   private Properties mpg;
   private Properties tools;

   public String automate(AutomatorRequest req) throws Exception {

      // 1. Generate JAXB class files from target schema.
      generateJAXBClasses(req.getSourceArgs());

      // 2. Generate JAXB class files from target schema.

      generateJAXBClasses(req.getTargetArgs());

      // 3. Compile
      Compiler compiler = new Compiler();
      List<String> fileNames = compiler.getFileNames(req.getGeneratedDir());

      compiler.compile(req.getCompiledDir(), fileNames.toArray(new String[0]));

      // 4. create JAR
      new JarMaker().createJar(req.getCompiledDir(), req.getJarFile());

      // 5. classload the JAR
      JarClassLoader jcl = new JarClassLoader();
      jcl.add(req.getJarFile());

      Object srcObject = null;
      // 6. Load source XML instance.
      if (req.srcFormat == FORMAT.XML) {
         JAXBContext context = JAXBContext.newInstance(req.getSourcePkg(), jcl);

         Unmarshaller unMarshaller = context.createUnmarshaller();
         srcObject = unMarshaller.unmarshal(new StreamSource(new StringReader(req.sourceInstance)), Class.forName(req
               .getSourcePkg() + "." + req.srcRoot, true, jcl));
         if (JAXBElement.class.isInstance(srcObject)) {
            srcObject = ((JAXBElement<?>) srcObject).getValue();
         }
      } else if (req.srcFormat == FORMAT.JSON) {
         ObjectMapper mapper = new ObjectMapper();
         srcObject = mapper.readValue(new StringReader(req.sourceInstance), Class.forName(req.getSourcePkg()
                                                                                          + "."
                                                                                          + req.srcRoot, true, jcl));
      } else {
         return "Flat file support is yet to be implemented";
      }
      // 7. Instantiate target type & update mapping
      Object targetObject = JclObjectFactory.getInstance().create(jcl, req.getTargetPkg() + "." + req.tgtRoot);

      // 7.5 Mappings
      mpg = new Properties();
      mpg.load(FileUtils.openInputStream(new File(req.getMappingFile())));

      tools = new Properties();
      tools.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("operations.properties"));

      for (Object key : mpg.keySet()) {
         String destProperty = (String) key;
         handleMapping(destProperty, srcObject, targetObject);
      }

      if (req.destFormat == FORMAT.JSON) {
         // 8. To JSON
         ObjectMapper mpr = new ObjectMapper();
         String json = mpr.writerWithDefaultPrettyPrinter().writeValueAsString(targetObject);
         System.out.println(json);
         return json;
      } else if (req.destFormat == FORMAT.XML) {
         JAXBContext context = JAXBContext.newInstance(req.getTargetPkg(), jcl);

         Marshaller marshaller = context.createMarshaller();
         StringWriter writer = new StringWriter();
         marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
         marshaller.marshal(targetObject, writer);
         String xml = writer.toString();
         System.out.println(xml);
         return xml;
      } else {
         return "Flat file support is yet to be implemented";
      }
   }

   private void generateJAXBClasses(String[] args) throws Exception {
      Method mtd = Driver.class.getMethod("run", String[].class, PrintStream.class, PrintStream.class);
      mtd.setAccessible(true);
      mtd.invoke(new Driver(), new Object[] { args, System.out, System.out });
   }

   private void handleMapping(String destProperty, Object src, Object dest) throws Exception {
      if (destProperty != null) {
         System.out.println("Reading property : " + destProperty);
         String propValue = mpg.getProperty(destProperty);
         System.out.println("Value : " + propValue);
         String[] fragments = propValue.split(" ");
         if (fragments != null && fragments.length > 1) {
            String operation = fragments[0];
            System.out.println("Operation : " + operation);
            String[] properties = fragments[1].split(",");
            System.out.println("source Properties : " + Arrays.toString(properties));
            // load operation class and perform the operation on properties.
            String operationClass = (String) tools.get(operation);
            if (operationClass != null) {
               Operation instance = (Operation) Class.forName(operationClass).newInstance();
               // Get Values of properties from Source Object.
               List<String> values = new ArrayList<String>();
               for (String srcProperty : properties) {
                  try {
                     if (srcProperty.startsWith("\"") && srcProperty.endsWith("\"")) {
                        String constValue = srcProperty.substring(1, srcProperty.length() - 1);
                        values.add(constValue);
                        System.out.println(srcProperty + " -> " + constValue);
                     } else {
                        String srcValue = (String) PropertyUtils.getNestedProperty(src, srcProperty);
                        values.add(srcValue);
                        System.out.println(srcProperty + " -> " + srcValue);
                     }
                  } catch (Exception e) {
                     System.err.println(e.getMessage());
                  }

               }
               Object result = instance.operate(values.toArray());
               System.out.println("After operation execution : " + destProperty + " -> " + result);
               PropertyUtils.setNestedProperty(dest, destProperty, result);
            }
         } else {
            String result = null;
            // no operations defined.
            if (propValue.startsWith("\"") && propValue.endsWith("\"")) {
               result = propValue.substring(1, propValue.length() - 1);
               System.out.println(propValue + " -> " + result);
            } else {
               result = (String) PropertyUtils.getNestedProperty(src, propValue);
               System.out.println(destProperty + " -> " + result);
            }

            PropertyUtils.setNestedProperty(dest, destProperty, result);
         }
      }
   }

   public static void main(String[] args) throws Exception {

      AutomatorRequest req = new AutomatorRequest();
      req.srcFormat = FORMAT.JSON;
      req.destFormat = FORMAT.XML;
      req.service = "employee.person.integration";
      req.srcSchemaName = "employee.xsd";
      req.tgtSchemaName = "person.xsd";
      req.srcRoot = "Employee";
      req.tgtRoot = "Person";
      req.baseDir = "C:/Users/gnanavad/Documents/stash-all-in-one/magica/src/main";
      if (req.srcFormat == FORMAT.XML) {
         req.sourceInstance = FileUtils.readFileToString(new File(req.getResourcesDir() + "/employee.xml"));
      } else {
         req.sourceInstance = FileUtils.readFileToString(new File(req.getResourcesDir() + "/employee.json"));
      }
      new Automator().automate(req);
   }

}
