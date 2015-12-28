package com.codemagic.magica;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

/**
 *
 * @author ákos tajti
 */
public class Compiler {

   /**
    * compiles a java source file with the given <code>fileName</code>
    * 
    * @param fileName
    * @throws IOException
    */
   public void compile(String outDir, String... fileName) throws IOException {
      /*
       * the compiler will send its messages to this listener
       */
      DiagnosticListener listener = new DiagnosticListener() {

         public void report(Diagnostic diagnostic) {
            System.err.println("Error: " + diagnostic.getMessage(null));
            System.err.println("Line: " + diagnostic.getLineNumber());
            System.err.println(diagnostic.getSource());
         }
      };

      // getting the compiler object
      JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
      StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
      Iterable<File> outfiles = Arrays.asList(new File(outDir));
      manager.setLocation(StandardLocation.CLASS_OUTPUT, outfiles);
      Iterable<? extends JavaFileObject> files = manager.getJavaFileObjects(fileName);
      JavaCompiler.CompilationTask task = compiler.getTask(null, manager, listener, null, null, files);

      // the compilation occures here
      task.call();
   }

   public List<String> getFileNames(String dir) {
      return _getFileNames(null, FileSystems.getDefault().getPath(dir));
   }

   private List<String> _getFileNames(List<String> fileNames, Path dir) {
      if (fileNames == null) {
         fileNames = new ArrayList<String>();
      }
      try {
         DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
         for (Path path : stream) {
            if (path.toFile().isDirectory()) {
               _getFileNames(fileNames, path);
            } else {
               String fqName = path.toAbsolutePath().toString().replaceAll("\\\\", "/");
               fileNames.add(fqName);
               System.out.println(fqName);
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return fileNames;
   }

   public static void main(String[] args) throws IOException {

   }
}
