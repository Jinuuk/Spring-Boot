package com.great.jinuk.web.controller.community;

import com.great.jinuk.domain.common.paging.FindCriteria;
import com.great.jinuk.domain.dao.article.Article;
import com.great.jinuk.domain.dao.article.ArticleFilterCondition;
import com.great.jinuk.domain.svc.article.ArticleSVC;
import com.great.jinuk.domain.svc.uploadFile.UploadFileSVC;
import com.great.jinuk.web.api.ApiResponse;
import com.great.jinuk.web.form.article.ArticleAddForm;
import com.great.jinuk.web.form.article.ArticleEditForm;
import com.great.jinuk.web.form.article.ArticleForm;
import com.great.jinuk.web.form.article.BoardForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.validation.Valid;
import java.util.*;

@Slf4j
//@Controller
//@RequestMapping("/community")
@RequiredArgsConstructor
public class ArticleController_old {

  private final ArticleSVC articleSVC;
  private final UploadFileSVC uploadFileSVC;

  @Autowired
  @Qualifier("fc10") //동일한 타입의 객체가 여러개있을때 빈이름을 명시적으로 지정해서 주입받을때
  private FindCriteria fc;

  //자유게시판 화면 : 전체
  @GetMapping({"/list",
      "/list/{reqPage}",
      "/list/{reqPage}//", //?
      "/list/{reqPage}/{searchType}/{keyword}"})
  public String board(
      @PathVariable(required = false) Optional<Integer> reqPage,
      @PathVariable(required = false) Optional<String> searchType,
      @PathVariable(required = false) Optional<String> keyword,
      @RequestParam(required = false) Optional<String> category, //왜 카테고리만 @RequestParam?
      Model model) {
    log.info("/list 요청됨{},{},{},{}", reqPage, searchType, keyword, category);

    String cate = getCategory(category);

    //FindCriteria 값 설정
    fc.getRc().setReqPage(reqPage.orElse(1)); //요청페이지, 요청없으면 1
    fc.setSearchType(searchType.orElse(""));  //검색유형
    fc.setKeyword(keyword.orElse(""));        //검색어

    List<Article> list = null;
    //게시물 목록 전체
    if (category == null || StringUtils.isEmpty(cate)) { //StringUtils.isEmpty?????

      //검색어 있음
      if (searchType.isPresent() && keyword.isPresent()) {
        ArticleFilterCondition filterCondition = new ArticleFilterCondition(
            "", fc.getRc().getStartRec(), fc.getRc().getEndRec(),
            searchType.get(),
            keyword.get());
        fc.setTotalRec(articleSVC.totalCount(filterCondition));
        fc.setSearchType(searchType.get());
        fc.setKeyword(keyword.get());
        list = articleSVC.findAll(filterCondition);

        //검색어 없음
      } else {
        //총레코드수
        fc.setTotalRec(articleSVC.totalCount());
        list = articleSVC.findAll(fc.getRc().getStartRec(), fc.getRc().getEndRec());
      }

      //카테고리별 목록
    } else {
      //검색어 있음
      if (searchType.isPresent() && keyword.isPresent()) {
        ArticleFilterCondition filterCondition = new ArticleFilterCondition(
            category.get(), fc.getRc().getStartRec(), fc.getRc().getEndRec(),
            searchType.get(),
            keyword.get());
        fc.setTotalRec(articleSVC.totalCount(filterCondition));
        fc.setSearchType(searchType.get());
        fc.setKeyword(keyword.get());
        list = articleSVC.findAll(filterCondition);
        //검색어 없음
      } else {
        fc.setTotalRec(articleSVC.totalCount(cate));
        list = articleSVC.findAll(cate, fc.getRc().getStartRec(), fc.getRc().getEndRec());
      }
    }

    List<BoardForm> partOfList = new ArrayList<>();
    for (Article article : list) {
      BoardForm boardForm = new BoardForm();
      BeanUtils.copyProperties(article, boardForm);
      partOfList.add(boardForm);
    }

    model.addAttribute("list", partOfList);
    model.addAttribute("fc", fc);
    model.addAttribute("category", cate);

    return "/community/board";
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
    Article savedArticle = articleSVC.save(article);
//    Article savedArticle = new Article();

    //주의 : view에서 multiple인 경우 파일 첨부가 없더라도 빈문자열("")이 반환되어
    // List<MultipartFile>에 빈 객체 1개가 포함됨
//    log.info("0");
//    if (articleAddForm.getFiles() == null) {
//      log.info("1");
//      savedArticle = articleSVC.save(article);
//      //이미지 첨부
//    } else if (!articleAddForm.getFiles().get(0).isEmpty()) {
//      log.info("2");
//      savedArticle = articleSVC.save(article, articleAddForm.getFiles());
//    }
//    log.info("3");


//    log.info("savedArticle : {}", article);
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
//    List<UploadFile> uploadFiles = uploadFileSVC.getFilesByCodeWithRid(AttachCode.P0102.name(), articleNum);
//    if (uploadFiles.size() > 0) {
//      List<UploadFile> imageFiles = new ArrayList<>();
//      for (UploadFile file : uploadFiles) {
//        imageFiles.add(file);
//      }
//      articleEditForm.setImageFiles(imageFiles);
//    }

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
    Article updatedArticle = articleSVC.update(articleNum, article);
//    Article updatedArticle = new Article();

//    //메타정보 수정
//    if (articleEditForm.getFiles() == null) {
//      updatedArticle = articleSVC.update(articleNum, article);
//      //사진 첨부
//    } else if (!articleEditForm.getFiles().get(0).isEmpty()) {
//      updatedArticle = articleSVC.update(articleNum, article, articleEditForm.getFiles());
//    }

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
//    List<UploadFile> uploadFiles = uploadFileSVC.getFilesByCodeWithRid(AttachCode.P0102.name(), articleNum);
//    if (uploadFiles.size() > 0) {
//      List<UploadFile> imageFiles = new ArrayList<>();
//      for (UploadFile file : uploadFiles) {
//        imageFiles.add(file);
//      }
//      articleForm.setImageFiles(imageFiles);
//    }

    model.addAttribute("articleForm", articleForm);

    //세션 완성 후 조건문 추가
    //articleForm.getMemNumber()이 세션에 저장된 회원 번호와 일치하다면 return "/community/articleWriter"
    //아니면  return "/community/articleViewer"
    //또는 뷰에서 타임리프 조건문

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

  //쿼리스트링 카테고리 읽기, 없으면 ""반환
  private String getCategory(Optional<String> category) {
    String cate = category.isPresent() ? category.get() : "";
    log.info("category={}", cate);
    return cate;
  }

}
