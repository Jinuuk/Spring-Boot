package com.kh.myapp3.web.admin;

import com.kh.myapp3.domain.Member;
import com.kh.myapp3.domain.admin.AdminMemberSVC;
import com.kh.myapp3.web.admin.form.member.AddForm;
import com.kh.myapp3.web.admin.form.member.EditForm;
import com.kh.myapp3.web.admin.form.member.MemberForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/members")
@RequiredArgsConstructor
public class AdminMemberController {

  private final AdminMemberSVC adminMemberSVC;

  //등록화면	GET	/members/add
  @GetMapping("/add")
  public String addForm(Model model) { //
    model.addAttribute("form", new AddForm());
    return "admin/member/addForm"; //등록 화면
  }

  //등록처리	POST	/members/add
  @PostMapping("/add")
  public String add(@Valid @ModelAttribute("form") AddForm addForm,
                    BindingResult bindingResult,
                    RedirectAttributes redirectAttributes) { //리다이렉트할 때 정보를 유지하기위해 사용 (굳이 왜?)
    //model.addAttribute("addForm", addForm); -> model.addAttribute("form", addForm);
    log.info("addForm={}", addForm);

    //검증
    if(bindingResult.hasErrors()){
      log.info("errors={}", bindingResult);
      return "admin/member/addForm";
    }
    //회원 아이디 중복 체크
    Boolean isExist = adminMemberSVC.dupChkOfMemberEmail(addForm.getEmail());
    if(isExist) {
      bindingResult.rejectValue("email", "dup.email", "동일한 이메일이 존재합니다.");

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
    return "redirect:/admin/members/{id}"; // 회원 상세
  }

  //조회화면	GET	/members/{id}
  @GetMapping("/{id}")
  public String findById(@PathVariable("id") Long id, Model model){

    Member foundMember = adminMemberSVC.findById(id);
    MemberForm memberForm = new MemberForm();

    BeanUtils.copyProperties(foundMember,memberForm);

//    memberForm.setMemberId(foundMember.getMemberId());
//    memberForm.setEmail(foundMember.getEmail());
//    memberForm.setPw(foundMember.getPw());
//    memberForm.setNickname(foundMember.getNickname());
//    memberForm.setCdate(foundMember.getCdate());
//    memberForm.setUdate(foundMember.getUdate());

    model.addAttribute("form",memberForm);

    return "admin/member/memberForm"; //회원 상세화면
  }

  //수정화면	GET	/members/{id}/edit
  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable("id") Long id, Model model) {

    Member foundMember = adminMemberSVC.findById(id);

    EditForm editForm = new EditForm(); //왜 굳이 editForm을 만드는지?
    editForm.setMemberId(foundMember.getMemberId());
    editForm.setEmail(foundMember.getEmail());
    editForm.setPw(foundMember.getPw());
    editForm.setNickname(foundMember.getNickname());

    model.addAttribute("form", editForm);
    return "admin/member/editForm"; //회원 수정화면
  }

  //수정처리	POST	/members/{id}/edit
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
      return "admin/member/editForm";
    }
    return "redirect:/admin/members/{id}"; //회원 상세회면 url
  }

  //삭제처리
  @GetMapping("/{id}/del")
  public String del(@PathVariable("id") Long id) {
    int deletedRow = adminMemberSVC.del(id);
    if (deletedRow == 0) {
      return "redirect:/admin/members/"+id;
    }
    return "redirect:/admin/members/all"; //회원 목록
  }

  //목록화면	GET	/members
  @GetMapping("/all")
  public String all(Model model) {

    List<Member> members = adminMemberSVC.all();
    List<MemberForm> list = new ArrayList<>();
    //case1) 향상된 for문
//    for (Member member : members) {
//      MemberForm memberForm = new MemberForm();
//      BeanUtils.copyProperties(member, memberForm);
//      list.add(memberForm);
//    }

    //case2) 고차함수 적용 => 람다 표현식
    members.stream().forEach(member->{
      MemberForm memberForm = new MemberForm();
      BeanUtils.copyProperties(member, memberForm);
      list.add(memberForm);
    });

    model.addAttribute("list",list);
    return "admin/member/all";
  }
}
