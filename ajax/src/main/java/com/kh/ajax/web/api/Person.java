package com.kh.ajax.web.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
  private String name;
  private Integer age;

  public void setAge(Integer age) {
    if(age < 0 ) {
      age = 0;
      return;
    }
    this.age = age;
  }
}
