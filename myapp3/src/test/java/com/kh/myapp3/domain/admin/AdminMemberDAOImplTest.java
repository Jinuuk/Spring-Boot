package com.kh.myapp3.domain.admin;

import com.kh.myapp3.domain.dao.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
//@Transactional //테스트 환경에서는 메소드 종료 전에 데이터를 롤백한다.
class AdminMemberDAOImplTest {

  @Autowired
  private AdminMemberDAO adminMemberDAO;

  @Test
  @DisplayName("회원가입")
  @Disabled
    //테스트 대상에서 제외
  void insert() {
    Member member = new Member();
    member.setMemberId(999L);
    member.setNickname("홍길동3");
    member.setPw("1234");
    member.setEmail("test999@test.com");
    int affectedRow = adminMemberDAO.insert(member);

    Assertions.assertThat(affectedRow).isEqualTo(1);
  }

//  @Test
//  @DisplayName("회원 아이디 중복")
//  void insertThrow() {
//    Member member = new Member();
//    member.setMemberId(999L);
//    member.setNickname("홍길동3");
//    member.setPw("1234");
//    member.setEmail("test999@test.com");
//
//    org.junit.jupiter.api.Assertions.assertThrows(
//        DuplicateFormatFlagsException.class,
//        ()->adminMemberDAO.insert(member)
//    );
//  }

  @Test
  @DisplayName("회원조회 - 회원 존재하는 경우")
  void findById() {
    Member foundMember = adminMemberDAO.findById(777L);
    Assertions.assertThat(foundMember.getNickname()).isEqualTo("홍길동");
    Assertions.assertThat(foundMember.getEmail()).isEqualTo("test777@test.com");
  }

  @Test
  @DisplayName("회원조회 - 회원 존재하지 않는 경우")
  void findById2() {
    Member foundMember = adminMemberDAO.findById(7787L);
    Assertions.assertThat(foundMember).isNull();
  }


  @Test
  @DisplayName("회원수정")
  void update() {

    Member member = new Member();
    member.setMemberId(777L);
    member.setNickname("홍길동(수정)");
    member.setPw("1234");

    int affectedRow = adminMemberDAO.update(member.getMemberId(), member);
    Member foundMember = adminMemberDAO.findById(member.getMemberId());

    Assertions.assertThat(foundMember.getNickname()).isEqualTo("홍길동(수정)");
    Assertions.assertThat(foundMember.getPw()).isEqualTo("1234");

  }

  //
//  @Test
//  void del() {
//  }
//
  @Test
  @DisplayName("회원목록")
  void all() {
    List<Member> all = adminMemberDAO.all();

//    for (Member member :
//        all) {
//      log.info(member.toString());
//    }

    all.stream().forEach(member -> log.info(member.toString()));

    Assertions.assertThat(all.size()).isEqualTo(3);
  }
//
//  @Test
//  void dupChkOfMemberEmail() {
//  }
}