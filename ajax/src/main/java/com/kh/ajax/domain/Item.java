package com.kh.ajax.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Item {
  private Long id;       //식별자
  private String name;   //상품명
  private String brand;  //상품 브랜드
  private String desc;   //상품설명
  private Long price;    //단가
}
