package com.great.jinuk.domain.dao;

import com.great.jinuk.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleDAO {
  // 메소드 오버로딩 사용
  /**
   * 게시글 목록 조회1 : 전체
   * @return 게시글 리스트
   */
  List<Article> findAll();

  /**
   * 게시글 목록 조회2 : 카테고리별 분류
   * @param article_category 게시글 카테고리
   * @return 게시글 리스트
   */
  List<Article> findByCategory(String article_category);

  /**
   * 게시글 목록 조회3 : 검색(제목)
   * @param article_title 게시글 제목
   * @return 게시글 리스트
   */
  List<Article> findByTitle(String article_title);

  /**
   * 게시글 목록 조회4 : 검색(내용)
   * @param article_contents 게시글 내용
   * @return 게시글 리스트
   */
  List<Article> findByContents(String article_contents);

  //게시글 목록 조회5 : 검색(제목+내용) -> SVC

  /**
   * 게시글 목록 조회6 : 검색(닉네임)
   * @param mem_nickname 회원 닉네임
   * @return 게시글 리스트
   */
  List<Article> findByNickname(String mem_nickname);

  /**
   * 게시글 조회
   * @param article_num 게시글 번호
   * @return 게시글
   */
  Optional<Article> read(Long article_num);


  /**
   * 게시글 작성
   * @param article 게시글 작성 내용
   * @return 게시글
   */
  int save(Article article);

  /**
   * 게시글 수정
   * @param article_num 게시글 번호
   * @param article 게시글 수정 내용
   */
  int update(Long article_num, Article article);

  /**
   * 게시글 삭제
   */
  int delete();

  //조회수 상승 -> 나중에
}
