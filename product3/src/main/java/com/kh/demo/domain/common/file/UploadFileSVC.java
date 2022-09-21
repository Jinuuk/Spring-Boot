package com.kh.demo.domain.common.file;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileSVC {

  /**
   * 업로드 파일 등록 - 단건
   * @param file 첨부파일 단건
   * @return 파일Id
   */
  Long addFile(MultipartFile file);

//  /**
//   * 업로드 파일 등록 - 여러건
//   * @param uploadFile
//   */
//  void addFile(List<UploadFile> uploadFile);
//
//  /**
//   * 업로드 파일 조회 여러건
//   * @param code
//   * @param rid
//   * @return
//   */
//  List<UploadFile> getFilesByCodeWithRid(String code,Long rid);
//
//
//  /**
//   * 업로드 파일 조회 단건
//   * @param uploadFileId
//   * @return
//   */
//  Optional<UploadFile> findFileByUploadFileId(Long uploadFileId);
//
//
//  /**
//   * 첨부파일 삭제 by uploadFileId
//   * @param uploadFileId 첨부파일아이디
//   * @return 삭제한 레코드수
//   */
//  int deleteFileByUploadFileId(Long uploadFileId);
//
//  /**
//   * 첨부파일 삭제 By code, rid
//   * @param code 첨부파일 분류코드
//   * @param rid 첨부파일아이디
//   * @return 삭제한 레코드수
//   */
//  int deleteFileByCodeWithRid(String code, Long rid);
}
