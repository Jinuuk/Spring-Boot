package com.great.jinuk.web.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T>{
  private Header header;
  private T data;

  @Data
  @AllArgsConstructor
  public static class Header {
    private String rtcd;
    private String rtmsg;
  }

  /**
   * api 응답 메시지 생성
   * @param rtcd 응답 코드
   * @param rtmsg 응답 메시지
   * @param data 데이터
   * @return
   * @param <T> 데이터
   */
  public static <T> ApiResponse<T> createApiResMsg(String rtcd, String rtmsg, T data) { //제네릭 메소드
//    ApiResponse<T> response = null;
//    Header header = new Header(rtcd, rtmsg);
//    response = new ApiResponse<>(header,data);
//    return response;

    return new ApiResponse<>(new Header(rtcd, rtmsg),data);

  }
}