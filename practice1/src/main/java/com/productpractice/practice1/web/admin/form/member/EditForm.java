package com.productpractice.practice1.web.admin.form.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class EditForm {
  private Long memberId;    //아이디
  private String email;     //이메일
  @NotBlank
  @Size(min = 1, max = 10)
  private String pw;        //비밀번호
  @Size(min = 1, max = 10)
  private String nickname;  //별칭
}
