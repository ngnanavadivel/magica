package com.codemagic.magica;

import java.util.Properties;

public class AutomatorRequest {
   public enum FORMAT {
      FLAT, JSON, XML
   };

   public String     baseDir;
   public FORMAT     destFormat;
   public Properties operations;
   public String     service;
   public String     sourceInstance;
   public FORMAT     srcFormat;
   public String     srcRoot;
   public String     srcSchemaName;
   public String     tgtRoot;
   public String     tgtSchemaName;

   public String getBasePkg() {
      return "com." + service;
   }

   public String getCompiledDir() {
      return baseDir + "/compiled";
   }

   public String getGeneratedDir() {
      return baseDir + "/generated";
   }

   public String getJarDir() {
      return baseDir + "/jar";
   }

   public String getJarFile() {
      return getJarDir() + "/sources.jar";
   }

   public String getMappingFile() {
      return getResourcesDir() + "/mapping.properties";
   }

   public String getOperationsFile() {
      return getResourcesDir() + "/operations.properties";
   }

   public String getResourcesDir() {
      return baseDir + "/resources";
   }

   public String[] getSourceArgs() {
      return new String[] { "-p", getSourcePkg(), getResourcesDir() + "/" + srcSchemaName, "-d", getGeneratedDir() };
   }

   public String getSourcePkg() {
      return getBasePkg() + ".source";
   }

   public String[] getTargetArgs() {
      return new String[] { "-p", getTargetPkg(), getResourcesDir() + "/" + tgtSchemaName, "-d", getGeneratedDir() };
   }

   public String getTargetPkg() {
      return getBasePkg() + ".target";
   }

}