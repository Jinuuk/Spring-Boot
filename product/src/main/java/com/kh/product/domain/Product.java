package com.kh.product.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  private Long pid;       //상품번호  pid number(10),
  private String pname;   //상품명  pname varchar(30),
  private Integer count;  //상품수량  count number(10),
  private Long price;  //상품가격  price number(10)
}
