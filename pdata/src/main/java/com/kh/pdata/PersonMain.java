package com.kh.pdata;

import java.util.*;

public class PersonMain {
  public static void main(String[] args) {
    Person p1 = new Person();
    Person p2 = new Person();
    Person p3 = new Person();
    Person p4 = new Person();
    System.out.println(p1);
    System.out.println(p1.getName());
    p1.setAge(10*10);

    new Person();


    List<Person> list = new ArrayList<>();
    list.add(p1);
    list.add(p2);
    list.add(p3);
    list.add(p4);

    Set<Person> set = new HashSet<>();
    set.add(p1);
    set.add(p2);
    set.add(p3);
    set.add(p4);

    Map<String,Person> map = new HashMap<>();
    map.put("1",p1);
    map.put("2",p2);
    map.put("3",p3);
    map.put("4",p4);


  }
}
