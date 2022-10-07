package com.great.jinuk.web.controller.attachFile;

import com.great.jinuk.domain.common.AttachCode;
import com.great.jinuk.domain.common.FileUtils;
import com.great.jinuk.domain.dao.uploadFile.UploadFile;
import com.great.jinuk.domain.svc.uploadFile.UploadFileSVC;
import com.great.jinuk.web.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
//@Controller
@RestController //@Controller + @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/api/attach")
public class ApiAttachFileController {

  private final UploadFileSVC uploadFileSVC;
  private final FileUtils fileUtils;

  //이미지
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/img/{attachCode}/{storeFileName}")
  public Resource img(
      @PathVariable String attachCode,
      @PathVariable String storeFileName) throws MalformedURLException {
    // http://서버:포트/경로...
    // file:///d:/tmp/P0101/xxx-xxx-xxx-xxx.png
    String url = "file:///"+fileUtils.getAttachFilePath(AttachCode.valueOf(attachCode),storeFileName);
    Resource resource = new UrlResource(url);
    return resource;
  }

  //다운로드
  @GetMapping("/file/{attachCode}/{fid}")
  //반환 타입이 ResponseEntity<>인 경우 응답 메시지 바디에 직접 쓰기 작업 시도 함 (@ResponseBody 불필요)
  public ResponseEntity<Resource> file(
      @PathVariable String attachCode,
      @PathVariable Long fid) throws MalformedURLException {

    ResponseEntity<Resource> res = null;

    Optional<UploadFile> uploadFile = uploadFileSVC.findFileByUploadFileId(fid);
    if (uploadFile.isEmpty()) return res;

    UploadFile attachFile = uploadFile.get();
    log.info("attachFile={}", attachFile);
    String storeFileName = attachFile.getStoreFilename();
    String uploadFileName = attachFile.getUploadFilename();

    String url = "file:///"+fileUtils.getAttachFilePath(AttachCode.valueOf(attachCode),storeFileName);
    Resource resource = new UrlResource(url);

    //첨부 파일명의 한글 깨짐 방지
    String encode = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);

    //클라이언트가 파일을 다운로드 해주기위해 응답 메시지 헤더에 포함
    String contentDisposition = "attachment; filename=\"" + encode + "\"";

    res = ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
        .body(resource);
    return res;
  }

  //첨부파일 삭제
  @DeleteMapping("/{fid}")
  public ApiResponse<Object> deleteAttachFile(@PathVariable Long fid) {
    //1)스토리지 파일을 삭제하기위해 첨부분류코드(code)와 저장파일명(storeFilename)을 가져온다.
    Optional<UploadFile> optional = uploadFileSVC.findFileByUploadFileId(fid);
    if (optional.isEmpty()) {
      return ApiResponse.createApiResMsg("01", "찾고자하는 파일이 없습니다.", null);
    }

    int affectedRow = uploadFileSVC.deleteFileByUploadFileId(fid);


    if (affectedRow == 1) {
      return ApiResponse.createApiResMsg("00", "성공", null);
    }
    return ApiResponse.createApiResMsg("99", "실패", null);
  }
}