package com.kh.product.domain.dao;

import com.kh.product.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductDAOImpl implements ProductDAO {

  private final JdbcTemplate jt;


  /**
   * 등록
   *
   * @param product 상품정보
   * @return 상품
   */
  @Override
  public int save(Product product) {
    int result = 0;
    StringBuffer sql = new StringBuffer();
    sql.append("insert into product (pid, pname, count, price) ");
    sql.append("values(?,?,?,?) ");

    result = jt.update(sql.toString(), product.getPid(), product.getPname(), product.getCount(), product.getPrice());
    return result;
  }

  /**
   * 신규 상품 아이디 생성
   *
   * @return 상품아이디
   */
  @Override
  public Long generatedProductId() {
    String sql = "select product_product_id_seq.nextval from dual ";
    Long productId = jt.queryForObject(sql, Long.class);
    return productId;
  }

  /**
   * 조회
   *
   * @param pid 상품번호
   * @return 상품
   */
  @Override
  public Product findById(Long pid) {
    StringBuffer sql = new StringBuffer();
    sql.append("select pid, pname, count, price ");
    sql.append("from product ");
    sql.append("where pid = ? ");

    Product product = null;

    try {
      product = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Product.class), pid);
    } catch (EmptyResultDataAccessException e) {
      log.info("해당 상품 번호가 없습니다.");
    }

    return product;
  }

  /**
   * 수정
   *
   * @param pid     상품번호
   * @param product 변경할 상품 내용
   */
  @Override
  public void update(Long pid, Product product) {
    StringBuffer sql = new StringBuffer();
    sql.append("update product ");
    sql.append("set pname = ?, ");
    sql.append("    count = ?, ");
    sql.append("    price = ? ");
    sql.append("where pid = ? ");

    jt.update(sql.toString(), product.getPname(), product.getCount(), product.getPrice(), pid);

  }

  /**
   * 삭제
   *
   * @param pid 상품번호
   */
  @Override
  public void delete(Long pid) {
    String sql = "delete from product where pid = ? ";
    jt.update(sql,pid);
  }

  /**
   * 상품 목록 조회
   *
   * @return 상품목록
   */
  @Override
  public List<Product> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("select pid, pname, count, price ");
    sql.append("from product ");

    List<Product> result = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Product.class));

    return result;
  }
}
