package com.productpractice.practice1.domain.dao;


import com.productpractice.practice1.domain.Product;
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
  void save(){
    Product product = new Product();
    product.setPname("외장하드");
    product.setQuantity(1);
    product.setPrice(500000);

    Product savedProduct = productDAO.save(product);
    log.info("savedProduct={}",savedProduct);
  }

  @Test
  @DisplayName("조회")
  void findById(){
    Long productId = 61l;
    Product foundProduct = productDAO.findById(productId);
    Assertions.assertThat(foundProduct.getPname()).isEqualTo("치킨");
    Assertions.assertThat(foundProduct.getQuantity()).isEqualTo(2);
    Assertions.assertThat(foundProduct.getPrice()).isEqualTo(36000);
  }

  @Test
  @DisplayName("수정")
  void update(){
    Long productId = 61l;

    Product product = new Product();

    product.setProductId(productId);
    product.setPname("피자");
    product.setQuantity(3);
    product.setPrice(45000);

    productDAO.update(productId,product);

    Product foundProduct = productDAO.findById(productId);
    Assertions.assertThat(foundProduct).isEqualTo(product);
  }

  @Test
  @DisplayName("삭제")
  void delete(){
    Long productId = 81l;
    productDAO.delete(productId);
    Product foundProduct = productDAO.findById(productId);
    Assertions.assertThat(foundProduct).isNull();
  }

  @Test
  @DisplayName("목록")
  void all(){
    List<Product> list = productDAO.findAll();
    Assertions.assertThat(list.size()).isEqualTo(4);
  }

}
