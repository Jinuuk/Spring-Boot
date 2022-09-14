package com.great.jinuk.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
  private Long attachment_num;      //  attachment_num         number(6),     첨부파일 번호
  private Long s_article_num;       //  s_article_num          number(6),     판매글 번호
  private Long c_article_num;       //  c_article_num          number(6),     자유게시판 게시글 번호
  private Long n_article_num;       //  n_article_num          number(6),     공지사항 게시글 번호
  private Long review_num;          //  review_num             number(6),     리뷰 번호
  private Long profile_num;         //  profile_num            number(6),     프로필 번호
  private String attachment_name;   //  attachment_name        varchar2(30),  첨부파일 이름
  private String attachment_type;   //  attachment_type        varchar2(12)   첨부파일 유형
}
