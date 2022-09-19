package com.great.jinuk.domain.dao;

import com.great.jinuk.domain.Article;

import java.util.List;

public interface ArticleDAO {

  //게시글 목록 조회1 : 전체
  List<Article> findAll();

  //게시글 목록 조회2 : 카테고리별 분류
  List<Article> findByCategory(String article_category);

  //게시글 목록 조회3 : 검색(제목)
  List<Article> findByTitle(String article_title);

  //게시글 목록 조회4 : 검색(내용)
  List<Article> findByContents(String article_contents);

  //게시글 목록 조회5 : 검색(제목+내용) -> SVC

  //게시글 목록 조회6 : 검색(닉네임)
  List<Article> findByNickname(String mem_nickname);

  //게시글 조회
  Article read(Long article_num);

  //게시글 작성
  Article write(Article article);

  //게시글 수정
  void modify(Long article_num, Article article);

  //게시글 삭제
  void delete();

  //조회수 상승
}
