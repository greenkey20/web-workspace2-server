package com.kh.board.model.vo;

import java.sql.Date;

// 2022.1.12(수) 17h10
public class Attachment {
	
	private int fileNo; // FILE_NO	NUMBER	No		1	파일번호
	private int refNo; // REF_BNO	NUMBER	No		2	참조게시글번호
	private String originName; // ORIGIN_NAME	VARCHAR2(255 BYTE)	No		3	파일원본명
	private String changeName; // CHANGE_NAME	VARCHAR2(255 BYTE)	No		4	파일수정명
	private String filePath; // FILE_PATH	VARCHAR2(1000 BYTE)	Yes		5	저장폴더경로
	private Date uploadDate; // UPLOAD_DATE	DATE	No	SYSDATE 	6	업로드일
	private int fileLevel; // FILE_LEVEL	NUMBER	Yes		7	파일레벨(1/2)
	private String status; // STATUS	VARCHAR2(1 BYTE)	Yes	'Y' 	8	
	
	public Attachment() {
		super();
	}

	public Attachment(int fileNo, int refNo, String originName, String changeName, String filePath, Date uploadDate,
			int fileLevel, String status) {
		super();
		this.fileNo = fileNo;
		this.refNo = refNo;
		this.originName = originName;
		this.changeName = changeName;
		this.filePath = filePath;
		this.uploadDate = uploadDate;
		this.fileLevel = fileLevel;
		this.status = status;
	}

	public int getFileNo() {
		return fileNo;
	}

	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}

	public int getRefNo() {
		return refNo;
	}

	public void setRefNo(int refNo) {
		this.refNo = refNo;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getChangeName() {
		return changeName;
	}

	public void setChangeName(String changeName) {
		this.changeName = changeName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public int getFileLevel() {
		return fileLevel;
	}

	public void setFileLevel(int fileLevel) {
		this.fileLevel = fileLevel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Attachment [fileNo=" + fileNo + ", refNo=" + refNo + ", originName=" + originName + ", changeName="
				+ changeName + ", filePath=" + filePath + ", uploadDate=" + uploadDate + ", fileLevel=" + fileLevel
				+ ", status=" + status + "]";
	}

}
