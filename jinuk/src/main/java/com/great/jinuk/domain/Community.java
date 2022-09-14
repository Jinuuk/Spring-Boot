package com.great.jinuk.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Community {
  private Long article_num;             //  article_num           number(6),    게시글 번호
  private String mem_number;            //  mem_number            number(6),    회원 번호
  private String article_category;      //  article_category      varchar2(6),  게시글 종류
  private String article_title;         //  article_title         varchar2(90), 게시글 제목
  private String article_contents;      //  article_contents      clob,         게시글 내용 string?
  private String attachment;            //  attachment            varchar2(1),  첨부파일 유무
  private LocalDateTime create_date;    //  create_date           date,         작성일
  private Long views;                   //  views                 number(5)     조회수
}
