package com.kh.pdata.api;

import com.kh.pdata.svc.ApiExplorer;
import com.kh.pdata.svc.CityReq;
import com.kh.pdata.svc.RfcOpenApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/pub")
@RequiredArgsConstructor
public class ApiPubDataController {

  private final ApiExplorer apiExplorer;

  @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
  public RfcOpenApi getJsonData(@RequestParam("cityNm") String cityNm){

    CityReq cityReq = new CityReq();

    cityReq.setPerPage("10");      //perPage	페이지 크기	10	0	1	페이지 크기(기본20)
    cityReq.setPageIndex("1");    //pageIndex	시작 페이지	10	0	0	시작 페이지(기본1)
    cityReq.setCity(cityNm);         //city	시군구	10	0	중구	시군구

    RfcOpenApi rfc = apiExplorer.apiCall(cityReq);
    log.info("rfc={}",rfc);
    return rfc;
  }
  @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
  public RfcOpenApi getXmlData(){
    RfcOpenApi rfc = apiExplorer.apiCall();
    log.info("rfc={}",rfc);
    return rfc;
  }
}
