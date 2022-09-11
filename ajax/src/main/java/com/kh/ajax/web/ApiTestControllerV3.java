package com.kh.ajax.web;

import com.kh.ajax.web.api.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/api/v3")
public class ApiTestControllerV3 {

  //@ResponseBody
  @GetMapping("/t1")
  public ResponseEntity<String> test1(){
    ResponseEntity<String> responseEntity = new ResponseEntity<>("hello", HttpStatus.OK);
    return responseEntity;
  }

  //@ResponseBody
  @GetMapping("/t2")
  public ResponseEntity<String> test2(){

    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type","text/html");

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                         .headers(headers)
                         .body("<h3>hello~</h3>");
  }

  //@ResponseBody
  @GetMapping("/t3")
  public ResponseEntity<Person> test3(){

    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type","application/json");

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                         .headers(headers)
                         .body(new Person("홍길동",30));
  }

  //@ResponseBody
  @GetMapping("/t4")
  public List<Person> test4(){

    List<Person> list = new ArrayList<>();
    list.add(new Person("홍길동1",10));
    list.add(new Person("홍길동2",20));
    list.add(new Person("홍길동3",30));

    return list;
  }

  //@ResponseBody
  @GetMapping("/t5")
  public Map<String,Person> test5(){

    Map map = new HashMap();
    map.put("1", new Person("홍길동1", 10));
    map.put("2", new Person("홍길동2", 20));
    map.put("3", new Person("홍길동3", 30));

    return map;
  }

  //@ResponseBody
  @GetMapping("/t6")
  public Object test6(){

    Set<Person> set = new HashSet<>();
    set.add(new Person("홍길동1", 10));
    set.add(new Person("홍길동2", 20));
    set.add(new Person("홍길동3", 30));

    return new Result<>(set);
  }

  class Result<T>{
    T data;
    Result(T o){
      this.data = o;
    }

    public T getData() {
      return data;
    }
  }
}







