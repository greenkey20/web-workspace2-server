package com.kh.board.model.vo;

// 2022.1.18(화) 16h35 댓글 달기 기능 구현 시작하며, 댓글 vo 클래스 생성
public class Reply {
	
	// 필드 선언
	private int replyNo; // REPLY_NO	NUMBER	No		1	댓글번호
	private String replyContent; // REPLY_CONTENT	VARCHAR2(400 BYTE)	No		2	댓글내용
	private int refBno; // REF_BNO	NUMBER	No		3	참조하는게시글번호
	private String replyWriter; // REPLY_WRITER	NUMBER	No		4	작성자회원번호 -> userNo나 userId 모두 담을 수 있도록 String 자료형으로 만듦 
	private String createDate; // CREATE_DATE	DATE	No	SYSDATE 	5	작성일
	private String status; // STATUS	VARCHAR2(1 BYTE)	Yes	'Y' 	6	상태값(Y/N)
	
	public Reply() {
		super();
	}

	public Reply(int replyNo, String replyContent, int refBno, String replyWriter, String createDate, String status) {
		super();
		this.replyNo = replyNo;
		this.replyContent = replyContent;
		this.refBno = refBno;
		this.replyWriter = replyWriter;
		this.createDate = createDate;
		this.status = status;
	}

	// 2022.1.18(화) 17H20 DB에서 댓글 조회해오며 이렇게 매개변수 4개 받는 생성자 추가
	public Reply(int replyNo, String replyContent, String replyWriter, String createDate) {
		super();
		this.replyNo = replyNo;
		this.replyContent = replyContent;
		this.replyWriter = replyWriter;
		this.createDate = createDate;
	}

	public int getReplyNo() {
		return replyNo;
	}

	public void setReplyNo(int replyNo) {
		this.replyNo = replyNo;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public int getRefBno() {
		return refBno;
	}

	public void setRefBno(int refBno) {
		this.refBno = refBno;
	}

	public String getReplyWriter() {
		return replyWriter;
	}

	public void setReplyWriter(String replyWriter) {
		this.replyWriter = replyWriter;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Reply [replyNo=" + replyNo + ", replyContent=" + replyContent + ", refBno=" + refBno + ", replyWriter="
				+ replyWriter + ", createDate=" + createDate + ", status=" + status + "]";
	}

}
