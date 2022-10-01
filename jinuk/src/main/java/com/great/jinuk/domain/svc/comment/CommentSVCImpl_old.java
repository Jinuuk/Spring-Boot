package com.great.jinuk.domain.svc.comment;

import com.great.jinuk.domain.common.FileUtils;
import com.great.jinuk.domain.dao.article.ArticleDAO;
import com.great.jinuk.domain.dao.comment.Comment;
import com.great.jinuk.domain.dao.comment.CommentDAO;
import com.great.jinuk.domain.svc.uploadFile.UploadFileSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentSVCImpl_old implements CommentSVC_old {

  private final CommentDAO commentDAO;
  private final ArticleDAO articleDAO;
  private final UploadFileSVC uploadFileSVC;
  private final FileUtils fileUtils;

  /**
   * 댓글 조회
   *
   * @param commentNum 댓글 번호
   * @return 댓글
   */
  @Override
  public Optional<Comment> find(Long commentNum) {
    return  commentDAO.find(commentNum);
  }

  /**
   * 게시글에 달린 댓글 목록 조회
   *
   * @param articleNum
   * @return 댓글 목록
   */
  @Override
  public List<Comment> findAll(Long articleNum) {

    return commentDAO.findAll(articleNum);
  }

  /**
   * 댓글 작성
   *
   * @param comment 댓글 정보
   * @return 작성된 댓글 수
   */
  @Override
  public Comment save(Comment comment) {
    Long generatedCommentNum = commentDAO.generatedCommentNum();
    comment.setCommentNum(generatedCommentNum);
    commentDAO.save(comment);

    int totalCountOfArticle = commentDAO.totalCountOfArticle(comment.getArticleNum());
    articleDAO.updateCommentsCnt(Long.valueOf(totalCountOfArticle),comment.getArticleNum());

    return commentDAO.find(generatedCommentNum).get();
  }


  /**
   * 대댓글 작성 (필요할까?)
   *
   * @param replyComment 댓글 정보
   * @return 작성된 댓글 수
   */
  @Override
  public Comment saveReply(Long pCommentNum, Comment replyComment) {
    Long generatedCommentNum = commentDAO.generatedCommentNum();
    replyComment.setCommentNum(generatedCommentNum);
    commentDAO.saveReply(pCommentNum,replyComment);

    int totalCountOfArticle = commentDAO.totalCountOfArticle(replyComment.getArticleNum());
    articleDAO.updateCommentsCnt(Long.valueOf(totalCountOfArticle),replyComment.getArticleNum());

    return commentDAO.find(generatedCommentNum).get();
  }

  /**
   * 댓글 수정
   *
   * @param commentNum 댓글 번호
   * @param comment    댓글 내용
   * @return
   */
  @Override
  public Comment update(Long commentNum, Comment comment) {
    commentDAO.update(commentNum,comment);

    int totalCountOfArticle = commentDAO.totalCountOfArticle(comment.getArticleNum());
    articleDAO.updateCommentsCnt(Long.valueOf(totalCountOfArticle),commentDAO.find(commentNum).get().getArticleNum());

    return commentDAO.find(commentNum).get();
  }


  /**
   * 댓글 삭제
   *
   * @param commentNum 댓글 번호
   * @return 댓글 내용
   */
  @Override
  public void delete(Long commentNum) {
    Optional<Comment> foundComment = commentDAO.find(commentNum);
    Long articleNum = foundComment.get().getArticleNum();

    commentDAO.delete(commentNum);

    int totalCountOfArticle = commentDAO.totalCountOfArticle(articleNum);
    articleDAO.updateCommentsCnt(Long.valueOf(totalCountOfArticle),articleNum);
  }

//  /**
//   * 게시물 댓글 건수 조회
//   *
//   * @param articleNum 게시글 번호
//   * @return 댓글 건수
//   */
//  @Override
//  public int totalCountOfArticle(Long articleNum) {
//    return commentDAO.totalCountOfArticle(articleNum);
//  }
}
