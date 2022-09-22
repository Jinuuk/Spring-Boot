package com.productpractice.practice2.web;

import com.productpractice.practice2.domain.common.file.UploadFile;
import com.productpractice.practice2.domain.common.file.UploadFileDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/attach")
public class AttachFileController {

  @Value("${attach.root_dir}")
  private String attachRoot; //첨부파일 루트
  private final UploadFileDAO uploadFileDAO;


  //이미지
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  @GetMapping("/img/{attachCode}/{storeFileName}")
  public Resource img(@PathVariable String attachCode,
                      @PathVariable String storeFileName

  ) throws MalformedURLException {
    // http://서버:포트/경로...
    // file:///d:/tmp/P0101/xxx-xxx-xxx-xxx.png
    Resource resource = new UrlResource("file:///" + attachRoot+attachCode + "/" + storeFileName);
    return resource;
  }

  //다운로드
  @GetMapping("/file/{attachCode}/{fid}")
  //반환 타입이 ResponseEntity<>인 경우 응답 메시지 바디에 직접 쓰기 작업 시도함 (@ResponseBody 불필요)
  public ResponseEntity<Resource> file(@PathVariable String attachCode,
                                       @PathVariable Long fid) throws MalformedURLException {

    ResponseEntity<Resource> res = null;

    Optional<UploadFile> uploadFile = uploadFileDAO.findFileByUploadFileId(fid);
    if(uploadFile.isEmpty()) return res;

    UploadFile attachFile = uploadFile.get();
    log.info("attachFile={}", attachFile);
    String storeFilename = attachFile.getStoreFilename();
    String uploadFilename = attachFile.getUploadFilename();

    Resource resource = new UrlResource("file:///"+attachRoot+attachCode+"/"+storeFilename);

    //첨부 파일명 한글 깨짐 방지
    String encode = UriUtils.encode(uploadFilename, StandardCharsets.UTF_8);

    //클라이언트가 파일을 다운로드 하기위해 응답 메시지 포함시킴
    String contentDisposition = "attachment; filename=\""+encode+"\"";

    res = ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
        .body(resource);
    return res;
  }

  //첨부파일 삭제
}
