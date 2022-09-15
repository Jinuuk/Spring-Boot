package com.productpractice.practice2.web.form;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class SaveForm {
  @NotBlank
  private String pname;     //  PNAME	VARCHAR2(30 BYTE)
  @NotNull
  @Positive
  @Max(9999999999L)
  private Long quantity;    //  QUANTITY	NUMBER(10,0)
  @NotNull
  @Positive
  @Max(9999999999L)
  private Long price;       //  PRICE	NUMBER(10,0)
}

