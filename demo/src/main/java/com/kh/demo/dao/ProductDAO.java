package com.kh.demo.dao;

import java.util.List;

public interface ProductDAO {

  //상품등록
  Long save(Product product);

  //상품목록
  List<Product> findAll();

  //상품조회
  Product findByProductId(Long ProductId);

  //상품변경
  int update(Long ProductId, Product product);

  //상품삭제
  int deleteByProductId(Long productId);

}
