package com.great.jinuk.web.controller.community;

import com.great.jinuk.web.form.community.ArticleAddForm;
import com.great.jinuk.web.form.community.ArticleEditForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

  @GetMapping
  public String communityMain(){ //자유게시판 메인 화면

    return "/community/community";
  }

  @GetMapping("write")
  public String write(ArticleAddForm articleAddForm){ //글쓰기 화면

    return "/community/writeForm";
  }

  @GetMapping("edit/{id}")
  public String edit(@PathVariable("id") Long id, ArticleEditForm articleEditForm){ //글수정 화면

    return "/community/editForm";
  }


}
