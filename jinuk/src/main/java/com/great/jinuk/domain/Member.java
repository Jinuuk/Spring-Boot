package com.great.jinuk.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
  private Long memNumber;      //  mem_number number(6)
  private String memNickname;  //  mem_nickname varchar2(18)
}
