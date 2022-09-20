package com.productpractice.practice2.web;

import com.productpractice.practice2.domain.product.Product;
import com.productpractice.practice2.domain.product.ProductSVC;
import com.productpractice.practice2.web.api.ApiResponse;
import com.productpractice.practice2.web.api.product.AddReq;
import com.productpractice.practice2.web.api.product.EditReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiProductControllerV3 {

  private final ProductSVC productSVC;

  //등록
  @PostMapping("/products")
  public ApiResponse<Object> add(@Valid @RequestBody AddReq addReq, BindingResult bindingResult) {
    log.info("reqMsg : {}", addReq);

    //검증
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      return ApiResponse.createApiRestMsg("99", "실패", getErrMsg(bindingResult));
    }

    //비즈니스 규칙
    //필드 검증
    if (addReq.getQuantity() > 100) {
      bindingResult.rejectValue("quantity", null, "상품 수량은 100개를 초과할 수 없습니다.");
    }
    //오브젝트 검증
    if (addReq.getQuantity() * addReq.getPrice() > 10_000_000) {
      bindingResult.reject(null, "총액은 1000만원을 초과할 수 없습니다.");
    }
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      return ApiResponse.createApiRestMsg("99", "실패", getErrMsg(bindingResult));
    }

    //AddReq to Product 변환
    Product product = new Product();
    BeanUtils.copyProperties(addReq, product);

    //상품등록
    Long id = productSVC.save(product);

    //응답 메시지
    return ApiResponse.createApiRestMsg("00", "성공", id);
  }

  //검증 오류 메시지
  private Map<String, String> getErrMsg(BindingResult bindingResult) {
    Map<String, String> errmsg = new HashMap<>();

    bindingResult.getAllErrors().stream().forEach(objectError ->{
      errmsg.put(objectError.getCodes()[0], objectError.getDefaultMessage());
    });

    return errmsg;
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
  public ApiResponse<Object> edit(@PathVariable("id") Long id,
                                  @Valid @RequestBody EditReq editReq,
                                  BindingResult bindingResult) {

    //검증
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      return ApiResponse.createApiRestMsg("99", "실패", getErrMsg(bindingResult));
    }

    //비즈니스 규칙
    //필드 검증
    if (editReq.getQuantity() > 100) {
      bindingResult.rejectValue("quantity", null, "상품 수량은 100개를 초과할 수 없습니다.");
    }
    //오브젝트 검증
    if (editReq.getQuantity() * editReq.getPrice() > 10_000_000) {
      bindingResult.reject(null, "총액은 1000만원을 초과할 수 없습니다.");
    }
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      return ApiResponse.createApiRestMsg("99", "실패", getErrMsg(bindingResult));
    }

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
    return ApiResponse.createApiRestMsg("00", "성공", productSVC.findByProductId(id).get());
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
