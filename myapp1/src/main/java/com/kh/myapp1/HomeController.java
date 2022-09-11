package com.kh.myapp1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home() {

        return "index";
    }

    @GetMapping ("/x")
    public String home2(@RequestParam("name") String name,
                        @RequestParam("age") int age){
        log.info("name:{}", name);
        log.info("age:{}", age);

        return "x";
    }

    @GetMapping("/y")
    public String sum (
        @RequestParam("op1") int operand1,
        @RequestParam("op2") int operand2,
        Model model
    ){

        int sum = operand1+operand2;
        log.info("{}{}={}", operand1, operand2,sum);

        model.addAttribute("op1",operand1);
        model.addAttribute("op2",operand2);
        model.addAttribute("sum",sum);
        return "result";
    }
    @GetMapping("/y/{op1}/{op2}")
    public String sum2(
        @PathVariable("op1") int operand1,
        @PathVariable("op2") int operand2,
        Model model){

        int sum = operand1+operand2;
        log.info("{}{}={}", operand1, operand2,sum);

        model.addAttribute("op1",operand1);
        model.addAttribute("op2",operand2);
        model.addAttribute("sum",sum);

        return "result";

    }

    //http://localhost:9080/y/10?op2=20
    @GetMapping("/y/{op1}")
    public String sum3(
        @PathVariable("op1") int operand1,
        @RequestParam("op2") int operand2,
        Model model){

        int sum = operand1+operand2;
        log.info("{}{}={}", operand1, operand2,sum);

        model.addAttribute("op1",operand1);
        model.addAttribute("op2",operand2);
        model.addAttribute("sum",sum);

        return "result";
    }

    //덧셈 양식
    @GetMapping("/sum")
    public String formOfSum(){

        return "addForm";
    }

    //덧셈 처리
    @PostMapping("/sum")
    public String result(
        @RequestParam("op1") int op1,
        @RequestParam("op2") int op2,
        Model model
    ){

        int sum = op1 + op2;

        model.addAttribute("op1",op1);
        model.addAttribute("op2",op2);
        model.addAttribute("result", sum);

        return "addForm";
    }
}



