package com.productpractice.practice1.domain.dao;

import com.productpractice.practice1.domain.Product;

import java.util.List;

public interface ProductDAO {
  /**
   * 등록
   * @param product 등록할 상품정보
   * @return 등록된 상품
   */
  Product save(Product product);

  /**
   * 조회
   * @param productId 조회할 상품 아이디
   * @return 상품 정보
   */
  Product findById(Long productId);

  /**
   * 수정
   * @param productId 수정하는 상품 아이디
   * @param product 수정할 상품 정보
   */
  void update(Long productId, Product product);

  /**
   * 삭제
   * @param productId 삭제하는 상품 아이디
   */
  void delete(Long productId);


  /**
   * 목록
   * @return 상품 전체
   */
  List<Product> findAll();

  /**
   * 전체삭제
   */
  void deleteAll();

  /**
   *
   * @return 상품 아이디 생성
   */
  Long generatePid();
}
