package com.productpractice.practice1.web.form.product;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EditForm {
  private Long productId; //상품아이디
  private String pname;
  private Integer quantity;
  private Integer price;
}
