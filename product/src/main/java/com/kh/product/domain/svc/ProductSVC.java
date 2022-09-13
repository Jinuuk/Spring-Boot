package com.kh.product.domain.svc;

import com.kh.product.domain.Product;

import java.util.List;

public interface ProductSVC {
  /**
   * 등록
   * @param product 상품정보
   * @return 상품
   */
  Product save(Product product);

  /**
   * 조회
   * @param pid 상품번호
   * @return 상품
   */
  Product findById(Long pid);

  /**
   * 수정
   * @param pid 상품번호
   * @param product 변경할 상품 내용
   */
  void update(Long pid, Product product);

  /**
   * 삭제
   * @param pid 상품번호
   */
  void delete(Long pid);

  /**
   * 상품 목록 조회
   * @return 상품목록
   */
  List<Product> findAll();
}
