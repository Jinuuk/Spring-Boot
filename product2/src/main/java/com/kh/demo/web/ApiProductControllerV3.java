package com.kh.demo.web;

import com.kh.demo.dao.Product;
import com.kh.demo.svc.ProductSVC;
import com.kh.demo.web.api.ApiResponse;
import com.kh.demo.web.api.product.AddReq;
import com.kh.demo.web.api.product.EditReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiProductControllerV3 {

  private final ProductSVC productSVC;

  //등록 post
  @PostMapping(value = "/products")
  public ApiResponse<Object> add(@Valid @RequestBody AddReq addReq, BindingResult bindingResult) {
    log.info("reqMsg : {}", addReq);
    //검증
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}",bindingResult);
      List<ObjectError> allErrors = bindingResult.getAllErrors();
      allErrors.stream().map(error->{
        Arrays.stream(error.getArguments()).
      });
      return ApiResponse.createApiRestMsg("99", "실패", allErrors);
    }

    //AddReq => Product 변환
    Product product = new Product();
    BeanUtils.copyProperties(addReq, product);

    //상품등록
    Long id = productSVC.save(product);

    //응답 메시지
    return ApiResponse.createApiRestMsg("00", "성공", bindingResult);
  }

  //조회 get
  @GetMapping("/products/{id}")
  public ApiResponse<Product> findById(@PathVariable("id") Long id){

    //상품조회
    Optional<Product> foundProduct = productSVC.findByProductId(id);

    //응답 메시지
    ApiResponse<Product> response = null;
    if(foundProduct.isPresent()){
      response = ApiResponse.createApiRestMsg("00", "성공", foundProduct.get());
    }else{
      response =  ApiResponse.createApiRestMsg("01", "찾고자하는 정보가 없습니다.",null);
    }
    return response;
  }

  //수정 patch
  @PatchMapping("/products/{id}")
  public ApiResponse<Product> edit(@PathVariable("id") Long id, @RequestBody EditReq editReq){

    //검증
    Optional<Product> foundProduct = productSVC.findByProductId(id);
    if (foundProduct.isEmpty()) {
      return ApiResponse.createApiRestMsg("01", "수정할 상품이 없습니다.",null);
    }

    //EditReq => Product 변환
    Product product = new Product();
    BeanUtils.copyProperties(editReq,product);

    //수정
    productSVC.update(id, product);

    //응답 메시지
    return ApiResponse.createApiRestMsg("00", "성공",productSVC.findByProductId(id).get());
  }

  //삭제 delete
  @DeleteMapping("/products/{id}")
  public ApiResponse<Product> del(@PathVariable("id") Long id) {

    //검증
    Optional<Product> foundProduct = productSVC.findByProductId(id);
    if (foundProduct.isEmpty()) {
      return ApiResponse.createApiRestMsg("01", "삭제할 상품이 없습니다.",null);
    }

    //삭제
    productSVC.deleteByProductId(id);

    //응답 메시지
    return ApiResponse.createApiRestMsg("00", "성공",null);
  }

  //목록 get

  @GetMapping("/products")
  public ApiResponse<List<Product>> findAll(){

    List<Product> list = productSVC.findAll();

    //api응답 메시지
    return ApiResponse.createApiRestMsg("00", "성공",list);
  }
}
