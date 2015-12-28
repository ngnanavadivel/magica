package com.codemagic.magica.reader;

import static com.codemagic.magica.reader.XmlUtils.eval;
import static com.codemagic.magica.reader.XmlUtils.getDocument;
import static com.codemagic.magica.reader.XmlUtils.getNodes;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.codemagic.magica.reader.HttpRequestConfig.HTTPMETHOD;

public class XmlHttpRequestCfgReader {
   private static final String XPATH_BODY    = "/cfg/body";
   private static final String XPATH_HEADERS = "/cfg/headers/h";
   private static final String XPATH_KEY     = "k";
   private static final String XPATH_METHOD  = "/cfg/method";
   private static final String XPATH_PARAMS  = "/cfg/params/p";
   private static final String XPATH_URL     = "/cfg/url";
   private static final String XPATH_VALUE   = "v";

   HttpRequestConfig parse(InputSource source) {
      HttpRequestConfig cfg = new HttpRequestConfig();
      if (source != null) {
         try {
            Document doc = getDocument(source);
            // URL
            cfg.setUrl(eval(doc, XPATH_URL));

            // HTTP Method
            cfg.setHttpMethod(HTTPMETHOD.valueOf(eval(doc, XPATH_METHOD)));

            // HTTP Parameters.
            NodeList params = getNodes(doc, XPATH_PARAMS);
            if (params != null) {
               int count = params.getLength();
               HashMap<String, String> parameters = new LinkedHashMap<String, String>();
               for (int i = 0; i < count; ++i) {
                  Node param = params.item(i);
                  parameters.put(eval(param, XPATH_KEY), eval(param, XPATH_VALUE));
               }
               if (!parameters.isEmpty()) {
                  cfg.setQueryParameters(parameters);
               }
            }

            // HTTP Headers.
            NodeList hdrNodes = getNodes(doc, XPATH_HEADERS);
            if (hdrNodes != null) {
               int count = hdrNodes.getLength();
               HashMap<String, String> headers = new LinkedHashMap<String, String>();
               for (int i = 0; i < count; ++i) {
                  Node hdr = hdrNodes.item(i);
                  headers.put(eval(hdr, XPATH_KEY), eval(hdr, XPATH_VALUE));
               }
               if (!headers.isEmpty()) {
                  cfg.setRequestHeaders(headers);
               }
            }

            // POST Body if any
            cfg.setRequestBody(eval(doc, XPATH_BODY));

         } catch (Exception e) {
            e.printStackTrace();
         }
      }
      return cfg;
   }

   public static void main(String[] args) throws IOException {
      InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("http.soap.xml");
      HttpRequestConfig cfg = new XmlHttpRequestCfgReader().parse(new InputSource(stream));
      System.out.println(cfg);
      System.out.println(new HttpReader().read(cfg));
   }
}
