package com.kh.myapp3.test;

import com.kh.myapp3.domain.Product;
import com.kh.myapp3.domain.dao.ProductDAO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

//테스트 메소드의 실행 순서
//1)문자와 숫자 조합으로 되어있으면 숫자의 오름차순으로 실행 순서를 결정
//2)클래스 레벨에 @TestMethodOrder, 메소드 레벨에 @Order로 결정
@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JunitTest {

  @Autowired
  private ProductDAO productDAO;
  private static Product testProduct;

  @BeforeAll
  static void beforeAll() {
    log.info("beforeAll() called");
  }

  @Test
  @Order(1)
  @DisplayName("상품등록")
  void test1() {
    log.info("test1() called");
    Product product = new Product(null,"의자",5,300000);
    testProduct = productDAO.save(product);
    Assertions.assertThat(testProduct).isNotNull();
  }

  @Test
  @Order(2)
  @DisplayName("상품조회")
  void test3() {
    log.info("test3() called");
    Product foundProduct = productDAO.findById(testProduct.getProductId());
    Assertions.assertThat(foundProduct).isEqualTo(testProduct);
  }
  
  @Test
  @Order(3)
  @DisplayName("상품수정")
  void test2() {
    log.info("test2() called");
    Long productId = testProduct.getProductId();
    Product product = new Product(testProduct.getProductId(), "책상",7,700000);
    productDAO.update(productId, product);

    Product foundProduct = productDAO.findById(productId);
    Assertions.assertThat(foundProduct).isEqualTo(product);
  }
  
  @Test
  @Order(4)
  @DisplayName("목록")
  @Disabled
  void test4() {
    log.info("test4() called");
    List<Product> products = productDAO.findAll();
    Assertions.assertThat(products.size()).isEqualTo(1);
  }

  @Test
  @Order(5)
  @DisplayName("삭제")
  void test5(){
    log.info("test5() called");
    productDAO.delete(testProduct.getProductId());
    Product foundProduct = productDAO.findById(testProduct.getProductId());
    Assertions.assertThat(foundProduct).isNull();
  }

  @BeforeEach
  void beforeEach() {
    log.info("beforeEach() called");
  }

  @AfterEach
  void afterEach() {
    log.info("afterEach() called");
    List<Product> products = productDAO.findAll();
    products.stream().forEach(product->{
      log.info("{}",product);
    });
  }


  @AfterAll
  static void afterAll() {
    log.info("afterAll() called");
  }
}
