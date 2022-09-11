package com.kh.myapp3.web.form.product;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SaveForm {
  private String pname;       //PNAME	VARCHAR2(30 BYTE)	Yes		2
  private Integer quantity;   //QUANTITY	NUMBER(10,0)	Yes		3
  private Integer price;      //PRICE	NUMBER(10,0)	Yes		4
}
