package com.great.jinuk.web.dto.article;

import lombok.Data;

@Data
public class ArticleSearchForm {
  private String searchCategory;
  private String searchKeyword;
}
