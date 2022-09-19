package com.kh.myapp3.web.session;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginMember {
  private String email;     //이메일
  private String nickname;  //별칭
}
