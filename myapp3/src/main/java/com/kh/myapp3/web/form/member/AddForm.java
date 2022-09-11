package com.kh.myapp3.web.form.member;

import lombok.Data;

@Data
public class AddForm {
  private String email;     //이메일
  private String pw;        //비밀번호
  private String pwChk;     //비밀번호 확인
  private String nickname;  //별칭
}
