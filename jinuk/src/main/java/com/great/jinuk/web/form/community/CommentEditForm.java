package com.great.jinuk.web.form.community;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentEditForm {
  private Long comment_num;             //  comment_num          number(6),   댓글 번호
  private Long article_num;             //  article_num          number(6),   게시글 번호
  private String mem_num;               //  mem_number           number(6),   회원 번호
  private String comment_contents;      //  comment_contents     clob,        댓글 내용   string?
  private LocalDateTime create_date;    //  create_date          date,        작성일
  private Long p_comment_num;           //  p_comment_num        number(5),   부모 댓글 번호
  private Integer comment_indent;       //  comment_indent       number(1)    댓글 여백
}
