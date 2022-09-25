package com.great.jinuk.domain.dao;

import com.great.jinuk.domain.Article;
import com.great.jinuk.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    sql.append("order by a.article_num desc ");

//    List<Article> articles = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Article.class));
        List<Article> articles = jt.query(sql.toString(), new RowMapper<Article>(){
          @Override
          public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
            Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs,rowNum);
            Article article = (new BeanPropertyRowMapper<>(Article.class)).mapRow(rs,rowNum);
            article.setMember(member);
            return article;
          }
        });

    return articles;
  }

  /**
   * 게시글 목록 조회2 : 카테고리별 분류
   *
   * @param articleCategory 게시글 카테고리
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findByCategory(String articleCategory) {
    StringBuffer sql = new StringBuffer();
    sql.append("select article_num, article_category, article_title, attachment, mem_nickname, create_date, views ");
    sql.append("from article a, member m ");
    sql.append("where a.mem_number = m.mem_number and a.article_category = ? ");
    sql.append("order by a.article_num desc ");

//    List<Article> articles = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Article.class),articleCategory);
    List<Article> articles = jt.query(sql.toString(), new RowMapper<Article>(){
      @Override
      public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs,rowNum);
        Article article = (new BeanPropertyRowMapper<>(Article.class)).mapRow(rs,rowNum);
        article.setMember(member);
        return article;
      }
    },articleCategory);

    return articles;
  }

  /**
   * 게시글 목록 조회3 : 검색(제목)
   *
   * @param articleTitle 게시글 제목
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findByTitle(String articleTitle) {
    StringBuffer sql = new StringBuffer();
    sql.append("select article_num, article_category, article_title, attachment, mem_nickname, create_date, views ");
    sql.append("from article a, member m ");
    sql.append("where a.mem_number = m.mem_number and a.article_title like ? ");
    sql.append("order by a.article_num desc ");

//    List<Article> articles = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Article.class),articleTitle);
    List<Article> articles = jt.query(sql.toString(), new RowMapper<Article>(){
      @Override
      public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs,rowNum);
        Article article = (new BeanPropertyRowMapper<>(Article.class)).mapRow(rs,rowNum);
        article.setMember(member);
        return article;
      }
    },articleTitle);


    return articles;
  }

  /**
   * 게시글 목록 조회4 : 검색(내용)
   *
   * @param articleContents 게시글 내용
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findByContents(String articleContents) {
    StringBuffer sql = new StringBuffer();
    sql.append("select article_num, article_category, article_title, attachment, mem_nickname, create_date, views ");
    sql.append("from article a, member m ");
    sql.append("where a.mem_number = m.mem_number and a.article_contents like ? ");
    sql.append("order by a.article_num desc ");

    //List<Article> articles = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Article.class),articleContents);
    List<Article> articles = jt.query(sql.toString(), new RowMapper<Article>(){
      @Override
      public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs,rowNum);
        Article article = (new BeanPropertyRowMapper<>(Article.class)).mapRow(rs,rowNum);
        article.setMember(member);
        return article;
      }
    },articleContents);


    return articles;
  }

  /**
   * 게시글 목록 조회6 : 검색(닉네임)
   *
   * @param memNickname 회원 닉네임
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findByNickname(String memNickname) {
    StringBuffer sql = new StringBuffer();
    sql.append("select article_num, article_category, article_title, attachment, mem_nickname, create_date, views ");
    sql.append("from article a, member m ");
    sql.append("where a.mem_number = m.mem_number and m.mem_nickname = ? ");
    sql.append("order by a.article_num desc ");

//    List<Article> articles = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Article.class),memNickname);
    List<Article> articles = jt.query(sql.toString(), new RowMapper<Article>(){
      @Override
      public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs,rowNum);
        Article article = (new BeanPropertyRowMapper<>(Article.class)).mapRow(rs,rowNum);
        article.setMember(member);
        return article;
      }
    },memNickname);

    return articles;
  }

  /**
   * 게시글 조회
   *
   * @param articleNum 게시글 번호
   * @return 게시글
   */
  @Override
  public Optional<Article> read(Long articleNum) {
    StringBuffer sql = new StringBuffer();
    sql.append("select article_num, article_category, article_title, article_contents, attachment, mem_nickname, create_date, views ");
    sql.append("from article a, member m ");
    sql.append("where a.mem_number = m.mem_number and a.article_num = ? ");

    try {
//      Article article = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Article.class), articleNum);
      Article article = jt.queryForObject(sql.toString(),new RowMapper<Article>(){
        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
          Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs,rowNum);
          Article article = (new BeanPropertyRowMapper<>(Article.class)).mapRow(rs,rowNum);
          article.setMember(member);
          return article;
        }
      },articleNum);
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
    StringBuffer sql = new StringBuffer();
    sql.append("insert into article (article_num, mem_number, article_category, article_title, article_contents, attachment, create_date,views) ");
    sql.append("values (?,?,?,?,?,?,sysdate,0) ");

    int result = jt.update(sql.toString(),
                          article.getArticleNum(),
                          article.getMemNumber(),
                          article.getArticleCategory(),
                          article.getArticleTitle(),
                          article.getArticleContents(),
                          article.getAttachment());
    return result;
  }

  /**
   * 게시글 수정
   *
   * @param articleNum 게시글 번호
   * @param article     게시글 수정 내용
   */
  @Override
  public int update(Long articleNum, Article article) {
    StringBuffer sql = new StringBuffer();
    sql.append("update article ");
    sql.append("set article_category = ?, article_title = ?, ");
    sql.append("article_contents = ?, attachment = ?, create_date = sysdate ");
    sql.append("where article_num = ? ");

    int affectedRow = jt.update(sql.toString(), article.getArticleCategory() ,article.getArticleTitle(), article.getArticleContents(),article.getAttachment(), articleNum);
    return affectedRow;
  }

  /**
   * 게시글 삭제
   */
  @Override
  public int delete(Long articleNum) {
    String sql = "delete from article where article_num = ? ";
    int affectedRow = jt.update(sql, articleNum);
    return affectedRow;
  }

  /**
   * 신규 게시물 번호 생성
   *
   * @return
   */
  @Override
  public Long generatedArticleNum() {
    String sql = "select article_article_num_seq.nextval from dual ";
    Long articleNum = jt.queryForObject(sql, Long.class);
    return articleNum;
  }
}
