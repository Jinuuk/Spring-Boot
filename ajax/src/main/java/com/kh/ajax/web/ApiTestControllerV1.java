package com.kh.ajax.web;

import com.kh.ajax.web.api.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/api/v1")
public class ApiTestControllerV1 {

  @ResponseBody
  @GetMapping("/t1")
  public String test1(){

    return "hello";
  }

  @ResponseBody
  @GetMapping("/t2")
  public Integer test2(){

    return 1000;
  }

  @ResponseBody
  @GetMapping("/t3")
  public Person test3(){

    return new Person("홍길동",30);
  }

  @ResponseBody
  @GetMapping("/t4")
  public List<Person> test4(){

    List<Person> list = new ArrayList<>();
    list.add(new Person("홍길동1",10));
    list.add(new Person("홍길동2",20));
    list.add(new Person("홍길동3",30));

    return list;
  }

  @ResponseBody
  @GetMapping("/t5")
  public Map<String,Person> test5(){

    Map map = new HashMap();
    map.put("1", new Person("홍길동1", 10));
    map.put("2", new Person("홍길동2", 20));
    map.put("3", new Person("홍길동3", 30));

    return map;
  }


  @ResponseBody
  @GetMapping("/t6")
  public Set<Person> test6(){

    Set<Person> set = new HashSet<>();
    set.add(new Person("홍길동1", 10));
    set.add(new Person("홍길동2", 20));
    set.add(new Person("홍길동3", 30));

    return set;
  }

  @ResponseBody
  @GetMapping("/t7")
  public Object test7(){

    Set<Person> set = new HashSet<>();
    set.add(new Person("홍길동1", 10));
    set.add(new Person("홍길동2", 20));
    set.add(new Person("홍길동3", 30));

    return new Result<>(set);
  }

  static class Result<T>{
    T data;
    Result(T o){
      this.data = o;
    }

    public T getData() {
      return data;
    }
  }
}







