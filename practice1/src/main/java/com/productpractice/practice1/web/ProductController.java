package com.productpractice.practice1.web;

import com.productpractice.practice1.domain.Product;
import com.productpractice.practice1.domain.svc.ProductSVC;
import com.productpractice.practice1.web.form.product.EditForm;
import com.productpractice.practice1.web.form.product.ItemForm;
import com.productpractice.practice1.web.form.product.SaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductSVC productSVC;


  //등록양식
  @GetMapping("/add")
  public String saveForm() {

    return "product/addForm"; //상품등록 view
  }

  //등록처리
  @PostMapping("/add")
  public String saver(SaveForm saveForm) {
    log.info("saveForm : {}", saveForm);

    Product product = new Product();
    product.setPname(saveForm.getPname());
    product.setQuantity(saveForm.getQuantity());
    product.setPrice(saveForm.getPrice());

    Product savedProduct = productSVC.save(product);
    log.info("프로덕트 아이디 : {}",savedProduct.getProductId());

    return "redirect:/products/"+savedProduct.getProductId(); //상품상세 요청 url

  }

  //상품개별조회
  @GetMapping("/{pid}")
  public String findByProductId(@PathVariable("pid") Long pid, Model model){

    //db에서 상품조회
    Product foundProduct = productSVC.findById(pid);

    //foundProduct => ItemForm 복사
    ItemForm itemForm = new ItemForm();
    itemForm.setProductId(foundProduct.getProductId());
    itemForm.setPname(foundProduct.getPname());
    itemForm.setQuantity(foundProduct.getQuantity());
    itemForm.setPrice(foundProduct.getPrice());

    //view에서 참조하기위해 model객체에 저장
    model.addAttribute("itemForm",itemForm);

    return "/product/itemForm"; //상품상세 view
  }

  //수정양식
  @GetMapping("/{pid}/edit")
  public String updateForm(@PathVariable("pid") Long pid, Model model) {

    Product foundProduct = productSVC.findById(pid);

    //Product => EditForm 변환
    EditForm editForm = new EditForm();
    editForm.setProductId(foundProduct.getProductId());
    editForm.setPname(foundProduct.getPname());
    editForm.setQuantity(foundProduct.getQuantity());
    editForm.setPrice(foundProduct.getPrice());

    model.addAttribute("editForm", editForm);

    return "product/editForm"; //상품수정 view
  }

  //수정치리
  @PostMapping("/{pid}/edit")
  public String update(@PathVariable("pid") Long pid, EditForm editForm){
    Product product = new Product();

    product.setProductId(pid);
    product.setPname(editForm.getPname());
    product.setQuantity(editForm.getQuantity());
    product.setPrice(editForm.getPrice());

    productSVC.update(pid, product);
    return "redirect:/products/"+pid; //상품상세 url
  }

  //삭제처리
  @GetMapping("/{pid}/del")
  public String delete(@PathVariable("pid") Long pid) {

    productSVC.delete(pid);
    return "redirect:/products"; //전체목록 url
  }

  //목록화면
  @GetMapping
  public String list(Model model) {

    List<Product> list = productSVC.findAll();
    model.addAttribute("list", list);

    return "product/all"; //전체목록 view
  }

}
