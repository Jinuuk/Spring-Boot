package com.productpractice.practice1.domain.svc;

import com.productpractice.practice1.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class MemberSVCImplTest {

  @Autowired
  private MemberSVC memberSVC;

  private static Member member;

  @Test
  @DisplayName("가입")
  @Order(1)
  void insert() {
    Member newMember = new Member("test777@test.com", "1234", "별칭");

    member = memberSVC.insert(newMember);
    log.info("insertedMember={}", member);
    Assertions.assertThat(member.getEmail()).isEqualTo(newMember.getEmail());
    Assertions.assertThat(member.getPw()).isEqualTo(newMember.getPw());
    Assertions.assertThat(member.getNickname()).isEqualTo(newMember.getNickname());
  }

  @Test
  @DisplayName("조회")
  @Order(2)
  void findById() {
    Member foundMember = memberSVC.findById(member.getMemberId());
    Assertions.assertThat(foundMember).isEqualTo(member);
  }


  @Test
  @DisplayName("수정")
  @Order(3)
  void update() {
    String pw = "9999";
    String nickname = "수정된 별칭";

    //수정 정보
    Member m = new Member();
    m.setPw(pw);
    m.setNickname(nickname);

    //수정
    memberSVC.update(member.getMemberId(),m);

    //조회
    Member foundMember = memberSVC.findById(member.getMemberId());

    //비교
    Assertions.assertThat(foundMember.getPw()).isEqualTo(pw);
    Assertions.assertThat(foundMember.getNickname()).isEqualTo(nickname);
  }

  @Test
  @DisplayName("삭제")
  @Order(5)
  void del() {
    memberSVC.del(member.getMemberId(), member.getPw());
    Member foundMember = memberSVC.findById(member.getMemberId());
    Assertions.assertThat(foundMember).isNull();
  }

}