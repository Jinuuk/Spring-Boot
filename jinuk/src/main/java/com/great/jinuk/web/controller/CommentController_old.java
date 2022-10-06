package com.great.jinuk.web.controller;

import com.great.jinuk.domain.dao.comment.Comment;
import com.great.jinuk.domain.svc.comment.CommentSVC;
import com.great.jinuk.web.api.ApiResponse;
import com.great.jinuk.web.form.comment.CommentAddForm;
import com.great.jinuk.web.form.comment.CommentEditForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
//@RestController
//@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController_old {

  private final CommentSVC commentSVC;

  //댓글 목록 조회
  @GetMapping("/list/{articleNum}")
  public ApiResponse<List<Comment>> commentList(@PathVariable("articleNum") Long articleNum) {

    List<Comment> commentList = commentSVC.findAll(articleNum);

    return ApiResponse.createApiResMsg("00","성공", commentList);
  }

  //댓글 등록
  @PostMapping("/write/{articleNum}")
  public ApiResponse<Comment> saveComment(@PathVariable("articleNum") Long articleNum,
                                          CommentAddForm commentAddForm) {

    Comment comment = new Comment();
    BeanUtils.copyProperties(commentAddForm,comment);
    comment.setArticleNum(articleNum); //꼭 필요할까?

    //댓글 등록
    Comment savedComment = commentSVC.save(comment);

    return ApiResponse.createApiResMsg("00","성공", savedComment);
  }

//  //대댓글 등록
//  @PostMapping("/reply")
//  public ApiResponse<Comment> saveReplyComment(ArticleForm articleForm,
//                                               CommentAddForm commentAddForm) {
//
//    Comment comment = new Comment();
//    BeanUtils.copyProperties(commentAddForm,comment);
//    comment.setArticleNum(articleForm.getArticleNum()); //꼭 필요할까?
//
//    //대댓글 등록
//    Comment savedReplyComment = commentSVC.saveReply(comment.getPCommentNum(), comment);
//
//    return ApiResponse.createApiResMsg("00","성공", savedReplyComment);
//  }

  //댓글 수정 화면

  @PatchMapping("/edit/{commentNum}")
  //댓글 수정 처리
  public ApiResponse<Comment> editComment(@PathVariable Long commentNum,
                                          CommentEditForm commentEditForm) {

    Comment comment = new Comment();
    BeanUtils.copyProperties(commentEditForm,comment);

    //댓글 수정
    Comment updatedComment = commentSVC.update(commentNum, comment);

    return ApiResponse.createApiResMsg("00","성공", updatedComment);
  }

  @DeleteMapping("/delete/{commentNum}")
  //댓글 삭제
  public ApiResponse<Comment> deleteComment(@PathVariable Long commentNum){

    commentSVC.delete(commentNum);

    return ApiResponse.createApiResMsg("00","성공", null);
  }

}
