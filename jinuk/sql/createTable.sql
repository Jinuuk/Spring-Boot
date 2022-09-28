--테이블 삭제
drop table penalty;
drop table report;
drop table uploadfile;
drop table comments;
drop table article;
drop table notice;
drop table review;
drop table product_info;
drop table profile;
drop table member;

--시퀀스 삭제
drop sequence article_article_num_seq;
drop sequence comments_comment_num_seq;
drop sequence uploadfile_uploadfile_id_seq;

--회원 임시 테이블 생성
create table member (
  mem_number number(6),
  mem_nickname varchar2(18),
  primary key (mem_number)
);

--판매 임시 테이블 생성
create table product_info (
  p_number number(6),
  primary key (p_number)
);

--리뷰 임시 테이블 생성
create table review (
  review_number number(6),
  primary key (review_number)
);

--공지사항 임시 테이블 생성
create table notice (
  notice_number number(6),
  primary key (notice_number)
);

--프로필 임시 테이블 생성
create table profile (
  profile_number number(6),
  primary key (profile_number)
);

--커뮤니티 테이블 생성
create table article (
  article_num           number(6),
  mem_number            number(6),
  article_category      varchar2(10),
  article_title         varchar2(90),
  article_contents      varchar2(1500),
  attachment            varchar2(1),
  create_date           date,
  views                 number(5)
);
--primary key
alter table article add constraint article_article_num_pk primary key(article_num);
--foreign key
alter table article add constraint article_mem_number_fk foreign key(mem_number) references member(mem_number) on delete cascade;
--default
alter table article modify create_date date default sysdate;
alter table article modify views number(5) default 0;
--not null
alter table article modify article_category constraint article_article_category_nn not null;
alter table article modify article_title constraint article_article_title_nn not null;
alter table article modify article_contents constraint article_article_contents_nn not null;
alter table article modify attachment constraint article_attachment_nn not null;

--게시글 번호 시퀀스 생성
create sequence article_article_num_seq
increment by 1
start with 1
minvalue 1
maxvalue 999999
nocycle
nocache
noorder;

--댓글 테이블 생성
create table comments (
  article_num          number(6),  -- 게시글 번호
  comment_group        number(6),   -- 댓글 그룹
  comment_num          number(6),  -- 댓글 번호
  p_comment_num        number(6),  -- 부모 댓글 번호
  mem_number           number(6),           -- 회원 번호
  comment_contents     varchar2(300),       -- 댓글 내용
  create_date          date,       -- 댓글 생성일
  comment_indent       number(3)  -- 대댓글 들여쓰기
);


--primary key
alter table comments add constraint comments_comment_num_pk primary key(comment_num);
--foreign key
alter table comments add constraint comments_article_num_fk foreign key(article_num) references article(article_num) on delete cascade;
alter table comments add constraint comments_mem_number_fk foreign key(mem_number) references member(mem_number) on delete cascade;
alter table comments add constraint comments_p_comment_num_fk foreign key(p_comment_num) references comments(comment_num) on delete set null;
--default
alter table comments modify create_date date default sysdate;
alter table comments modify comment_indent number default 0;
--not null
alter table comments modify comment_contents constraint comments_comment_contents_nn not null;

--댓글 번호 시퀀스 생성
create sequence comments_comment_num_seq
increment by 1
start with 1
minvalue 1
maxvalue 999999
nocycle
nocache
noorder;

--신고 테이블 생성
create table report (
  report_num         number(6),
  a_mem_num          number(6),
  d_mem_num          number(6),
  report_date        date,
  report_type        number(1),
  report_contents    clob,
  s_article_num      number(6),
  c_article_num      number(6),
  comment_num        number(6),
  review_num         number(6)
);
--primary key
alter table report add constraint report_report_num_pk primary key(report_num);
--foreign key
alter table report add constraint report_a_mem_num_fk foreign key(a_mem_num) references member(mem_number) on delete cascade;
alter table report add constraint report_d_mem_num_fk foreign key(d_mem_num) references member(mem_number) on delete cascade;
alter table report add constraint report_s_article_num_fk foreign key(s_article_num) references product_info(p_number) on delete cascade;
alter table report add constraint report_c_article_num_fk foreign key(c_article_num) references article(article_num) on delete cascade;
alter table report add constraint report_review_num_fk foreign key(review_num) references review(review_number) on delete cascade;
alter table report add constraint report_comment_num_fk foreign key(comment_num) references comments(comment_num ) on delete cascade;
--default
alter table report modify report_date date default sysdate;
--not null
alter table report modify report_type constraint report_report_type_nn not null;
alter table report modify report_contents constraint report_report_contents_nn not null;


--제재 테이블 생성
create table penalty (
  penalty_num         number(6),
  d_mem_num           number(6),
  report_num          number(6),
  penalty_content     clob,
  penalty_peroid      date
);
--primary key
alter table penalty add constraint penalty_penalty_num_pk primary key(penalty_num);
--foreign key
alter table penalty add constraint penalty_report_num_fk foreign key(report_num) references report(report_num) on delete cascade;
alter table penalty add constraint penalty_d_mem_num_fk foreign key(d_mem_num) references member(mem_number) on delete cascade;
--not null
alter table penalty modify penalty_peroid constraint penalty_penalty_peroid_nn not null;

