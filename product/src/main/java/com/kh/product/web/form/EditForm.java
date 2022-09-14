package com.kh.product.web.form;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class EditForm {
  private Long pid;       //상품번호  pid number(10),

  @NotBlank
  private String pname;   //상품명  pname varchar(30),

  @NotNull
  @Max(value=100)
  @Min(value=1)
  private Integer count;  //상품수량  count number(10),

  @NotNull
  @Min(value=1)
  private Long price;  //상품가격  price number(10)
}
