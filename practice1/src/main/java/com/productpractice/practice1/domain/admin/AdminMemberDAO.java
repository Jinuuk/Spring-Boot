package com.productpractice.practice1.domain.admin;

import com.productpractice.practice1.domain.Member;

import java.util.List;

public interface AdminMemberDAO {

  /**
   * 가입
   * @param member 가입 정보
   * @return 가입건수
   */
  int insert(Member member);

  /**
   * 회원정보 조회
   * @param memberId 회원 아이디
   * @return 회원 정보
   */
  Member findById(Long memberId);

  /**
   * 회원정보 수정
   * @param memberId 회원 아이디
   * @param member 수정할 정보
   * @return 수정건수
   */
  int update(Long memberId, Member member);


  /**
   * 탈퇴
   * @param memberId 회원 아이디
   * @return 삭제건수
   */
  int del(Long memberId);

  /**
   * 목록
   * @return 회원 전체
   */
  List<Member> all();

  /**
   * 신규 회원 아이디 (내부관리용) 생성
   * @return 회원 아이디
   */
  Long generateMemberId();

  /**
   * 이메일 중복 체크
   * @param email 이메일
   * @return 존재하면 true
   */
  Boolean dupChkOfMemberEmail(String email);
}
