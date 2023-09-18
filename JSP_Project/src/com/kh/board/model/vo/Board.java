package com.kh.board.model.vo;

import java.sql.Date;

public class Board {
	// 2022.1.11(화) 16h
	// 필드부; 테이블과 똑같이 만들 필요는 없음
	private int boardNo; // BOARD_NO	NUMBER	No		1	게시글번호 -> primary key
	private int boardType; // BOARD_TYPE	NUMBER	Yes		2	게시글타입(일반1/사진2)
	private String category; // CATEGORY_NO	NUMBER	Yes		3	카테고리번호 -> CATEGORY 테이블과 JOIN할 용도; 2022.1.12(수) 11h15 일반게시판의 게시글 전체 조회해 올 때 CATEGORY_NAME 받아오면서 이 필드의 자료형을  int로부터 String으로 변경
	private String boardTitle; // BOARD_TITLE	VARCHAR2(100 BYTE)	No		4	게시글제목
	private String boardContent; // BOARD_CONTENT	VARCHAR2(4000 BYTE)	No		5	게시글내용
	private String boardWriter; // BOARD_WRITER	NUMBER	No		6	작성자회원번호
	private int count; // COUNT	NUMBER	Yes	0	7	조회수
	private Date createDate; // CREATE_DATE	DATE	No	SYSDATE 	8	작성일
	private String status; // STATUS	VARCHAR2(1 BYTE)	Yes	'Y' 	9	상태값(Y/N)
	private String titleImg; // 2022.1.17(월) 10h 필드 추가 -> 사진게시판 썸네일의 파일명 = 별칭 TITLEIMG에 해당하는 필드
	
	// 기본 생성자
	public Board() {
		super();
	}

	// 모든 필드를 매개변수로 갖는 생성자 
	public Board(int boardNo, int boardType, String category, String boardTitle, String boardContent, String boardWriter,
			int count, Date createDate, String status) {
		super();
		this.boardNo = boardNo;
		this.boardType = boardType;
		this.category = category;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardWriter = boardWriter;
		this.count = count;
		this.createDate = createDate;
		this.status = status;
	}

	// 2022.1.12(수) 11h15 일반게시판의 게시글 전체 조회(일반게시판 목록 페이지 띄우기)해오며 매개변수 6개인 생성자 만듦
	public Board(int boardNo, String category, String boardTitle, String boardWriter, int count, Date createDate) {
		super();
		this.boardNo = boardNo;
		this.category = category;
		this.boardTitle = boardTitle;
		this.boardWriter = boardWriter;
		this.count = count;
		this.createDate = createDate;
	}
	
	// 2022.1.13(목) 12h30 일반게시판 게시글 상세 조회하며 매개변수 6개인 생성자 만듦
	public Board(int boardNo, String category, String boardTitle, String boardContent, String boardWriter,
			Date createDate) {
		super();
		this.boardNo = boardNo;
		this.category = category;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardWriter = boardWriter;
		this.createDate = createDate;
	}

	// getter, setter
	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public int getBoardType() {
		return boardType;
	}

	public void setBoardType(int boardType) {
		this.boardType = boardType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public String getBoardWriter() {
		return boardWriter;
	}

	public void setBoardWriter(String boardWriter) {
		this.boardWriter = boardWriter;
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
	
	// 2022.1.17(월) 10h 필드 추가하며 getter+setter 추가
	public String getTitleImg() {
		return titleImg;
	}

	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}

	// toString() 오버라이딩
	@Override
	public String toString() {
		return "Board [boardNo=" + boardNo + ", boardType=" + boardType + ", category=" + category + ", boardTitle="
				+ boardTitle + ", boardContent=" + boardContent + ", boardWriter=" + boardWriter + ", count=" + count
				+ ", createDate=" + createDate + ", status=" + status + "]";
	}

}
