package com.great.jinuk.domain.dao;

import com.great.jinuk.domain.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
  public List<Article> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("select article_num, article_category, article_title, attachment, mem_nickname, create_date, views ");
    sql.append("from article a, member m ");
    sql.append("where a.mem_number = m.mem_number ");

    List<Article> articles = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Article.class));

    return articles;
  }

  /**
   * 게시글 목록 조회2 : 카테고리별 분류
   *
   * @param article_category 게시글 카테고리
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findByCategory(String article_category) {
    StringBuffer sql = new StringBuffer();
    sql.append("select article_num, article_category, article_title, attachment, mem_nickname, create_date, views ");
    sql.append("from article a, member m ");
    sql.append("where a.mem_number = m.mem_number and a.article_category = ? ");

    List<Article> articles = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Article.class),article_category);

    return articles;
  }

  /**
   * 게시글 목록 조회3 : 검색(제목)
   *
   * @param article_title 게시글 제목
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findByTitle(String article_title) {
    StringBuffer sql = new StringBuffer();
    sql.append("select article_num, article_category, article_title, attachment, mem_nickname, create_date, views ");
    sql.append("from article a, member m ");
    sql.append("where a.mem_number = m.mem_number and a.article_title like ? ");

    List<Article> articles = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Article.class),article_title);

    return articles;
  }

  /**
   * 게시글 목록 조회4 : 검색(내용)
   *
   * @param article_contents 게시글 내용
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findByContents(String article_contents) {
    StringBuffer sql = new StringBuffer();
    sql.append("select article_num, article_category, article_title, attachment, mem_nickname, create_date, views ");
    sql.append("from article a, member m ");
    sql.append("where a.mem_number = m.mem_number and a.article_contents like ? ");

    List<Article> articles = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Article.class),article_contents);

    return articles;
  }

  /**
   * 게시글 목록 조회6 : 검색(닉네임)
   *
   * @param mem_nickname 회원 닉네임
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findByNickname(String mem_nickname) {
    StringBuffer sql = new StringBuffer();
    sql.append("select article_num, article_category, article_title, attachment, mem_nickname, create_date, views ");
    sql.append("from article a, member m ");
    sql.append("where a.mem_number = m.mem_number and m.mem_nickname = ? ");

    List<Article> articles = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Article.class),mem_nickname);

    return articles;
  }

  /**
   * 게시글 조회
   *
   * @param article_num 게시글 번호
   * @return 게시글
   */
  @Override
  public Optional<Article> read(Long article_num) {
    StringBuffer sql = new StringBuffer();
    sql.append("select article_num, article_category, article_title, article_contents, attachment, mem_nickname, create_date, views ");
    sql.append("from article a, member m ");
    sql.append("where a.mem_number = m.mem_number and a.article_num = ? ");

    try {
      Article article = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Article.class), article_num);
      return Optional.of(article);
    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  /**
   * 게시글 작성
   *
   * @param article 게시글 작성 내용
   * @return 게시글
   */
  @Override
  public int save(Article article) {
    return 0;
  }

  /**
   * 게시글 수정
   *
   * @param article_num 게시글 번호
   * @param article     게시글 수정 내용
   */
  @Override
  public int update(Long article_num, Article article) {
    StringBuffer sql = new StringBuffer();
    sql.append("update article ");
    sql.append("set article_title = ?, article_contents = ?, create_date = ? ");
    sql.append("where article_num = ? ");

    int affectedRow = jt.update(sql.toString(), article.getArticle_title(), article.getArticle_contents(), article.getCreate_date(), article_num);
    return affectedRow;
  }

  /**
   * 게시글 삭제
   */
  @Override
  public int delete(Long article_num) {
    String sql = "delete from article where article_num = ? ";
    int affectedRow = jt.update(sql, article_num);
    return affectedRow;
  }
}
