package com.productpractice.practice2.web;

import com.productpractice.practice2.domain.product.Product;
import com.productpractice.practice2.domain.product.ProductSVC;
import com.productpractice.practice2.web.api.ApiResponse;
import com.productpractice.practice2.web.api.product.AddReq;
import com.productpractice.practice2.web.api.product.EditReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j

//@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiProductControllerV2 {

  private final ProductSVC productSVC;

  //등록
  @PostMapping("/products")
  public ApiResponse<Long> add(@RequestBody AddReq addReq) {

    //AddReq to Product 변환
    Product product = new Product();
    BeanUtils.copyProperties(addReq,product);

    //상품등록
    Long id = productSVC.save(product);

    //응답 메시지
    return ApiResponse.createApiRestMsg("00","성공",id);
  }

  //조회
  @GetMapping("products/{id}")
  public ApiResponse<Product> findById(@PathVariable("id") Long id) {

    //상품조회
    Optional<Product> foundProduct = productSVC.findByProductId(id);

    //응답 메시지
    ApiResponse<Product> response = null;
    if (foundProduct.isPresent()) {
      response = ApiResponse.createApiRestMsg("00", "성공", foundProduct.get());
    } else {
      response = ApiResponse.createApiRestMsg("01", "찾고자하는 상품이 없습니다.", null);
    }
    return response;
  }

  //수정
  @PatchMapping("/products/{id}")
  public ApiResponse<Product> edit(@PathVariable("id") Long id, @RequestBody EditReq editReq) {

    //검증
    Optional<Product> foundProduct = productSVC.findByProductId(id);
    ApiResponse<Product> response = null;
    if (foundProduct.isEmpty()) {
      return ApiResponse.createApiRestMsg("01", "수정할 상품이 없습니다.", null);
    }

    //EditReq to Product 변환
    Product product = new Product();
    BeanUtils.copyProperties(editReq,product);

    //수정
    productSVC.update(id, product);

    //응답 메시지
    return response = ApiResponse.createApiRestMsg("00", "성공", productSVC.findByProductId(id).get());


  }

  //삭제
  @DeleteMapping("/products/{id}")
  public ApiResponse<Product> del(@PathVariable("id") Long id) {

    //검증
    Optional<Product> foundProduct = productSVC.findByProductId(id);
    ApiResponse<Product> response = null;
    if (foundProduct.isEmpty()) {
      return ApiResponse.createApiRestMsg("01", "삭제할 상품이 없습니다.", null);
    }

    //삭제
    productSVC.deleteByProductId(id);

    //응답 메시지
    return ApiResponse.createApiRestMsg("00", "성공", null);
  }

  //목록
  @GetMapping("/products")
  public ApiResponse<List<Product>> findAll() {

    List<Product> list = productSVC.findAll();

    //응답 메시지
    return ApiResponse.createApiRestMsg("00", "성공", list);
  }
}
