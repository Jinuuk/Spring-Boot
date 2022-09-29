package com.kh.demo.dao;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductDAOImplTest {

  @Autowired
  private ProductDAO productDAO;

  //등록
  @Test
  @Order(1)
  @DisplayName("상품 등록")
  void save() {
    Product product = new Product();
    product.setProductId(1L);
    product.setPname("콜라");
    product.setPrice(1700L);
    product.setQuantity(1L);

    Long affectedRow = productDAO.save(product);
    Assertions.assertThat(affectedRow).isEqualTo(1L);
  }

  //조회
  @Test
  @Order(2)
  @DisplayName("상품 조회")
  void findByProductId() {

    Optional<Product> foundProduct = productDAO.findByProductId(1L);

    Assertions.assertThat(foundProduct.get().getPname()).isEqualTo("콜라");
    Assertions.assertThat(foundProduct.get().getPrice()).isEqualTo(1700L);
    Assertions.assertThat(foundProduct.get().getQuantity()).isEqualTo(1L);
  }

  //수정
  @Test
  @Order(3)
  @DisplayName("상품 수정")
  void update() {
    Product updatingProduct = new Product();
    updatingProduct.setProductId(1L);
    updatingProduct.setPname("사이다");
    updatingProduct.setPrice(1800L);
    updatingProduct.setQuantity(1L);

    //수정 후 수정된 행 개수 확인
    int affectedRow = productDAO.update(1L, updatingProduct);

    Assertions.assertThat(affectedRow).isEqualTo(1L);

    //수정 후 상품 정보 조회
    Optional<Product> updatedProduct = productDAO.findByProductId(1L);

    //수정된 정보 확인
    Assertions.assertThat(updatedProduct.get().getPname()).isEqualTo("사이다");
    Assertions.assertThat(updatedProduct.get().getPrice()).isEqualTo(1800L);
    Assertions.assertThat(updatedProduct.get().getQuantity()).isEqualTo(1L);
  }

  //삭제
  @Test
  @Order(4)
  @DisplayName("상품 삭제")
  void deleteByProductId() {

    //삭제 후 삭제된 행 개수 확인
    int affectedRow = productDAO.deleteByProductId(1L);
    Assertions.assertThat(affectedRow).isEqualTo(1L);

    //삭제 후 삭제된 상품 아이디로 조회했을 때 empty인 상태가 true인지 나오는지 확인
    Optional<Product> foundProduct = productDAO.findByProductId(1L);
    Assertions.assertThat(foundProduct.isEmpty()).isTrue();

  }

  //목록
  @Test
  @Order(5)
  @DisplayName("상품 목록 조회")
  void findAll() {
    Product product1 = new Product();
    product1.setProductId(1L);
    product1.setPname("콜라");
    product1.setPrice(1700L);
    product1.setQuantity(1L);
    Product product2 = new Product();
    product2.setProductId(2L);
    product2.setPname("사이다");
    product2.setPrice(2000L);
    product2.setQuantity(1L);
    Product product3 = new Product();
    product3.setProductId(3L);
    product3.setPname("씨그램");
    product3.setPrice(1800L);
    product3.setQuantity(1L);
    //상품 3개 등록
    productDAO.save(product1);
    productDAO.save(product2);
    productDAO.save(product3);
    //전체 목록 개수 확인
    List<Product> list = productDAO.findAll();
    Assertions.assertThat(list.size()).isEqualTo(3);
  }

}