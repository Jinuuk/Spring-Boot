package com.great.jinuk.domain.dao.comment;

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
public class CommentDAOImpl implements CommentDAO {

  private final JdbcTemplate jt;

  /**
   * 댓글 조회
   *
   * @param commentNum 댓글 번호
   * @return 댓글
   */
  @Override
  public Optional<Comment> find(Long commentNum) {
    StringBuffer sql = new StringBuffer();

    sql.append("select article_num, comment_group, comment_num, p_comment_num, ");
    sql.append("m.mem_nickname, comment_contents, create_date ");
    sql.append("from comments c, member m ");
    sql.append("where c.mem_number = m.mem_number and c.comment_num = ? ");

    try {
      Comment comment = jt.queryForObject(sql.toString(), new RowMapper<Comment>() {
        @Override
        public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
          Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
          Comment comment = (new BeanPropertyRowMapper<>(Comment.class)).mapRow(rs, rowNum);
          comment.setMember(member);
          return comment;
        }
      }, commentNum);
      return Optional.of(comment);
    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  /**
   * 게시글에 달린 댓글 목록 조회
   *
   * @param articleNum
   * @return 댓글 목록
   */
  @Override
  public List<Comment> findAll(Long articleNum) {
    StringBuffer sql = new StringBuffer();

    sql.append("select article_num, comment_group, comment_num, p_comment_num, ");
    sql.append("m.mem_nickname, comment_contents, create_date ");
    sql.append("from comments c, member m ");
    sql.append("where c.mem_number = m.mem_number and c.article_num = ? ");
    sql.append("order by comment_group asc, comment_num asc ");

    List<Comment> comments = jt.query(sql.toString(), new RowMapper<Comment>() {
      @Override
      public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
        Comment comment = (new BeanPropertyRowMapper<>(Comment.class)).mapRow(rs, rowNum);
        comment.setMember(member);
        return comment;
      }
    },articleNum);

    return comments;
  }

  /**
   * 댓글 작성
   *
   * @param comment 댓글 정보
   * @return 작성된 댓글 수
   */
  @Override
  public int save(Comment comment) {
    StringBuffer sql = new StringBuffer();

    sql.append("insert into comments ");
    sql.append("(article_num, comment_group, comment_num, ");
    sql.append("mem_number, comment_contents, create_date) ");
    sql.append("values (?,comments_comment_group_seq.nextval,?,?,?,sysdate) ");

    int affectedRow = jt.update(sql.toString(),
        comment.getArticleNum(),
//        comment.getCommentGroup(),
        comment.getCommentNum(),
        comment.getMemNumber(),
        comment.getCommentContents());

    return affectedRow;
  }

  /**
   * 대댓글 작성 (필요할까?)
   *
   * @param replyComment 댓글 정보
   * @return 작성된 댓글 수
   */
  @Override
  public int saveReply(Long pCommentNum, Comment replyComment) {

    //부모 댓글 참조 반영
    Comment comment = addInfoOfParentToChild(pCommentNum, replyComment);

    StringBuffer sql = new StringBuffer();

    sql.append("insert into comments ");
    sql.append("(article_num, comment_group, comment_num, p_comment_num, ");
    sql.append("mem_number, comment_contents, create_date) ");
    sql.append("values (?,?,?,?,?,?, sysdate) ");

    int affectedRow = jt.update(sql.toString(),
        comment.getArticleNum(),
        comment.getCommentGroup(),
        comment.getCommentNum(),
        pCommentNum,
        comment.getMemNumber(),
        comment.getCommentContents()
    );

    return affectedRow;
  }

  //대댓글에 부모 댓글 정보 반영
  private Comment addInfoOfParentToChild(Long pCommentNum, Comment replyComment) {

    //부모 댓글
    Optional<Comment> comment = find(pCommentNum);

    //comment group 로직 : 대댓글의 comment group = 댓글의 comment group
    replyComment.setCommentGroup(comment.get().getCommentGroup());

    return replyComment;
  }

  /**
   * 댓글 수정
   *
   * @param commentNum 댓글 번호
   * @param comment    댓글 내용
   * @return
   */
  @Override
  public int update(Long commentNum, Comment comment) {
    StringBuffer sql = new StringBuffer();

    sql.append("update comments ");
    sql.append("set comment_contents = ?, create_date = sysdate ");
    sql.append("where comment_num = ? ");

    int affectedRow = jt.update(sql.toString(),
        comment.getCommentContents(),
        commentNum);

    return affectedRow;
  }

  /**
   * 댓글 삭제
   *
   * @param commentNum 댓글 번호
   * @return 댓글 내용
   */
  @Override
  public int delete(Long commentNum) {
    String sql = "delete from comments where comment_num = ? ";

    int affectedRow = jt.update(sql, commentNum);

    return affectedRow;
  }

  /**
   * 신규 댓글 번호 생성
   *
   * @return 댓글 번호
   */
  @Override
  public Long generatedCommentNum() {
    String sql = "select comments_comment_num_seq.nextval from dual ";
    Long commentNum = jt.queryForObject(sql, Long.class);
    return commentNum;
  }

  /**
   * 게시물 댓글 건수 조회
   *
   * @param articleNum 게시글 번호
   * @return 댓글 건수
   */
  @Override
  public int totalCountOfArticle(Long articleNum) {

    String sql = "select count(*) from comments where article_num = ? ";
    Integer cntPerArticle = jt.queryForObject(sql, Integer.class, articleNum);
    return cntPerArticle;
  }
}
