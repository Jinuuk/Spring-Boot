package com.kh.myapp3.domain.dao;

import com.kh.myapp3.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class ProductDAOImplTest {

  @Autowired
  ProductDAO productDAO;

  @Test
  @DisplayName("상품등록")
  void save() {
    Product product = new Product();
    product.setPname("외장하드");
    product.setQuantity(1);
    product.setPrice(500000);

    Product savedProduct = productDAO.save(product);
    log.info("savedProduct={}", savedProduct);
  }

  @Test
  @DisplayName("조회")
  void findById() {
    Long productId = 45l;
    Product foundProduct = productDAO.findById(productId);
    Assertions.assertThat(foundProduct.getPname()).isEqualTo("스피커");
    Assertions.assertThat(foundProduct.getQuantity()).isEqualTo(2);
    Assertions.assertThat(foundProduct.getPrice()).isEqualTo(20000);
  }

  @Test
  @DisplayName("수정")
  void update() {
    Long productId = 45l;

    Product product = new Product();

    product.setProductId(productId);
    product.setPname("고급 스피커");
    product.setQuantity(5);
    product.setPrice(250000);

    productDAO.update(productId, product);

    Product foundProduct = productDAO.findById(productId);
//    Assertions.assertThat(foundProduct.getPname()).isEqualTo("고급 스피커");
//    Assertions.assertThat(foundProduct.getQuantity()).isEqualTo(5);
//    Assertions.assertThat(foundProduct.getPrice()).isEqualTo(250000);
    Assertions.assertThat(foundProduct).isEqualTo(product);
  }

  @Test
  @DisplayName("삭제")
  void delete(){
    Long productId = 45l;
    productDAO.delete(productId);
    Product foundProduct = productDAO.findById(productId);
    Assertions.assertThat(foundProduct).isNull();
  }

  @Test
  @DisplayName("목록")
  void all(){
    List<Product> list = productDAO.findAll();
//    log.info("전체목록 = {}",list);
    //람다식
//    list.stream().forEach(ele->log.info("상품 : {}",ele));

    //향상된 for문
    for(Product p : list){
      log.info("상품 : {}",p);
    }

    Assertions.assertThat(list.size()).isEqualTo(8);
  }
}
