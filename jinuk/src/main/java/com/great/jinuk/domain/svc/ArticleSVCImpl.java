package com.great.jinuk.domain.svc;

import com.great.jinuk.domain.Article;
import com.great.jinuk.domain.common.file.AttachCode;
import com.great.jinuk.domain.common.file.FileUtils;
import com.great.jinuk.domain.common.file.UploadFile;
import com.great.jinuk.domain.common.file.UploadFileSVC;
import com.great.jinuk.domain.dao.ArticleDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleSVCImpl implements ArticleSVC {

  private final ArticleDAO articleDAO;
  private final UploadFileSVC uploadFileSVC;
  private final FileUtils fileUtils;

  /**
   * 게시글 목록 조회1 : 전체
   *
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findAll() {
    return articleDAO.findAll();
  }

  /**
   * 게시글 목록 조회2 : 카테고리별 분류
   *
   * @param articleCategory 게시글 카테고리
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findByCategory(String articleCategory) {
    return articleDAO.findByCategory(articleCategory);
  }

  /**
   * 게시글 목록 조회3 : 검색(제목)
   *
   * @param articleTitle 게시글 제목
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findByTitle(String articleTitle) {
    return articleDAO.findByTitle(articleTitle);
  }

  /**
   * 게시글 목록 조회4 : 검색(내용)
   *
   * @param articleContents 게시글 내용
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findByContents(String articleContents) {
    return articleDAO.findByContents(articleContents);
  }

  /**
   * 게시글 목록 조회5 : 검색(제목+내용)
   *
   * @param keyword
   * @return
   */
  @Override
  public List<Article> findByTitleOrContents(String keyword) {

    List<Article> foundList = new ArrayList<>();

    List<Article> listFoundByTitle = articleDAO.findByTitle(keyword);
    List<Article> listFoundByContents = articleDAO.findByContents(keyword);

    foundList.addAll(listFoundByTitle);
    foundList.removeAll(listFoundByContents);
    foundList.addAll(listFoundByContents);

    return foundList;
  }

  /**
   * 게시글 목록 조회6 : 검색(닉네임)
   *
   * @param memNickname 회원 닉네임
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findByNickname(String memNickname) {
    return articleDAO.findByNickname(memNickname);
  }

  /**
   * 게시글 조회
   *
   * @param articleNum 게시글 번호
   * @return 게시글
   */
  @Override
  public Optional<Article> read(Long articleNum) {

    return articleDAO.read(articleNum);
  }

  /**
   * 게시글 작성
   *
   * @param article 게시글 작성 내용
   * @return 게시글
   */
  @Override
  public Article save(Article article) {

    Long generatedArticleNum = articleDAO.generatedArticleNum();
    article.setArticleNum(generatedArticleNum);
    articleDAO.save(article);

    return articleDAO.read(generatedArticleNum).get();
  }


  @Override
  public Article save(Article article, List<MultipartFile> files) {
    Long generatedArticleNum = articleDAO.generatedArticleNum();
    article.setArticleNum(generatedArticleNum);
    articleDAO.save(article);

    //첨부파일-이미지
    uploadFileSVC.addFile(files,AttachCode.P0102,generatedArticleNum);

    return articleDAO.read(generatedArticleNum).get();
  }

  /**
   * 게시글 수정
   *
   * @param articleNum 게시글 번호
   * @param article    게시글 수정 내용
   */
  @Override
  public Article update(Long articleNum, Article article) {
    articleDAO.update(articleNum, article);
    return articleDAO.read(articleNum).get();
  }

  @Override
  public Article update(Long articleNum, Article article, List<MultipartFile> files) {
    articleDAO.update(articleNum, article);

    //2)첨부파일-이미지
    uploadFileSVC.addFile(files,AttachCode.P0102,articleNum);

    return articleDAO.read(articleNum).get();
  }

  /**
   * 게시글 삭제
   *
   * @param articleNum
   */
  @Override
  public void delete(Long articleNum) {
    //1)첨부파일 메타정보 조회
    List<UploadFile> attachFiles = uploadFileSVC.getFilesByCodeWithRid(AttachCode.P0101.name(), articleNum);
    List<UploadFile> imageFiles = uploadFileSVC.getFilesByCodeWithRid(AttachCode.P0102.name(), articleNum);

    //2)스토리지 파일 삭제
    List<UploadFile> unionFiles = new LinkedList<>();
    unionFiles.addAll(attachFiles);
    unionFiles.addAll((imageFiles));
    for (UploadFile file : unionFiles) {
      fileUtils.deleteAttachFile(AttachCode.valueOf(file.getCode()), file.getStoreFilename());
    }

    //3)게시글 삭제
    articleDAO.delete(articleNum);

    //메타정보 삭제
    uploadFileSVC.deleteFileByCodeWithRid(AttachCode.P0101.name(),articleNum);
    uploadFileSVC.deleteFileByCodeWithRid(AttachCode.P0102.name(),articleNum);
  }
}
