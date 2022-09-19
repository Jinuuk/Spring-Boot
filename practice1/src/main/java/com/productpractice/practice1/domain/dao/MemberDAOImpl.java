package com.productpractice.practice1.domain.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberDAOImpl implements MemberDAO {

  private final JdbcTemplate jt;

  /**
   * 가입
   *
   * @param member 가입 정보
   * @return 가입건수
   */
  @Override
  public int insert(Member member) {
    int result = 0;
    StringBuffer sql = new StringBuffer();
    sql.append("insert into member (member_id , email, pw, nickname) ");
    sql.append("values(?, ?, ?, ?) ");

    jt.update(sql.toString(), member.getMemberId(), member.getEmail(), member.getPw(), member.getNickname());

    return result;
  }

  /**
   * 신규 회원 아이디 (내부관리용) 생성
   *
   * @return 회원 아이디
   */
  @Override
  public Long generateMemberId() {
    String sql = "select member_member_id_seq.nextval from dual";
    Long memberId = jt.queryForObject(sql, Long.class);
    return memberId;
  }

  /**
   * 회원정보 조회
   *
   * @param memberId 회원 아이디
   * @return 회원 정보
   */
  @Override
  public Member findById(Long memberId) {
    StringBuffer sql = new StringBuffer();

    sql.append("select member_id,email,pw,nickname, cdate, udate ");
    sql.append("from member ");
    sql.append("where member_id= ? ");

    Member foundMember = null;

    try {
      //BeanPropertyRowMapper는 매핑되는 자바 클래스에 디폴트 생성자 필수
      foundMember = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Member.class), memberId);
    } catch (DataAccessException e) {
      log.info("찾고자하는 회원이 없습니다. {}", memberId);
    }

    return foundMember;
  }

  /**
   * 회원정보 수정
   *
   * @param memberId 회원 아이디
   * @param member   수정할 정보
   * @return 수정건수
   */
  @Override
  public int update(Long memberId, Member member) {
    int result = 0;
    StringBuffer sql = new StringBuffer();
    sql.append("update member ");
    sql.append("set nickname = ?, ");
    sql.append("    udate = systimestamp ");
    sql.append("where member_id = ? ");
    sql.append(" and pw = ? ");

    result = jt.update(sql.toString(), member.getNickname(), memberId, member.getPw());
    return result;
  }

  /**
   * 탈퇴
   *
   * @param memberId 회원 아이디
   * @return 삭제건수
   */
  @Override
  public int del(Long memberId) {
    int result = 0;
    String sql = "delete from member where member_id = ? ";

    result = jt.update(sql, memberId);
    return result;
  }

  /**
   * 로그인
   *
   * @param email 이메일
   * @param pw    비밀번호
   * @return 회원
   */
  @Override
  public Optional<Member> login(String email, String pw) {
    StringBuffer sql = new StringBuffer();
    sql.append("select * ");
    sql.append(" from member ");
    sql.append(" where email = ? ");
    sql.append(" and pw = ? ");

    try {
      Member member = jt.queryForObject(
                      sql.toString(),
                      new BeanPropertyRowMapper<>(Member.class),
                      email, pw
      );
      return Optional.of(member);
    } catch (DataAccessException e) {
      return Optional.empty();
    }
  }
}
