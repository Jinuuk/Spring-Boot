package com.kh.myapp3.domain.dao;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class Member {
  private Long memberId;         //회원 아이디(내부관리용)
  private String email;          //이메일
  private String pw;             //비밀번호
  private String nickname;       //별칭

  private LocalDateTime cdate;   //생성일
  private LocalDateTime udate;   //수정일

  public Member(String email, String pw, String nickname) {
    this.email = email;
    this.pw = pw;
    this.nickname = nickname;
  }

}
