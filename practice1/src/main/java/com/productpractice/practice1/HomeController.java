package com.productpractice.practice1;


import com.productpractice.practice1.domain.dao.Member;
import com.productpractice.practice1.domain.svc.MemberSVC;
import com.productpractice.practice1.web.form.LoginForm;
import com.productpractice.practice1.web.session.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

  final MemberSVC memberSVC;

  @GetMapping
  public String home(){
    //세션이 존재하면 로그인 후 화면으로 이동
    //세션이 없으면 로그인 전 화면으로 이동
    return "beforeLogin";
  }

  //로그인 화면
  @GetMapping("/login")
  public String loginForm(@ModelAttribute("form") LoginForm loginForm){

    return "login";
  }

  //로그인 처리
  @PostMapping("/login")
  public String login(@Valid @ModelAttribute("form") LoginForm loginForm,
                      BindingResult bindingResult,
                      HttpServletRequest request) {

    //기본 검증
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      return "login";
    }

    //회원 유무 검증
    Optional<Member> member = memberSVC.login(loginForm.getEmail(), loginForm.getPw());
    if (member.isEmpty()) {
      bindingResult.reject("LoginForm.login", "회원 정보가 없습니다.");
      return "login";
    }
    
    //회원인 경우
    Member foundMember = member.get();
    
    //request.getSession(true) : 세션에 회원 정보 저장
    LoginMember loginMember = new LoginMember(foundMember.getEmail(), foundMember.getPw());
    HttpSession session = request.getSession(true);
    session.setAttribute("LoginMember",loginMember);

    return "afterLogin";
  }
  
  //로그아웃
  @GetMapping("/logout")
  public String logout(HttpServletRequest request) {
    //request.getSession(false) : 세션 정보가 있으면 가져오고 없으면 세션을 만들지 않음
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return "redirect:/"; //초기화면으로 이동
  }
}
