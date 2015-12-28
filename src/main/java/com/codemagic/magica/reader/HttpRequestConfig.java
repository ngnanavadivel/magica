package com.codemagic.magica.reader;

import java.util.HashMap;

public class HttpRequestConfig {
   public enum HTTPMETHOD {
      DELETE, GET, POST, PUT
   };

   private HTTPMETHOD              httpMethod;
   private HashMap<String, String> queryParameters;
   private String                  requestBody;
   private HashMap<String, String> requestHeaders;
   private String                  url;

   public HTTPMETHOD getHttpMethod() {
      return httpMethod;
   }

   public HashMap<String, String> getQueryParameters() {
      return queryParameters;
   }

   public String getRequestBody() {
      return requestBody;
   }

   public HashMap<String, String> getRequestHeaders() {
      return requestHeaders;
   }

   public String getUrl() {
      return url;
   }

   public void setHttpMethod(HTTPMETHOD httpMethod) {
      this.httpMethod = httpMethod;
   }

   public void setQueryParameters(HashMap<String, String> queryParameters) {
      this.queryParameters = queryParameters;
   }

   public void setRequestBody(String requestBody) {
      this.requestBody = requestBody;
   }

   public void setRequestHeaders(HashMap<String, String> requestHeaders) {
      this.requestHeaders = requestHeaders;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("HttpRequestConfig [url=");
      builder.append(url);
      builder.append("\n, queryParameters=");
      builder.append(queryParameters);
      builder.append("\n, httpMethod=");
      builder.append(httpMethod);
      builder.append("\n, requestBody=");
      builder.append(requestBody);
      builder.append("\n, requestHeaders=");
      builder.append(requestHeaders);
      builder.append("]");
      return builder.toString();
   }

}
