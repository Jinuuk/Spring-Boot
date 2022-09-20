package com.productpractice.practice1;

import com.productpractice.practice1.web.interceptor.LogInterceptor;
import com.productpractice.practice1.web.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  //인터셉터 등록
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    //LogInterceptor : 모든 요청에 대한 log
    registry.addInterceptor(new LogInterceptor())
            .order(1)
            .addPathPatterns("/**")
            .excludePathPatterns("/error");

    //LoginInterceptor : 로그인 인증 체크 (세션 체크)
    List<String> whiteList = new ArrayList<>();
    whiteList.add("/");
    whiteList.add("/login");
    whiteList.add("/logout");
    whiteList.add("/error");
    whiteList.add("/products/**");

    registry.addInterceptor(new LoginInterceptor())
        .order(2)
        .addPathPatterns("/**")
        .excludePathPatterns(whiteList);
  }
}
