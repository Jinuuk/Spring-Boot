--테이블 생성
create table product (
pid number(10) primary key,
pname varchar(30),
count number(10),
price number(10)
)

--시퀀스 생성
create sequence product_product_id_seq;

--시퀀스
select product_product_id_seq.nextval from dual;

--상품등록
insert into product (pid, pname, count, price)
values(product_product_id_seq.nextval,'피자','1',15000);
insert into product (pid, pname, count, price)
values(product_product_id_seq.nextval,'치킨','1',20000);
insert into product (pid, pname, count, price)
values(product_product_id_seq.nextval,'떡볶이','1',15000);

commit;
select * from product;
delete from product;
rollback;

select pid, pname, count, price
from product
where pid = 5;

update product
set pname = '피자(수정)',
    count = 2,
    price = 30000
where pid = 5;

select pid, pname, count, price
from product;


