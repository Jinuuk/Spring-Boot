package com.kh.demo.dao;

public class Product {
  private Long productId; //  product_id  number(10)
  private String pname;   //  pname       varchar(30)
  private Long quantity;  //  quantity    number(10)
  private Long price;     //  price       number(10)

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getPname() {
    return pname;
  }

  public void setPname(String pname) {
    this.pname = pname;
  }

  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

  public Long getPrice() {
    return price;
  }

  public void setPrice(Long price) {
    this.price = price;
  }
}


