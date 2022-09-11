package com.kh.pdata.svc;

public interface ApiExplorer {
  RfcOpenApi apiCall();
  RfcOpenApi apiCall(CityReq cityReq);
}
