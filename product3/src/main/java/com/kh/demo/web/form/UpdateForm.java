package com.kh.demo.web.form;

import com.kh.demo.domain.common.file.UploadFile;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class UpdateForm {
  private Long productId;
  @NotBlank
  private String pname;     //  PNAME	VARCHAR2(30 BYTE)
  @NotNull
  @Positive
  @Max(9999999999L)
  private Long quantity;    //  QUANTITY	NUMBER(10,0)
  @NotNull
  @Positive
  @Max(9999999999L)
  private Long price;       //  PRICE	NUMBER(10,0)

  //파일 첨부(수정하기 위해)
  private MultipartFile file;        //상품 이미지 첨부 : 단건
  private List<MultipartFile> files; //상품 이미지 첨부 : 여러건

  //파일 참조(조회하기 위해)
  private UploadFile attachFile;
  private List<UploadFile> imageFiles;
}
