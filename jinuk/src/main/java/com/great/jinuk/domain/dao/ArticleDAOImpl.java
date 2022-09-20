package com.great.jinuk.domain.dao;

import com.great.jinuk.domain.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ArticleDAOImpl implements ArticleDAO {

  private final JdbcTemplate jt;


  /**
   * 게시글 목록 조회1 : 전체
   *
   * @return 게시글 리스트
   */
  @Override
  public Optional<List<Article>> findAll() {
    StringBuffer sql = new StringBuffer();


    return Optional.empty();
  }

  /**
   * 게시글 목록 조회2 : 카테고리별 분류
   *
   * @param article_category 게시글 카테고리
   * @return 게시글 리스트
   */
  @Override
  public Optional<List<Article>> findByCategory(String article_category) {
    return Optional.empty();
  }

  /**
   * 게시글 목록 조회3 : 검색(제목)
   *
   * @param article_title 게시글 제목
   * @return 게시글 리스트
   */
  @Override
  public Optional<List<Article>> findByTitle(String article_title) {
    return Optional.empty();
  }

  /**
   * 게시글 목록 조회4 : 검색(내용)
   *
   * @param article_contents 게시글 내용
   * @return 게시글 리스트
   */
  @Override
  public Optional<List<Article>> findByContents(String article_contents) {
    return Optional.empty();
  }

  /**
   * 게시글 목록 조회6 : 검색(닉네임)
   *
   * @param mem_nickname 회원 닉네임
   * @return 게시글 리스트
   */
  @Override
  public Optional<List<Article>> findByNickname(String mem_nickname) {
    return Optional.empty();
  }

  /**
   * 게시글 조회
   *
   * @param article_num 게시글 번호
   * @return 게시글
   */
  @Override
  public Optional<Article> read(Long article_num) {
    return Optional.empty();
  }

  /**
   * 게시글 작성
   *
   * @param article 게시글 작성 내용
   * @return 게시글
   */
  @Override
  public Article save(Article article) {
    return null;
  }

  /**
   * 게시글 수정
   *
   * @param article_num 게시글 번호
   * @param article     게시글 수정 내용
   */
  @Override
  public Article update(Long article_num, Article article) {
    return null;
  }

  /**
   * 게시글 삭제
   */
  @Override
  public int delete() {
    return 0;
  }
}
