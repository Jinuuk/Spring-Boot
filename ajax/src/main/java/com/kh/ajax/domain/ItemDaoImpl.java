package com.kh.ajax.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemDaoImpl implements ItemDao{

  private static Map<Long, Item> store = new HashMap<>();
  private static long seq = 0L;

  private static final ItemDaoImpl itemDaoImpl = new ItemDaoImpl();

  private ItemDaoImpl(){}

  public static ItemDaoImpl getInstance() {
    return itemDaoImpl;
  }

  //등록
  @Override
  public Item save(Item item) {
    item.setId(++seq);
    store.put(item.getId(), item);
    return item;
  }

  //목록
  @Override
  public List<Item> findAll() {
    return new ArrayList<>(store.values());
  }

  //조회
  @Override
  public Item findById(Long id) {
    return store.get(id);
  }

  //삭제
  @Override
  public Item delete(Long id) {
    return store.remove(id);  //HashMap.remove : 삭제한 value를 반환, 대상키가 없으면 null반환
  }

  //수정
  @Override
  public Item update(Long id, Item item) {
    item.setId(id);
    return store.put(id, item); //HashMap.put : key에대한 value를 반환 없으면 null
  }

  //저장소 clear 전체삭제
  @Override
  public void clearStore() {
    store.clear();
  }
}
