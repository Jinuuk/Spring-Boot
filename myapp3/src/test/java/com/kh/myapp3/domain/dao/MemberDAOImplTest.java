package com.kh.myapp3.domain.dao;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Slf4j
@SpringBootTest
public class MemberDAOImplTest {

  @Autowired
  MemberDAO memberDAO;

  @Test
  @DisplayName("회원 존재 유무 테스트 : 있는지")
  void isExistMember(){
    String email = "test1@test.com";
    String pw = "1234";
    Optional<Member> login = memberDAO.login(email, pw);
//    long count = login.stream().filter(member -> member.getEmail().equals("test1@test.com")).count();
//    Assertions.assertThat(count).isEqualTo(1);
    Assertions.assertThat(login.isPresent()).isTrue();
  }

  @Test
  @DisplayName("회원 존재 유무 테스트 : 없는지")
  void isNotExistMember(){
    String email = "test1@test.com";
    String pw = "9999";
    Optional<Member> login = memberDAO.login(email, pw);
//    long count = login.stream().filter(member -> member.getEmail().equals("test1@test.com")).count();
//    Assertions.assertThat(count).isEqualTo(1);
    Assertions.assertThat(login.isEmpty()).isTrue();
  }
}
