package com.productpractice.practice1.web.session;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginMember {
  private String email;
  private String nickname;
}
