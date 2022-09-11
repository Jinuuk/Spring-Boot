package com.kh.pdata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ApiExplorer {
  private static final String serviceKey = "xU6mazacn%2Fhhkf95xipuffbSCElOvbsKZKleMBmjUpU7Gi5m6f9lmb0mfH2yCfG8xmH0rysA7A7VuUOiVF58IA%3D%3D";
  public static void main(String[] args) throws IOException {

    StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6310000/ulsanrestaurant/getulsanrestaurantList"); /*URL*/
    urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
    urlBuilder.append("&" + URLEncoder.encode("perPage","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 크기(기본20)*/
    urlBuilder.append("&" + URLEncoder.encode("pageIndex","UTF-8") + "=" + URLEncoder.encode("0", "UTF-8")); /*시작 페이지(기본1)*/
    urlBuilder.append("&" + URLEncoder.encode("city","UTF-8") + "=" + URLEncoder.encode("중구", "UTF-8")); /*시군구*/
    URL url = new URL(urlBuilder.toString());
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("accept", "application/xml");
    System.out.println("Response code: " + conn.getResponseCode());
    BufferedReader rd;
    if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
      rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    } else {
      rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
    }
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = rd.readLine()) != null) {
      sb.append(line);
    }
    rd.close();
    conn.disconnect();
    System.out.println(sb.toString());

    String xmlStr = sb.toString();
    //xml to json
    XmlMapper xmlMapper = new XmlMapper();
    JsonNode node = xmlMapper.readTree(xmlStr);
    ObjectMapper jsonMapper = new ObjectMapper();
    String json = jsonMapper.writeValueAsString(node);
    System.out.println(json);

    //xml to java Object
    RfcOpenApi rfc = xmlMapper.readValue(xmlStr, RfcOpenApi.class);
    System.out.println(rfc);
    for(RfcOpenApi.Body.List ele : rfc.getBody().getData()){
      System.out.println("가게명 : "+ele.getCompany());
    }

    //json to java Object
//    RfcOpenApi rfc2 = jsonMapper.readValue(json,RfcOpenApi.class);
//    System.out.println(rfc2);
//    for(RfcOpenApi.Body.List ele : rfc.getBody().getData()){
//      System.out.println("가게명 : "+ele.getCompany());
//    }
  }
}