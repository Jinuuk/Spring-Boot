package com.kh.myapp3.domain.admin;

import com.kh.myapp3.domain.dao.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class AdminMemberSVCImplTest {

  @Autowired
  private AdminMemberSVC adminMemberSVC;

  @Test
  @DisplayName("회원가입")
  void insert() {
    Member member = new Member();
    member.setEmail("test5@test.com");
    member.setNickname("홍길서");
    member.setPw("1234");

    Member joinedMember = adminMemberSVC.insert(member);

    log.info("joinedMember = {}",joinedMember);
  }

  @Test
  void findById() {
  }

  @Test
  void update() {
  }

  @Test
  void del() {
  }

  @Test
  void all() {
  }

  @Test
  void dupChkOfMemberEmail() {
  }
}