package com.kh.product;

import com.kh.product.domain.Product;
import com.kh.product.domain.svc.ProductSVC;
import com.kh.product.web.form.AddForm;
import com.kh.product.web.form.EditForm;
import com.kh.product.web.form.ProductForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductSVC productSVC;

  //등록양식
  @GetMapping("/add")
  public String addForm(Model model) {
    model.addAttribute("addForm",new AddForm());
    return "addForm"; //상품등록 화면
  }

  //등록처리
  @PostMapping("/add")
  public String add(@Valid @ModelAttribute AddForm addForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {


    if(bindingResult.hasErrors()){
      return "addForm";
    }

    Product product = new Product();
    BeanUtils.copyProperties(addForm, product);
    if((product.getPrice()*product.getCount()) >= 10000000) {
      bindingResult.reject("totalErr","총액은 1000만원을 초과할 수 없습니다.");
      return "addForm";
    }
    Product savedProduct = productSVC.save(product);
    Long pid = savedProduct.getPid();
    redirectAttributes.addAttribute("pid",pid);

    return "redirect:/products/{pid}"; //상품조회 url 재요청

  }

  //상품조회
  @GetMapping("/{pid}")
  public String findById(@PathVariable("pid") Long pid, Model model) {

    Product foundProduct = productSVC.findById(pid);
    ProductForm productForm = new ProductForm();
    productForm.setPid(pid);
    BeanUtils.copyProperties(foundProduct,productForm);
    model.addAttribute("productForm",productForm);

    return "productForm"; //상품 상세화면
  }

  //수정양식
  @GetMapping("/{pid}/edit")
  public String editForm(@PathVariable("pid") Long pid, Model model) {

    Product foundProduct = productSVC.findById(pid);
    EditForm editForm = new EditForm();
    BeanUtils.copyProperties(foundProduct,editForm);
    model.addAttribute("editForm", editForm);
    
    return "editForm"; //상품 수정화면
  }

  //수정처리
  @PostMapping("/{pid}/edit")
  public String update(@PathVariable("pid") Long pid, @Valid @ModelAttribute EditForm editForm,BindingResult bindingResult, RedirectAttributes redirectAttributes) {

    if(bindingResult.hasErrors()){
      return "editForm"; //상품 수정화면
    }

    Product product = new Product();
    product.setPid(pid);
    BeanUtils.copyProperties(editForm,product);
    if(product.getPrice()*product.getCount() >= 10000000) {
      bindingResult.reject("totalErr","총액은 1000만원을 초과할 수 없습니다.");
      return "editForm";
    }
    productSVC.update(pid, product);
    redirectAttributes.addAttribute("pid",pid);

    return "redirect:/products/{pid}";
  }

  //삭제처리
  @GetMapping("/{pid}/del")
  public String delete(@PathVariable("pid") Long pid) {

    productSVC.delete(pid);
    return "redirect:/products";
  }

  //목록화면
  @GetMapping
  public String list(Model model) {

    List<Product> products = productSVC.findAll();
    List<ProductForm> list = new ArrayList<>();

    for(Product product : products) {
      ProductForm productForm = new ProductForm();
      BeanUtils.copyProperties(product,productForm);
      list.add(productForm);
    }

    model.addAttribute("list", list);

    return "all"; //전체화면 view
  }
}
