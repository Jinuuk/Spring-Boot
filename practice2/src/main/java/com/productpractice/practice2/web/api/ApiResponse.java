package com.productpractice.practice2.web.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
  private Header header;
  private T data;

  @Data
  @AllArgsConstructor
  public static class Header {
    private String rtcd;
    private String rtmsg;
  }

  public static <T> ApiResponse<T> createApiRestMsg(String rtcd, String rtmsg, T data) {
//    ApiResponse<T> response = null;
//    Header header = new Header(rtcd, rtmsg);
//    response = new ApiResponse<>(header,data);
//    return response;

    return new ApiResponse<>(new Header(rtcd, rtmsg),data);
  }

}
