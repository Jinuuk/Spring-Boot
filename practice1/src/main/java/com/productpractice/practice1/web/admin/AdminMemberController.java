package com.productpractice.practice1.web.admin;

import com.productpractice.practice1.domain.dao.Member;
import com.productpractice.practice1.domain.admin.AdminMemberSVC;
import com.productpractice.practice1.web.admin.form.member.AddForm;
import com.productpractice.practice1.web.admin.form.member.EditForm;
import com.productpractice.practice1.web.admin.form.member.MemberForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/members")
@RequiredArgsConstructor
public class AdminMemberController {

  private final AdminMemberSVC adminMemberSVC;

  //등록화면
  @GetMapping("/add")
  public String addForm(Model model) {
    model.addAttribute("form", new AddForm());
    return "admin/member/addForm"; //등록 화면
  }

  //등록처리
  @PostMapping("/add")
  public String add(@Valid @ModelAttribute("form") AddForm addForm,
                    BindingResult bindingResult,
                    RedirectAttributes redirectAttributes) {
    //검증
    if(bindingResult.hasErrors()){
      log.info("errors={}", bindingResult);
      return "admin/member/addForm";
    }

    //objectError 2개 이상의 필드 분석을 통해 오류 검정
    //비밀번호, 별칭의 자리수 모두가 5미만인 경우
//    if(addForm.getPw().trim().length() < 5 && addForm.getNickname().trim().length() < 5){
//      bindingResult.reject("memberChk", new String[]{"5"}, "비밀번호, 별칭의 지리수 모두 5미민입니다.");
//      log.info("errors={}", bindingResult);
//      return "admin/member/addForm";
//    }

    //회원 아이디 중복 체크
    Boolean isExist = adminMemberSVC.dupChkOfMemberEmail(addForm.getEmail());
    if(isExist) {
      bindingResult.rejectValue("email", "dup.email", "동일한 이메일이 존재합니다.");
      log.info("errors={}", bindingResult);
      return "admin/member/addForm";
    }

    //회원등록
    Member member = new Member();
    member.setEmail(addForm.getEmail());
    member.setPw(addForm.getPw());
    member.setNickname(addForm.getNickname());
    Member insertedMember = adminMemberSVC.insert(member);

    Long id = insertedMember.getMemberId();
    redirectAttributes.addAttribute("id",id);
    return "redirect:/admin/members/{id}"; //회원상세
  }

  //조회화면
  @GetMapping("/{id}")
  public String findById(@PathVariable("id") Long id, Model model) {

    Member foundMember = adminMemberSVC.findById(id);

    MemberForm memberForm = new MemberForm();
    memberForm.setMemberId(foundMember.getMemberId());
    memberForm.setEmail(foundMember.getEmail());
    memberForm.setPw(foundMember.getPw());
    memberForm.setNickname(foundMember.getNickname());
    memberForm.setCdate(foundMember.getCdate());
    memberForm.setUdate(foundMember.getUdate());

    model.addAttribute("memberForm", memberForm);

    return "admin/member/memberForm"; //회원 상세화면
  }

  //수정화면
  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable("id") Long id, Model model) {

    Member foundMember = adminMemberSVC.findById(id);

    EditForm editForm = new EditForm();
    editForm.setMemberId(foundMember.getMemberId());
    editForm.setEmail(foundMember.getEmail());
    editForm.setPw(foundMember.getPw());
    editForm.setNickname(foundMember.getNickname());

    model.addAttribute("form", editForm);
    return "admin/member/editForm";
  }

  //수정처리
  @PostMapping("/{id}/edit")
  public String edit(@PathVariable("id") Long id,
                     @Valid @ModelAttribute("form") EditForm editForm,
                     BindingResult bindingResult
  ) {

    //검증
    if(bindingResult.hasErrors()){
      log.info("errors={}", bindingResult);
      return "admin/member/editForm";
    }

    Member member = new Member();
    member.setPw(editForm.getPw());
    member.setNickname(editForm.getNickname());

    int updatedRow = adminMemberSVC.update(id, member);
    if (updatedRow == 0) {
      return "member/editForm";
    }
    return "redirect:/admin/members/{id}"; //회원 상세화면 url
  }

  //삭제처리
  @GetMapping("/{id}/del")
  public String del(@PathVariable("id") Long id) {
    int deletedRow = adminMemberSVC.del(id);
    if (deletedRow == 0) {
      return "redirect:/admin/members/"+id;
    }
    return "redirect:/admin/members/all"; //회원목록
  }

  //목록화면
  @GetMapping("/all")
  public String all(Model model) {

    List<Member> list = adminMemberSVC.all();
    model.addAttribute("list",list);
    return "admin/member/all";
  }
}
