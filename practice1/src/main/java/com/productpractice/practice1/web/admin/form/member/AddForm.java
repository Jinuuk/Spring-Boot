package com.productpractice.practice1.web.admin.form.member;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AddForm {
  @NotBlank
  @Email(regexp = ".+@.+\\..+")
  private String email;     //이메일
  @NotBlank
  @Size(min=0,max=10)
  private String pw;        //비밀번호
  @Size(min=0,max=10)
  private String nickname;  //별칭
}
