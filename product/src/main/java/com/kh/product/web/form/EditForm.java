package com.kh.product.web.form;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class EditForm {
  private Long pid;       //상품번호  pid number(10),
  private String pname;   //상품명  pname varchar(30),
  @Max(value=100)
  @Min(value=0)
  private Integer count;  //상품수량  count number(10),
  @Min(value=0)
  private Integer price;  //상품가격  price number(10)
}
