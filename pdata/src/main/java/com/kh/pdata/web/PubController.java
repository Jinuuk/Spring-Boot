package com.kh.pdata.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pub")
public class PubController {

  @GetMapping("")
  public String pubData(){

    return "pub/result";
  }
}
