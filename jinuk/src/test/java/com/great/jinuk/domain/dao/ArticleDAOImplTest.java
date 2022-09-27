package com.great.jinuk.domain.dao;

import com.great.jinuk.domain.entity.article.Article;
import com.great.jinuk.domain.dao.article.ArticleDAO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
class ArticleDAOImplTest {

  @Autowired
  ArticleDAO articleDAO;

  @Test
  @DisplayName("게시글 목록 조회1 : 전체")
  void findAll() {
    List<Article> list = articleDAO.findAll();

    for (Article article : list) {
      log.info("게시글 : {}",article);
    }

    Assertions.assertThat(list.size()).isEqualTo(4);
  }

  @Test
  @DisplayName("게시글 목록 조회2 : 카테고리별 분류")
  void findByCategory() {
    List<Article> list = articleDAO.findByCategory("문의");

    for (Article article : list) {
      log.info("게시글 : {}",article);
    }

    Assertions.assertThat(list.size()).isEqualTo(4);
  }

  @Test
  @DisplayName("게시글 목록 조회3 : 검색(제목)")
  void findByTitle() {
    List<Article> list = articleDAO.findByTitle("제목4");

    for (Article article : list) {
      log.info("게시글 : {}",article);
    }

    Assertions.assertThat(list.size()).isEqualTo(4);
  }

  @Test
  @DisplayName("게시글 목록 조회4 : 검색(내용)")
  void findByContents() {
    List<Article> list = articleDAO.findByContents("내용3");

    for (Article article : list) {
      log.info("게시글 : {}",article);
    }

    Assertions.assertThat(list.size()).isEqualTo(4);
  }

  @Test
  @DisplayName("게시글 목록 조회6 : 검색(닉네임)")
  void findByNickname() {
    List<Article> list = articleDAO.findByNickname("닉네임2");

    for (Article article : list) {
      log.info("게시글 : {}",article);
    }

    Assertions.assertThat(list.size()).isEqualTo(4);
  }

  @Test
  @DisplayName("게시글 조회")
  void read() {
    Optional<Article> article = articleDAO.read(3L);
    Article foundArticle = article.get();
    log.info("게시글 정보 : {}",foundArticle);

    Assertions.assertThat(foundArticle.getArticleNum()).isEqualTo(3);
    Assertions.assertThat(foundArticle.getArticleCategory()).isEqualTo("부산");
    Assertions.assertThat(foundArticle.getArticleTitle()).isEqualTo("글제목3");
    Assertions.assertThat(foundArticle.getArticleContents()).isEqualTo("글내용3");
    Assertions.assertThat(foundArticle.getAttachment()).isEqualTo("Y");
    Assertions.assertThat(foundArticle.getMember().getMemNickname()).isEqualTo("닉네임2");
    //Assertions.assertThat(foundArticle.getCreateDate())
    Assertions.assertThat(foundArticle.getViews()).isEqualTo(0L);
  }

//  @Test
//  @DisplayName("게시글 등록")
//  void save() {
//    Article article = new Article();
//    article.setMemNumber(1L);
//    article.setArticleCategory("문의");
//    article.setArticleTitle("글제목5");
//    article.setArticleContents("글내용5");
//    article.setAttachment("N");
//    article.setCreateDate();
//  }

//  @Test
//  void update() {
//  }
//
//  @Test
//  void delete() {
//  }
//
//  @Test
//  void generatedArticleNum() {
//  }
}