package com.kh.ajax.domain;

import java.util.List;

public interface ItemDao {
  
  //등록
  Item save(Item item);
  
  //목록
  List<Item> findAll();

  //조회
  Item findById(Long id);

  //삭제
  Item delete(Long id);

  //수정
  Item update(Long id,Item item);

  //저장소 clear
  void clearStore();
}
