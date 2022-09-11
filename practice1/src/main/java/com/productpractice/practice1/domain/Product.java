package com.productpractice.practice1.domain;

import lombok.Data;

@Data
public class Product {
  private Long productId;
  private String pname;
  private Integer quantity;
  private Integer price;
}
