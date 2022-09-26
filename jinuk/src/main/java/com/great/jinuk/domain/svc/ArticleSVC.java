package com.great.jinuk.domain.svc;

import com.great.jinuk.domain.Article;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ArticleSVC {
  /**
   * 게시글 목록 조회1 : 전체
   *
   * @return 게시글 리스트
   */
  List<Article> findAll();

  /**
   * 게시글 목록 조회2 : 카테고리별 분류
   *
   * @param articleCategory 게시글 카테고리
   * @return 게시글 리스트
   */
  List<Article> findByCategory(String articleCategory);

  /**
   * 게시글 목록 조회3 : 검색(제목)
   *
   * @param articleTitle 게시글 제목
   * @return 게시글 리스트
   */
  List<Article> findByTitle(String articleTitle);

  /**
   * 게시글 목록 조회4 : 검색(내용)
   *
   * @param articleContents 게시글 내용
   * @return 게시글 리스트
   */
  List<Article> findByContents(String articleContents);

  /**
   * 게시글 목록 조회5 : 검색(제목+내용)
   *
   * @param keyword
   * @return
   */
  List<Article> findByTitleOrContents(String keyword);

  /**
   * 게시글 목록 조회6 : 검색(닉네임)
   *
   * @param memNickname 회원 닉네임
   * @return 게시글 리스트
   */
  List<Article> findByNickname(String memNickname);

  /**
   * 게시글 조회
   *
   * @param articleNum 게시글 번호
   * @return 게시글
   */
  Optional<Article> read(Long articleNum);


  /**
   * 게시글 작성
   *
   * @param article 게시글 작성 내용
   * @return 게시글
   */
  Article save(Article article);

  Article save(Article article, List<MultipartFile> files);

  /**
   * 게시글 수정
   *
   * @param articleNum 게시글 번호
   * @param article    게시글 수정 내용
   */
  Article update(Long articleNum, Article article);

  Article update(Long articleNum, Article article, List<MultipartFile> files);

  /**
   * 게시글 삭제
   */
  void delete(Long articleNum);

  //조회수 상승 -> 나중에
}
