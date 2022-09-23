package com.great.jinuk.web.controller.community;

import com.great.jinuk.domain.Article;
import com.great.jinuk.domain.svc.ArticleSVC;
import com.great.jinuk.web.form.community.ArticleAddForm;
import com.great.jinuk.web.form.community.ArticleEditForm;
import com.great.jinuk.web.form.community.ArticleForm;
import com.great.jinuk.web.form.community.BoardForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class ArticleController {

  private final ArticleSVC articleSVC;

  //자유게시판 화면 : 전체
  @GetMapping
  public String board(Model model){
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

  //자유게시판 화면 : 카테고리 분류, 검색
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
  @PostMapping("/write")
  public String write(@Valid @ModelAttribute ArticleAddForm articleAddForm,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {

    //검증 : 제목, 내용 글자수 제한
    if (bindingResult.hasErrors()) {
      log.info("bindingResult : ",bindingResult);
      return "/community/writeForm";
    }

    Article article = new Article();
    BeanUtils.copyProperties(articleAddForm,article);

    Article savedArticle = articleSVC.save(article);
    Long articleNum = savedArticle.getArticleNum();

    redirectAttributes.addAttribute("id",articleNum);
    return "redirect:/community/article/{id}";
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
  @PatchMapping("edit/{id}")
  public String edit(@PathVariable("id") Long articleNum,
                     @Valid @ModelAttribute("articleEditForm") ArticleEditForm articleEditForm,
                     BindingResult bindingResult,
                     RedirectAttributes redirectAttributes) {

    //검증 : 제목, 내용 글자수 제한
    if (bindingResult.hasErrors()) {
      log.info("bindingResult : ",bindingResult);
      return "/community/editForm";
    }

    Article article = new Article();
    BeanUtils.copyProperties(articleEditForm,article);

    articleSVC.update(articleNum,article);

    redirectAttributes.addAttribute("id",articleNum);
    return "redirect:/community/article/{id}";
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

    return "/community/articleViewer";
  }

  //게시글 삭제
  @DeleteMapping("/article/{id}/del")
  public String delete(@PathVariable("id") Long articleNum) {

    articleSVC.delete(articleNum);

    return "redirect:/community";
  }

}
