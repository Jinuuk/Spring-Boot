package com.great.jinuk.web.controller.community;

import com.great.jinuk.domain.Article;
import com.great.jinuk.domain.common.file.AttachCode;
import com.great.jinuk.domain.common.file.UploadFile;
import com.great.jinuk.domain.common.file.UploadFileSVC;
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
  private final UploadFileSVC uploadFileSVC;

  //자유게시판 화면 : 전체
  @GetMapping
  public String board(ArticleSearchForm articleSearchForm, Model model) {
    List<Article> articles = articleSVC.findAll();
    List<Article> list = new ArrayList<>();

    BoardForm boardForm = new BoardForm(); // 왜 필요?

    for (Article article : articles) {
      BeanUtils.copyProperties(article, boardForm); // 왜 필요?
      list.add(article);
    }
    ;

    model.addAttribute("list", list);
    return "/community/board";
  }

  //자유게시판 화면 : 카테고리 분류
  @GetMapping("/category")
  public String boardCategory() {
    return null;
  }

  //자유기시판 화면 : 검색(제목, 내용, 제목+내용, 닉네임)
  @GetMapping("/search")
  public String boardSearch(ArticleSearchForm articleSearchForm, Model model) {
    log.info("articleSearchForm : {}", articleSearchForm);

    List<Article> list = new ArrayList<>();
    List<Article> foundArticles = new ArrayList<>();
    String searchCategory = articleSearchForm.getSearchCategory();
    String searchKeyword = articleSearchForm.getSearchKeyword();


    if (searchCategory.equals("title")) {
      log.info("1");
      foundArticles.addAll(articleSVC.findByTitle("%" + searchKeyword + "%"));
      for (Article article : foundArticles) {
        list.add(article);
        model.addAttribute("list", list);
      }
    } else if (searchCategory.equals("contents")) {
      log.info("2");
      foundArticles.addAll(articleSVC.findByContents("%" + searchKeyword + "%"));
      for (Article article : foundArticles) {
        list.add(article);
        model.addAttribute("list", list);
      }
    } else if (searchCategory.equals("titleOrContents")) {
      log.info("3");
      foundArticles.addAll(articleSVC.findByTitleOrContents("%" + searchKeyword + "%"));
      for (Article article : foundArticles) {
        list.add(article);
        model.addAttribute("list", list);
      }
    } else if (searchCategory.equals("nickname")) {
      log.info("4");
      foundArticles.addAll(articleSVC.findByNickname("%" + searchKeyword + "%"));
      for (Article article : foundArticles) {
        list.add(article);
        model.addAttribute("list", list);
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
  public String writePage(Model model) {
    model.addAttribute("articleAddForm", new ArticleAddForm());
    return "/community/writeForm";
  }

  //글쓰기 처리
  @ResponseBody
  @PostMapping("/write")
  public ApiResponse<Object> write(@Valid ArticleAddForm articleAddForm,
                                   BindingResult bindingResult) {

    log.info("articleAddForm : {}", articleAddForm);

    //검증 : 제목, 내용 글자수 제한
    if (bindingResult.hasErrors()) {
      log.info("bindingResult : {}", bindingResult);
      return ApiResponse.createApiResMsg("99", "실패", getErrMsg(bindingResult));
    }

    Article article = new Article();
    BeanUtils.copyProperties(articleAddForm, article);
    log.info("article : {}", article);
    Article savedArticle = new Article();

    //주의 : view에서 multiple인 경우 파일 첨부가 없더라도 빈문자열("")이 반환되어
    // List<MultipartFile>에 빈 객체 1개가 포함됨
    log.info("0");
    if (articleAddForm.getFiles() == null) {
      log.info("1");
      savedArticle = articleSVC.save(article);
      //이미지 첨부
    } else if (!articleAddForm.getFiles().get(0).isEmpty()) {
      log.info("2");
      savedArticle = articleSVC.save(article, articleAddForm.getFiles());
    }
    log.info("3");


    log.info("savedArticle : {}", savedArticle);
    return ApiResponse.createApiResMsg("00", "성공", savedArticle);
  }

  //글수정 화면
  @GetMapping("edit/{id}")
  public String editPage(@PathVariable("id") Long articleNum,
                         Model model) {

    Optional<Article> foundArticle = articleSVC.read(articleNum);
    ArticleEditForm articleEditForm = new ArticleEditForm();
    if (!foundArticle.isEmpty()) {
      BeanUtils.copyProperties(foundArticle.get(), articleEditForm);
    }

    //2)이미지 조회
    List<UploadFile> uploadFiles = uploadFileSVC.getFilesByCodeWithRid(AttachCode.P0102.name(), articleNum);
    if (uploadFiles.size() > 0) {
      List<UploadFile> imageFiles = new ArrayList<>();
      for (UploadFile file : uploadFiles) {
        imageFiles.add(file);
      }
      articleEditForm.setImageFiles(imageFiles);
    }

    model.addAttribute("articleEditForm", articleEditForm);

    return "/community/editForm";
  }

  //글수정 처리
  @ResponseBody
  @PatchMapping("edit/{id}")
  public ApiResponse<Object> edit(@PathVariable("id") Long articleNum,
                                  @Valid ArticleEditForm articleEditForm,
                                  BindingResult bindingResult) {

    log.info("articleEditForm : {}", articleEditForm);
    //검증 : 제목, 내용 글자수 제한
    if (bindingResult.hasErrors()) {
      log.info("bindingResult : ", bindingResult);
      return ApiResponse.createApiResMsg("99", "실패", getErrMsg(bindingResult));
    }

    Article article = new Article();
    BeanUtils.copyProperties(articleEditForm, article);
    log.info("article : {}", article);
    Article updatedArticle = new Article();

    //메타정보 수정
    if (articleEditForm.getFiles() == null) {
      updatedArticle = articleSVC.update(articleNum, article);
      //사진 첨부
    } else if (!articleEditForm.getFiles().get(0).isEmpty()) {
      updatedArticle = articleSVC.update(articleNum, article, articleEditForm.getFiles());
    }

    return ApiResponse.createApiResMsg("00", "성공", updatedArticle);
  }

  //게시글 조회
  @GetMapping("/article/{id}")
  public String read(@PathVariable("id") Long articleNum,
                     Model model) {

    Optional<Article> foundArticle = articleSVC.read(articleNum);
    ArticleForm articleForm = new ArticleForm();
    if (!foundArticle.isEmpty()) {
      BeanUtils.copyProperties(foundArticle.get(), articleForm);
    }

    //2)게시글 이미지 조회
    List<UploadFile> uploadFiles = uploadFileSVC.getFilesByCodeWithRid(AttachCode.P0102.name(), articleNum);
    if (uploadFiles.size() > 0) {
      List<UploadFile> imageFiles = new ArrayList<>();
      for (UploadFile file : uploadFiles) {
        imageFiles.add(file);
      }
      articleForm.setImageFiles(imageFiles);
    }

    model.addAttribute("articleForm", articleForm);

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

    return ApiResponse.createApiResMsg("00", "성공", null);
  }

  //검증 오류 메시지
  private Map<String, String> getErrMsg(BindingResult bindingResult) {
    Map<String, String> errmsg = new HashMap<>();

    bindingResult.getAllErrors().stream().forEach(objectError -> {
      errmsg.put(objectError.getCodes()[0], objectError.getDefaultMessage());
    });

    return errmsg;
  }

}
