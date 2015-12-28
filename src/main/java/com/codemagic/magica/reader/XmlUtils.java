package com.codemagic.magica.reader;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlUtils {

   public static String eval(Node doc, String xpath_url) throws XPathExpressionException {
      XPathFactory xpathFactory = XPathFactory.newInstance();
      return xpathFactory.newXPath().compile(xpath_url).evaluate(doc);
   }

   public static Document getDocument(InputSource source) throws ParserConfigurationException, SAXException,
         IOException {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(source);
      return doc;
   }

   public static NodeList getNodes(Node doc, String xpath_url) throws XPathExpressionException {
      XPathFactory xpathFactory = XPathFactory.newInstance();
      return (NodeList) xpathFactory.newXPath().compile(xpath_url).evaluate(doc, XPathConstants.NODESET);
   }
}
