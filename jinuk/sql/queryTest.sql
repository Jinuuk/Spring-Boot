--[커뮤니티]
--게시글 목록 조회1 : 전체
select article_num "게시글 번호", article_category "말머리", article_title "글 제목", attachment "첨부파일 유무", mem_nickname "닉네임", create_date "작성일", views "조회수"
from article a, member m
where a.mem_number = m.mem_number;

--게시글 목록 조회2 : 카테고리별 분류
select article_num "게시글 번호", article_category "말머리", article_title "글 제목", attachment "첨부파일 유무", mem_nickname "닉네임", create_date "작성일", views "조회수"
from article a, member m
where a.mem_number = m.mem_number and a.article_category = '문의';

--게시글 목록 조회3 : 검색(제목)
select article_num "게시글 번호", article_category "말머리", article_title "글 제목", attachment "첨부파일 유무", mem_nickname "닉네임", create_date "작성일", views "조회수"
from article a, member m
where a.mem_number = m.mem_number and a.article_title like '%제목4%';

--게시글 목록 조회4 : 검색(내용)
select article_num "게시글 번호", article_category "말머리", article_title "글 제목", attachment "첨부파일 유무", mem_nickname "닉네임", create_date "작성일", views "조회수"
from article a, member m
where a.mem_number = m.mem_number and a.article_contents like '%내용3%';

--게시글 목록 조회5 : 검색(제목+내용)
select article_num "게시글 번호", article_category "말머리", article_title "글 제목", attachment "첨부파일 유무", mem_nickname "닉네임", create_date "작성일", views "조회수"
from article a, member m
where a.mem_number = m.mem_number and (a.article_title like '%3%' or a.article_contents like '%3%');

--게시글 목록 조회6 : 검색(닉네임)
select article_num "게시글 번호", article_category "말머리", article_title "글 제목", attachment "첨부파일 유무", mem_nickname "닉네임", create_date "작성일", views "조회수"
from article a, member m
where a.mem_number = m.mem_number and m.mem_nickname = '닉네임2';

--게시글 조회 (3번 게시글)
select article_num "게시글 번호", article_category "말머리", article_title "글 제목", article_contents "글 내용", attachment "첨부파일 유무", mem_nickname "닉네임", create_date "작성일", views "조회수"
from article a, member m
where a.mem_number = m.mem_number and a.article_num = 3;

--게시글 작성
insert into article values (5,3,'인천','글제목5','글내용5','Y',sysdate,0);
select * from article;

--게시글 수정
update article set article_category = '문의', article_title = '글제목5(수정)', article_contents = '글내용5(수정)', attachment = 'N', create_date = sysdate
where article_num = 5;
select * from article;

--게시글 삭제
delete from article where article_num = 5;
select * from article;

--조회수 상승

--[댓글]
--댓글 조회 (1번 게시글)
select mem_nickname "닉네임", comment_contents "댓글 내용", create_date "작성일"
from comments c, member m
where a.mem_number = m.mem_number and a.article_num = 1;

--댓글 작성 (1번 게시글에 댓글 작성)
insert into comments values (5,1,2,'댓글 내용5',sysdate,null,null);

--대댓글 작성 (1번 게시글의 2번 댓글에 대댓글 작성)
insert into comments values (6,1,3,'댓글 내용6',sysdate,2,3);

--댓글 수정
update comments set comment_contents = '댓글 내용1(수정)' where comment_num = 1;

--댓글 삭제
delete from comments where comment_num = 1;

--[첨부파일]
--첨부파일 업로드
insert into attachment values (5,null,2,null,null,null,'첨부파일5','png');
select * from attachment;

--첨부파일 삭제
delete from attachment where attachment_num = 5;
select * from attachment;

--[신고]
--신고 내용 작성
insert into report values (5,2,4,sysdate,4,'신고 내용1',null,4,null,null);
select * from report;

--[제재]
--제재 내용 작성
insert into penalty values (5,1,4,'제재 내용','2022-09-18');
select * from penalty;

--제재 내용 출력 (닉네임1)
select mem_nickname "위반자 닉네임", report_type "위반 유형", penalty_content "제재 사유", penalty_peroid "정지 기간"
from penalty p, member m, report r
where p.d_mem_num = m.mem_number and p.report_num = r.report_num and m.mem_nickname = '닉네임1';

--[회원관리]
--회원 정보 조회
select mem_number "회원 번호", mem_type "회원 유형", mem_id "아이디", mem_name "이름", mem_email "이메일", mem_regtime "가입일", mem_lock_expiration "정지기간"
from member
where mem_nickname = '홍길동';

--판매글 목록 조회
select p_number "번호", p_title "제목", update_date "작성일"
from product_info
where mem_nickname = '홍길동';

--리뷰 목록 조회
select review_number "번호", content "내용", date "작성일"
from review
where mem_nickname = '홍길동';

--커뮤니티글 목록 조회
select article_num "번호", article_title "제목", create_date "작성일"
from article
where mem_nickname = '홍길동';

--댓글 목록 조회
select comment_num "번호", comment_contents "내용", create_date "작성일"
from comment
where mem_nickname = '홍길동';

--판매글 조회
--리뷰 조회
--커뮤니티글 조회
--댓글 조회

--판매글 삭제 (선택 삭제 구현 필요)
delete from product_info
where mem_nickname = '홍길동' and p_number = 1;

--리뷰 삭제 (선택 삭제 구현 필요)
delete from review
where mem_nickname = '홍길동' and review_number = 1;

--커뮤니티글 삭제 (선택 삭제 구현 필요)
delete from article
where mem_nickname = '홍길동' and article_num = 1;

--댓글 삭제 (선택 삭제 구현 필요)
delete from comments
where mem_nickname = '홍길동' and comment_num = 1;


--[작성글 관리]
--판매글 목록 조회
select p_number "번호", p_title "제목", mem_nickname "닉네임", update_date "작성일"
from product_info p, member m
where p.owner_number = m.mem_number;

--리뷰 목록 조회
select review_number "번호", content "내용", mem_nickname "닉네임", date "작성일"
from review r, member m
where r.buyer_number = m.mem_number;

--커뮤니티글 목록 조회
select article_num "번호", article_title "제목", mem_nickname "닉네임", create_date "작성일"
from article a, member m
where a.mem_number = m.mem_number;

--댓글 목록 조회
select comment_num "번호", comment_contents "내용", mem_nickname "닉네임", create_date "작성일"
from comment c, member m
where a.mem_number = m.mem_number;

--판매글 삭제 (선택 삭제 구현 필요)
delete from product_info where p_number = 1;

--리뷰 삭제 (선택 삭제 구현 필요)
delete from review where review_number = 1;

--커뮤니티글 삭제 (선택 삭제 구현 필요)
delete from article where article_num = 1;

--댓글 삭제 (선택 삭제 구현 필요)
delete from comments where comment_num = 1;

--판매글 조회
--리뷰 조회
--커뮤니티글 조회
--댓글 조회
