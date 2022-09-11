package com.kh.myapp3.web.form.product;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EditForm {
  private Long productId;     //상품아이디
  private String pname;       //PNAME	VARCHAR2(30 BYTE)	Yes		2
  private Integer quantity;   //QUANTITY	NUMBER(10,0)	Yes		3
  private Integer price;      //PRICE	NUMBER(10,0)	Yes		4
}
