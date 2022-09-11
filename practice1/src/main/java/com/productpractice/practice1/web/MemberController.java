package com.productpractice.practice1.web;

import com.productpractice.practice1.domain.Member;
import com.productpractice.practice1.domain.svc.MemberSVC;
import com.productpractice.practice1.web.form.member.AddForm;
import com.productpractice.practice1.web.form.member.EditForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

  private final MemberSVC memberSVC;

  //가입화면
  @GetMapping("/add")
  public String addForm() {

    return "member/addForm"; //가입화면
  }

  //가입처리
  @PostMapping("/add")
  public String add(AddForm addForm) {
    //검증
    log.info("addForm={}", addForm);

    Member member = new Member();
    member.setEmail(addForm.getEmail());
    member.setPw(addForm.getPw());
    member.setNickname(addForm.getNickname());

    memberSVC.insert(member);

    return "login/loginForm"; //로그인 화면
  }

  //조회화면
  @GetMapping("/{id}")
  public String findById() {

    return "member/memberForm"; //회원 상세화면
  }

  //수정화면
  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable("id") Long id, Model model) {

    Member foundMember = memberSVC.findById(id);
    model.addAttribute("member", foundMember);
    return "member/editForm";
  }

  //수정처리
  @PostMapping("/{id}/edit")
  public String edit(@PathVariable("id") Long id, EditForm editForm) {

    Member member = new Member();
    member.setPw(editForm.getPw());
    member.setNickname(editForm.getNickname());

    int updatedRow = memberSVC.update(id, member);
    if (updatedRow == 0) return "member/editForm";
    return "redirect:/members/{id}"; //회원 상세화면 url
  }

  //탈퇴화면
  @GetMapping("/{id}/del")
  public String delForm() {

    return "member/delForm"; //회원 탈퇴 화면
  }

  //탈퇴처리
  @PostMapping("/{id}/del")
  public String del(@PathVariable("id") Long id, @RequestParam("pw") String pw) {
    int deletedRow = memberSVC.del(id, pw);
    if (deletedRow == 0) {
      return "/member/delForm";
    }
    return "redirect:/";
  }

}
