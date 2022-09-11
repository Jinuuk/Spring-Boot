package com.productpractice.practice1.domain.admin;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class AdminMemberDAOImplTest {

  @Autowired
  private AdminMemberDAO adminMemberDAO;


  @Test
  @DisplayName("이메일 중복 체크")
  void dupChkOfMemberEmail() {
    Boolean isExist = adminMemberDAO.dupChkOfMemberEmail("tes2t1@test.com");
    Assertions.assertThat(isExist).isFalse();
  }
}