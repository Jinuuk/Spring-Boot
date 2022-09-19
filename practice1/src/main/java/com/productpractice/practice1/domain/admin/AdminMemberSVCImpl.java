package com.productpractice.practice1.domain.admin;

import com.productpractice.practice1.domain.dao.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminMemberSVCImpl implements AdminMemberSVC {

  private final AdminMemberDAO adminMemberDAO;

  /**
   * 가입
   *
   * @param member 가입 정보
   * @return 회원 정보
  */
  @Override
  public Member insert(Member member) {

    //회원아이디 생성
    Long generateMemberId = adminMemberDAO.generateMemberId();
    member.setMemberId(generateMemberId);
    adminMemberDAO.insert(member);
    return adminMemberDAO.findById(generateMemberId);
  }

  /**
   * 회원정보 조회
   *
   * @param memberId 회원 아이디
   * @return 회원 정보
   */
  @Override
  public Member findById(Long memberId) {
    return adminMemberDAO.findById(memberId);
  }

  /**
   * 회원정보 수정
   *
   * @param memberId 회원 아이디
   * @param member   수정할 정보
   */
  @Override
  public int update(Long memberId, Member member) {
    int cnt = adminMemberDAO.update(memberId, member);
    log.info("수정건수={}",cnt);
    return cnt;
  }

  /**
   * 탈퇴
   *
   * @param memberId 회원 아이디
   */
  @Override
  public int del(Long memberId) {
    int cnt = adminMemberDAO.del(memberId);
    log.info("삭제건수={}",cnt);
    return cnt;
  }

  /**
   * 목록
   *
   * @return 회원 전체
   */
  @Override
  public List<Member> all() {
    return adminMemberDAO.all();
  }

  /**
   * 이메일 중복 체크
   *
   * @param email 이메일
   * @return 존재하면 true
   */
  @Override
  public Boolean dupChkOfMemberEmail(String email) {
    return adminMemberDAO.dupChkOfMemberEmail(email);
  }
}
