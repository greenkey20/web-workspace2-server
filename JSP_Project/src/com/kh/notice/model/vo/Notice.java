package com.kh.notice.model.vo;

import java.sql.Date;

// 2022.1.10(월) 11h15
public class Notice {
	
	// NOTICE 테이블의 컬럼들을 필드들로 선언
	private int noticeNo; // NOTICE_NO	NUMBER	No		1	공지사항번호 -> primary key, seq 걸려있음
	private String noticeTitle; // NOTICE_TITLE	VARCHAR2(100 BYTE)	No		2	공지사항제목
	private String noticeContent; // NOTICE_CONTENT	VARCHAR2(4000 BYTE)	No		3	공지사항내용
	private String noticeWriter; // NOTICE_WRITER	NUMBER	No		4	작성자회원번호 -> 외래키; NOTICE 테이블에서는 int 컬럼으로 만들었으나, 실제 활용 시 userId를 보여줄 예정이므로, 또는 NoticeInsertController에서 userNo를 String 자료형으로 넘기므로, 추후(_h) String으로 자료형 변경
	private int count; // COUNT	NUMBER	Yes	0(default 값)	5	조회수
	private Date createDate; // CREATE_DATE	DATE	No	SYSDATE(default 값) 	6	작성일
	private String status; // STATUS	VARCHAR2(1 BYTE)	Yes	'Y'(default 값) 	7	상태값(Y/N)
	
	// 기본 생성자
	public Notice() {
		super();
	}

	// 모든 필드가 매개변수로 있는 생성자
	public Notice(int noticeNo, String noticeTitle, String noticeContent, String noticeWriter, int count, Date createDate,
			String status) {
		super();
		this.noticeNo = noticeNo;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.noticeWriter = noticeWriter;
		this.count = count;
		this.createDate = createDate;
		this.status = status;
	}

	public Notice(int noticeNo, String noticeTitle, String noticeWriter, int count, Date createDate) {
		super();
		this.noticeNo = noticeNo;
		this.noticeTitle = noticeTitle;
		this.noticeWriter = noticeWriter;
		this.count = count;
		this.createDate = createDate;
	}
	
	// 2022.1.11(화) 9h40
	// 바로 위 생성자도 매개변수 5개를 받지만, 받는 (4번째)매개변수의 자료형이 다름 = 오버로딩(한 클래스 내에서 같은 메소드명 + 다른 매개변수 개수 및 자료형 vs 오버라이딩(부모클래스의 메소드를 자식클래스의 메소드가 재정의하여 사용)
	public Notice(int noticeNo, String noticeTitle, String noticeContent, String noticeWriter, Date createDate) {
		super();
		this.noticeNo = noticeNo;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.noticeWriter = noticeWriter;
		this.createDate = createDate;
	}

	// getter, setter
	public int getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getNoticeWriter() {
		return noticeWriter;
	}

	public void setNoticeWriter(String noticeWriter) {
		this.noticeWriter = noticeWriter;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// toString() 메소드 오버라이딩
	@Override
	public String toString() {
		return "Notice [noticeNo=" + noticeNo + ", noticeTitle=" + noticeTitle + ", noticeContent=" + noticeContent
				+ ", noticeWriter=" + noticeWriter + ", count=" + count + ", createDate=" + createDate + ", status="
				+ status + "]";
	}

}
