package com.productpractice.practice2.domain.common.file;

public interface UploadFileSVC {

  /**
   * 업로드 파일 등록 : 단건
   * @param uploadFile
   * @return
   */
  Long addFile(UploadFile uploadFile);

//  /**
//   * 업로드 파일 등록 : 여러건
//   * @param uploadFile
//   */
//  void addFile(List<UploadFile> uploadFile);
//
//  /**
//   * 업로드 파일 조회 : 단건
//   * @param uploadFileId
//   * @return
//   */
//  Optional<UploadFile> findFileByUploadFileId(Long uploadFileId);
//
//  /**
//   * 업로드 파일 조회 : 여러건
//   * @param code
//   * @param rid
//   * @return
//   */
//  List<UploadFile> getFilesByCodeWithRid(String code, Long rid);
//
//  /**
//   * 업로드 파일 삭제 by uploadFileId
//   * @param uploadFileId
//   * @return
//   */
//  int deleteFileByUploadFileId(Long uploadFileId);
//
//
//  /**
//   * 업로드 파익 삭제 by code, rid
//   * @param code
//   * @param rid
//   * @return
//   */
//  int deleteFileByCodeWithRid(String code, Long rid);
}
