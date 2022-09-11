package com.kh.pdata.svc;

import lombok.Data;

@Data
public class CityReq {
  private String perPage;      //perPage	페이지 크기	10	0	1	페이지 크기(기본20)
  private String pageIndex;    //pageIndex	시작 페이지	10	0	0	시작 페이지(기본1)
  private String serviceKey;   //serviceKey	인증키	100	1	인증키(URL Encode)	공공데이터포털에서 발급받은 인증키
  private String city;         //city	시군구	10	0	중구	시군구


}
