package com.kh.myapp3.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

  public static final String TRANSACTION_ID = "transactionId";

  //컨트롤러 호출 전
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    log.info("preHandle called!");
    String requestURI = request.getRequestURI(); //클라이언트의 요청 url

    String uuid = UUID.randomUUID().toString(); //요청 추적 번호
    request.setAttribute(TRANSACTION_ID,uuid); //컨트롤+알트+C : 상수화, 컨트롤+알트+M : 메소드화

    log.info("REQUEST [{}][{}][{}]",uuid,requestURI,handler);
    return true;
  }

  //컨트롤러 수행 후 view가 렌더링 되기 전
  //컨트롤러에서 예외 발생 시 호출되지 않음
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    log.info("postHandle called!");
    String requestURI = request.getRequestURI(); //클라이언트의 요청 url
    String uuid = (String)request.getAttribute(TRANSACTION_ID);
    log.info("REQUEST [{}][{}][{}]",uuid,requestURI,handler);
  }

  //view가 렌더링되고 응답 메시지가 클라이언트에 전송된 후
  //컨트롤로에서 예외 발생 유무에 상관없이 항상 호출
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    log.info("afterCompletion called!");
    String requestURI = request.getRequestURI(); //클라이언트의 요청 url
    String uuid = (String)request.getAttribute(TRANSACTION_ID);
    log.info("REQUEST [{}][{}][{}]",uuid,requestURI,handler);

    if (ex != null) {
      log.error("afterCompletion error!!", ex);
    }
  }
}
