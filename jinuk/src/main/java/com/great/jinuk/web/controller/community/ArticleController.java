//package com.great.jinuk.web.controller.community;
//
//import com.great.jinuk.domain.Article;
//import com.great.jinuk.domain.svc.ArticleSVC;
//import com.great.jinuk.web.form.community.ArticleAddForm;
//import com.great.jinuk.web.form.community.ArticleEditForm;
//import com.great.jinuk.web.form.community.BoardForm;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Slf4j
//@Controller
//@RequestMapping("/community")
//@RequiredArgsConstructor
//public class ArticleController {
//
//  private final ArticleSVC articleSVC;
//
//  //자유게시판 화면 : 전체
//  @GetMapping
//  public String board(Model model){
//    List<Article> articles = articleSVC.findAll();
//    BoardForm boardForm = new BoardForm();
//
//    for (Article article : articles) {
//      BeanUtils.copyProperties(article,boardForm);
//    }
//
//    return "/community/board";
//  }
//
//  //자유게시판 화면 : 카테고리 분류, 검색
//  @ResponseBody
//  @PostMapping
//  public String sortArticles() {
//    return null;
//  }
//
//  //글쓰기 화면
//  @GetMapping("write")
//  public String writePage(ArticleAddForm articleAddForm){
//    return "/community/writeForm";
//  }
//
//  //글쓰기 처리
//  @PostMapping
//  public String write() {
//    return "community/articleWriter";
//  }
//
//  //글수정 화면
//  @GetMapping("edit/{id}")
//  public String editPage(@PathVariable("id") Long id, ArticleEditForm articleEditForm){
//    return "/community/editForm";
//  }
//
//  //글수정 처리
//  @PatchMapping
//  public String edit() {
//    return "community/articleWriter";
//  }
//
//  //게시글 조회
//  @GetMapping
//  public String read() {
//
//    if (게시글 작성자라면) {
//      return "community/articleWriter";
//    }
//    return "community/articleViewer";
//  }
//
//  //게시글 삭제
//  @DeleteMapping
//  public String delete() {
//    return "/community/board";
//  }
//
//
//}
