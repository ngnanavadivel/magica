package com.codemagic.magica.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.codemagic.magica.reader.HttpRequestConfig.HTTPMETHOD;

public class HttpReader {
   public String read(HttpRequestConfig cfg) throws IOException {
      String responseString = null;
      // Create a HttpClient.
      CloseableHttpClient client = null;
      CloseableHttpResponse response = null;
      try {
         // Request.
         HttpRequestBase request = null;

         // Create a HttpClient.
         client = HttpClients.createDefault();

         // Set the URI
         URIBuilder uriBuilder = new URIBuilder(cfg.getUrl());
         boolean isPost = cfg.getHttpMethod() == HTTPMETHOD.POST;
         boolean isGet = cfg.getHttpMethod() == HTTPMETHOD.GET;

         if (isGet) {
            // Add Query parameters if any.
            HashMap<String, String> queryParameters = cfg.getQueryParameters();
            if (queryParameters != null && !queryParameters.isEmpty()) {
               for (Entry<String, String> e : queryParameters.entrySet()) {
                  uriBuilder.addParameter(e.getKey(), e.getValue());
               }
            }

            // Initialize the request.
            request = new HttpGet(uriBuilder.build());
         }
         if (isPost) {
            // Add Post Parameters if any.
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            HashMap<String, String> queryParameters = cfg.getQueryParameters();
            if (queryParameters != null && !queryParameters.isEmpty()) {
               for (Entry<String, String> e : queryParameters.entrySet()) {
                  params.add(new BasicNameValuePair(e.getKey(), e.getValue()));
               }
            }

            // Initialize the request.
            request = new HttpPost(uriBuilder.build());
            ((HttpPost) request).setEntity(new UrlEncodedFormEntity(params));
         }

         // Add Request Headers if any.
         HashMap<String, String> requestHeaders = cfg.getRequestHeaders();
         if (requestHeaders != null && !requestHeaders.isEmpty()) {
            for (Entry<String, String> e : requestHeaders.entrySet()) {
               request.addHeader(e.getKey(), e.getValue());
            }
         }

         // Add Request Body if any.
         if (isPost && cfg.getRequestBody() != null && !cfg.getRequestBody().trim().isEmpty()) {
            ((HttpPost) request).setEntity(new StringEntity(cfg.getRequestBody()));
         }

         // Execute the request.
         response = client.execute(request);

         responseString = IOUtils.toString(response.getEntity().getContent());

      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         if (response != null) {
            response.close();
         }
         if (client != null) {
            client.close();
         }
      }
      return responseString;
   }
}
