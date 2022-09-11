package com.productpractice.practice1.domain.svc;

import com.productpractice.practice1.domain.Member;
import com.productpractice.practice1.domain.dao.MemberDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSVCImpl implements MemberSVC{

  private final MemberDAO memberDAO;

  /**
   * 가입
   *
   * @param member 가입 정보
   * @return 회원 정보
  */
  @Override
  public Member insert(Member member) {

    //회원아이디 생성
    Long generateMemberId = memberDAO.generateMemberId();
    member.setMemberId(generateMemberId);
    memberDAO.insert(member);
    return memberDAO.findById(generateMemberId);
  }

  /**
   * 회원정보 조회
   *
   * @param memberId 회원 아이디
   * @return 회원 정보
   */
  @Override
  public Member findById(Long memberId) {
    return memberDAO.findById(memberId);
  }

  /**
   * 회원정보 수정
   *
   * @param memberId 회원 아이디
   * @param member   수정할 정보
   */
  @Override
  public int update(Long memberId, Member member) {
    int cnt = memberDAO.update(memberId, member);
    log.info("수정건수={}",cnt);
    return cnt;
  }

  /**
   * 탈퇴
   *
   * @param memberId 회원 아이디
   */
  @Override
  public int del(Long memberId, String pw) {
    int cnt = memberDAO.del(memberId);
    log.info("삭제건수={}",cnt);
    return cnt;
  }
}
