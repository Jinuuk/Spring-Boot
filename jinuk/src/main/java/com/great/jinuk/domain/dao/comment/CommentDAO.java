package com.great.jinuk.domain.dao.comment;

import java.util.List;

public interface CommentDAO {

  /**
   * 댓글 목록 조회
   * @return 댓글 목록
   */
  List<Comment> findAll();

  /**
   * 댓글 작성
   * @param comment 댓글 정보
   * @return 작성된 댓글 수
   */
  int save(Comment comment);

  /**
   * 대댓글 작성
   * @param comment 댓글 정보
   * @return 작성된 댓글 수
   */
  int saveReply(Comment comment);


  //댓글 수정

  /**
   * 댓글 수정
   * @param commentNum
   * @param comment
   * @return
   */
  int update(Long commentNum, Comment comment);
  //댓글 삭제
  int delete(Long commentNum);
  //신규 댓글 번호 생성
  Long generatedCommentNum();
}
