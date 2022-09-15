package com.productpractice.practice2.web;

import com.productpractice.practice2.dao.Product;
import com.productpractice.practice2.svc.ProductSVC;
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
//@Controller
//@ResponseBody
//@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiProductControllerV1 {

  private final ProductSVC productSVC;

//  //등록
//  @ResponseBody
//  @PostMapping("/products")
//  public ApiResponse<List<Person>> add(@RequestBody String regMsg){
////    Set<Person> persons = new HashSet<>();
//    List<Person> persons = new ArrayList<>();
//    Person p1 = new Person("홍길동",30);
//    Person p2 = new Person("홍길순",20);
//    persons.add(p1);
//    persons.add(p2);
//
////    Map<String, Person> persons = new HashMap<>();
////    persons.put("1",p1);
////    persons.put("2",p2);
//
//    ApiResponse.Header header = new ApiResponse.Header("00","성공");
//    ApiResponse<List<Person>> response = new ApiResponse<>(header,persons);
//
//    return response;
//
//  }
//
//  @Data
//  @AllArgsConstructor
//  static class Person{
//    private String name;
//    private int age;
//  }
  //등록
//  @ResponseBody
  @PostMapping("/products")
  public ApiResponse<Long> add(@RequestBody AddReq addReq) {

    //AddReq to Product 변환
    Product product = new Product();
    BeanUtils.copyProperties(addReq,product);

    //상품등록
    Long id = productSVC.save(product);

    //응답 메시지
    ApiResponse.Header header = new ApiResponse.Header("00", "성공");
    ApiResponse<Long> response = new ApiResponse<>(header,id);

    return response;
  }
  //조회
//  @ResponseBody
  @GetMapping("products/{id}")
  public ApiResponse<Product> findById(@PathVariable("id") Long id) {

    //상품조회
    Optional<Product> foundProduct = productSVC.findByProductId(id);

    //응답 메시지
    ApiResponse<Product> response = null;
    if (foundProduct.isPresent()) {
      Product product = foundProduct.get();
      ApiResponse.Header header = new ApiResponse.Header("00", "성공");
      response = new ApiResponse<>(header, product);
    } else {
      ApiResponse.Header header = new ApiResponse.Header("01", "찾고자하는 상품이 없습니다.");
      response = new ApiResponse<>(header, null);
    }
    return response;
  }

  //수정
//  @ResponseBody
  @PatchMapping("/products/{id}")
  public ApiResponse<Product> edit(@PathVariable("id") Long id, @RequestBody EditReq editReq) {

    //검증
    Optional<Product> foundProduct = productSVC.findByProductId(id);
    ApiResponse<Product> response = null;
    if (foundProduct.isEmpty()) {
      ApiResponse.Header header = new ApiResponse.Header("01", "수정할 상품이 없습니다.");
      response = new ApiResponse<>(header, null);
      return response;
    }

    //EditReq to Product 변환
    Product product = new Product();
    BeanUtils.copyProperties(editReq,product);

    //수정
    productSVC.update(id, product);

    //응답 메시지
    ApiResponse.Header header = new ApiResponse.Header("00", "성공");
    response = new ApiResponse<>(header, productSVC.findByProductId(id).get());

    return response;

  }

  //삭제
//  @ResponseBody
  @DeleteMapping("/products/{id}")
  public ApiResponse<Product> del(@PathVariable("id") Long id) {

    //검증
    Optional<Product> foundProduct = productSVC.findByProductId(id);
    ApiResponse<Product> response = null;
    if (foundProduct.isEmpty()) {
      ApiResponse.Header header = new ApiResponse.Header("01", "삭제할 상품이 없습니다.");
      response = new ApiResponse<>(header, null);
      return response;
    }

    //삭제
    productSVC.deleteByProductId(id);

    //응답 메시지
    ApiResponse.Header header = new ApiResponse.Header("00", "성공");
    response = new ApiResponse<>(header, null);

    return response;
  }
  //목록
//  @ResponseBody
  @GetMapping("/products")
  public ApiResponse<List<Product>> findAll() {

    List<Product> list = productSVC.findAll();

    //응답 메시지
    ApiResponse.Header header = new ApiResponse.Header("00", "성공");
    ApiResponse<List<Product>> response = new ApiResponse<>(header, list);

    return response;
  }
}
