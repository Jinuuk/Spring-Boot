package com.kh.pdata;

import lombok.Data;

import java.util.ArrayList;

@Data
public class RfcOpenApi {
  private Header header;


  @Data
  static class Header {
    private String resultCode;
    private String resultMsg;
  }

  private Body body;

  @Data
  static class Body {
    private String pageIndex;
    private String numOfRows;
    private String pageNo;
    private String totalCount;
    private ArrayList<List> data;

    @Data
    static class List {
      private String company;
      private String lat;
      private String lng;
      private String foodType;
      private String city;
      private String address;
      private String phoneNumber;
      private String mainMenu;
    }
  }
}

