--���̺� ����
create table product (
pid number(10) primary key,
pname varchar(30),
count number(10),
price number(10)
)

--������ ����
create sequence product_product_id_seq;

--������
select product_product_id_seq.nextval from dual;

--��ǰ���
insert into product (pid, pname, count, price)
values(product_product_id_seq.nextval,'����','1',15000);
insert into product (pid, pname, count, price)
values(product_product_id_seq.nextval,'ġŲ','1',20000);
insert into product (pid, pname, count, price)
values(product_product_id_seq.nextval,'������','1',15000);

commit;
select * from product;
delete from product;
rollback;

select pid, pname, count, price
from product
where pid = 5;

update product
set pname = '����(����)',
    count = 2,
    price = 30000
where pid = 5;

select pid, pname, count, price
from product;


