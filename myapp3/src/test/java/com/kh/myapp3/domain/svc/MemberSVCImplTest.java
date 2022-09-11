package com.kh.myapp3.domain.svc;

import com.kh.myapp3.domain.Member;
import com.kh.myapp3.domain.admin.AdminMemberSVC;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberSVCImplTest {

  @Autowired
  private MemberSVC memberSVC;
  @Autowired
  private AdminMemberSVC adminMemberSVC;
  private static Member member;

  @Test
  @DisplayName("가입")
  @Order(1)
  void insert() {
    Member newMember = new Member("test456@test.com","1234","별칭456");

    member = memberSVC.insert(newMember);
    log.info("insertedMember={}",member);
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
    String nickname = "별칭9999";

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
  @DisplayName("탈퇴")
  @Order(5)
  void del() {
    memberSVC.del(member.getMemberId(), member.getPw());
    Member foundMember = memberSVC.findById(member.getMemberId());
    Assertions.assertThat(foundMember).isNull();
  }

  @Test
  @DisplayName("목록")
  @Order(4)
  void all() {
    List<Member> list = adminMemberSVC.all();
    Member foundMember = adminMemberSVC.findById(member.getMemberId());
    Assertions.assertThat(list).containsAnyOf(foundMember);
  }
}