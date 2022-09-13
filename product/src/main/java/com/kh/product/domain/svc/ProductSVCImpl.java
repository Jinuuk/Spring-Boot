package com.kh.product.domain.svc;

import com.kh.product.domain.Product;
import com.kh.product.domain.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSVCImpl implements ProductSVC {

  private final ProductDAO productDAO;
  /**
   * 등록
   *
   * @param product 상품정보
   * @return 상품
   */
  @Override
  public Product save(Product product) {

    //상품 아이디 생성
    Long generatedProductId = productDAO.generatedProductId();
    product.setPid(generatedProductId);
    productDAO.save(product);
    return productDAO.findById(generatedProductId);
  }

  /**
   * 조회
   *
   * @param pid 상품번호
   * @return 상품
   */
  @Override
  public Product findById(Long pid) {
    return productDAO.findById(pid);
  }

  /**
   * 수정
   *
   * @param pid     상품번호
   * @param product 변경할 상품 내용
   */
  @Override
  public void update(Long pid, Product product) {
    productDAO.update(pid, product);
  }

  /**
   * 삭제
   *
   * @param pid 상품번호
   */
  @Override
  public void delete(Long pid) {
    productDAO.delete(pid);
  }

  /**
   * 상품 목록 조회
   *
   * @return 상품목록
   */
  @Override
  public List<Product> findAll() {
    return productDAO.findAll();
  }
}
