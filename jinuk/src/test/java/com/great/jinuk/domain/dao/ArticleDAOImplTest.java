package com.great.jinuk.domain.dao;

import com.great.jinuk.domain.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
  }

  @Test
  void findByCategory() {
  }

  @Test
  void findByTitle() {
  }

  @Test
  void findByContents() {
  }

  @Test
  void findByNickname() {
  }

  @Test
  void read() {
  }

  @Test
  void save() {
  }

  @Test
  void update() {
  }

  @Test
  void delete() {
  }

  @Test
  void generatedArticleNum() {
  }
}