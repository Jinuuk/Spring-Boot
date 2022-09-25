package com.great.jinuk.web.controller.community;

import com.great.jinuk.domain.Article;
import com.great.jinuk.domain.svc.ArticleSVC;
import com.great.jinuk.web.api.ApiResponse;
import com.great.jinuk.web.api.article.ArticleAddForm;
import com.great.jinuk.web.api.article.ArticleEditForm;
import com.great.jinuk.web.form.community.ArticleForm;
import com.great.jinuk.web.form.community.ArticleSearchForm;
import com.great.jinuk.web.form.community.BoardForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class ArticleController {

  private final ArticleSVC articleSVC;

  //자유게시판 화면 : 전체
  @GetMapping
  public String board(ArticleSearchForm articleSearchForm, Model model){
    List<Article> articles = articleSVC.findAll();
    List<Article> list = new ArrayList<>();

    BoardForm boardForm = new BoardForm(); // 왜 필요?

    for (Article article : articles) {
      BeanUtils.copyProperties(article,boardForm); // 왜 필요?
      list.add(article);
    };

    model.addAttribute("list",list);
    return "/community/board";
  }

  //자유게시판 화면 : 카테고리 분류
  @GetMapping("/category")
  public String boardCategory(){
    return null;
  }

  //자유기시판 화면 : 검색(제목, 내용, 제목+내용, 닉네임)
  @GetMapping("/search")
  public String boardSearch(ArticleSearchForm articleSearchForm, Model model){
    log.info("articleSearchForm : {}",articleSearchForm);

    List<Article> list = new ArrayList<>();
    List<Article> foundArticles = new ArrayList<>();
    String searchCategory = articleSearchForm.getSearchCategory();
    String searchKeyword = articleSearchForm.getSearchKeyword();


    if (searchCategory.equals("title")) {
      log.info("1");
      foundArticles.addAll(articleSVC.findByTitle(searchKeyword));
      for (Article article : foundArticles) {
          list.add(article);
          model.addAttribute("list",list);
        }
    } else if (searchCategory.equals("contents")) {
      log.info("2");
      foundArticles.addAll(articleSVC.findByContents(searchKeyword));
      for (Article article : foundArticles) {
        list.add(article);
        model.addAttribute("list",list);
      }
    } else if (searchCategory.equals("titleOrContents")) {
      log.info("3");
      foundArticles.addAll(articleSVC.findByTitleOrContents(searchKeyword));
      for (Article article : foundArticles) {
        list.add(article);
        model.addAttribute("list",list);
      }
    } else if (searchCategory.equals("nickname")) {
      log.info("4");
      foundArticles.addAll(articleSVC.findByNickname(searchKeyword));
      for (Article article : foundArticles) {
        list.add(article);
        model.addAttribute("list",list);
      }
    }
    log.info("5");
    return "/community/board";
  }

  @ResponseBody
  @PostMapping
  public String sortArticles() {
    return null;
  }

  //글쓰기 화면
  @GetMapping("/write")
  public String writePage(Model model){
    model.addAttribute("articleAddForm", new ArticleAddForm());
    return "/community/writeForm";
  }

  //글쓰기 처리
  @ResponseBody
  @PostMapping("/write")
  public ApiResponse<Object> write(@Valid @RequestBody ArticleAddForm articleAddForm,
                           BindingResult bindingResult) {

    log.info("articleAddForm : {}",articleAddForm);

    //검증 : 제목, 내용 글자수 제한
    if (bindingResult.hasErrors()) {
      log.info("bindingResult : {}",bindingResult);
      return ApiResponse.createApiRestMsg("99", "실패", getErrMsg(bindingResult));
    }

    Article article = new Article();
    BeanUtils.copyProperties(articleAddForm,article);
    log.info("article : {}",article);
    Article savedArticle = articleSVC.save(article);

    return ApiResponse.createApiRestMsg("00", "성공", savedArticle);
  }

  //글수정 화면
  @GetMapping("edit/{id}")
  public String editPage(@PathVariable("id") Long articleNum,
                         Model model){

    Optional<Article> foundArticle = articleSVC.read(articleNum);
    ArticleEditForm articleEditForm = new ArticleEditForm();
    if (!foundArticle.isEmpty()) {
      BeanUtils.copyProperties(foundArticle.get(),articleEditForm);
    }
    model.addAttribute("articleEditForm",articleEditForm);

    return "/community/editForm";
  }

  //글수정 처리
  @ResponseBody
  @PatchMapping("edit/{id}")
  public ApiResponse<Object> edit(@PathVariable("id") Long articleNum,
                     @Valid @RequestBody ArticleEditForm articleEditForm,
                     BindingResult bindingResult) {

    log.info("articleEditForm : {}",articleEditForm);
    //검증 : 제목, 내용 글자수 제한
    if (bindingResult.hasErrors()) {
      log.info("bindingResult : ",bindingResult);
      return ApiResponse.createApiRestMsg("99", "실패", getErrMsg(bindingResult));
    }

    Article article = new Article();
    BeanUtils.copyProperties(articleEditForm,article);
    log.info("article : {}",article);

    articleSVC.update(articleNum,article);


    return ApiResponse.createApiRestMsg("00", "성공", null);
  }

  //게시글 조회
  @GetMapping("/article/{id}")
  public String read(@PathVariable("id") Long articleNum,
                     Model model) {

    Optional<Article> foundArticle = articleSVC.read(articleNum);
    ArticleForm articleForm = new ArticleForm();
    if (!foundArticle.isEmpty()) {
      BeanUtils.copyProperties(foundArticle.get(),articleForm);
    }
    model.addAttribute("articleForm",articleForm);

    //세션 완성 후 조건문 추가
    //articleForm.getMemNumber()이 세션에 저장된 회원 번호와 일치하다면 return "/community/articleWriter"
    //아니면  return "/community/articleViewer"

    return "/community/articleWriter";
  }

  //게시글 삭제
  @ResponseBody
  @DeleteMapping("/article/{id}/del")
  public ApiResponse<Article> delete(@PathVariable("id") Long articleNum) {
    log.info("게시글 번호 : {}", articleNum);
    articleSVC.delete(articleNum);

    return ApiResponse.createApiRestMsg("00", "성공", null);
  }

  //검증 오류 메시지
  private Map<String, String> getErrMsg(BindingResult bindingResult) {
    Map<String, String> errmsg = new HashMap<>();

    bindingResult.getAllErrors().stream().forEach(objectError ->{
      errmsg.put(objectError.getCodes()[0], objectError.getDefaultMessage());
    });

    return errmsg;
  }

}
