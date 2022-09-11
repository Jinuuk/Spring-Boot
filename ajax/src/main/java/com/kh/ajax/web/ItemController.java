package com.kh.ajax.web;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Controller
@RequestMapping("/items")
public class ItemController {

  @GetMapping
  public String itemSvc(){

    return "itemsvc";
  }
}
