package com.kh.common.model.vo;

public class PageInfo {
	
	// 2022.1.12(수) 10h15
	// paging 처리 관련 7개의 변수들을 필드로 정의
	private int listCount; // 현재 일반게시판의 게시글 총 개수 <- 그룹 함수 중 하나인 COUNT(*) 활용해서 BOARD 테이블로부터 조회
	private int currentPage; // 사용자가 요청한/현재 페이지 -> 아주 특별한 경우가 아니라면/사용자가 즐겨찾기를 해둔 경우가 아니라면, 보통 가장 최신(글이 보여지는)/1번 페이지 요청
	private int pageLimit; // 페이지 하단에 보여질/표시되는 paging bar/buttons의 최대 개수 -> 10개(계산 편리)로 임의 지정
	private int boardLimit; // 한 페이지에 보여질 게시글의 최대 개수 -> 10개로 임의 지정
	private int maxPage; // 가장 마지막 페이지가 몇 번 페이지인지 = 페이지의 총 개수
	private int startPage; // 페이지 하단에 보여질 첫번째 paging bar
	private int endPage; // 페이지 하단에 표시되는 마지막 paging bar
	
	public PageInfo() {
		super();
	}

	public PageInfo(int listCount, int currentPage, int pageLimit, int boardLimit, int maxPage, int startPage,
			int endPage) {
		super();
		this.listCount = listCount;
		this.currentPage = currentPage;
		this.pageLimit = pageLimit;
		this.boardLimit = boardLimit;
		this.maxPage = maxPage;
		this.startPage = startPage;
		this.endPage = endPage;
	}

	public int getListCount() {
		return listCount;
	}

	public void setListCount(int listCount) {
		this.listCount = listCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageLimit() {
		return pageLimit;
	}

	public void setPageLimit(int pageLimit) {
		this.pageLimit = pageLimit;
	}

	public int getBoardLimit() {
		return boardLimit;
	}

	public void setBoardLimit(int boardLimit) {
		this.boardLimit = boardLimit;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	@Override
	public String toString() {
		return "PageInfo [listCount=" + listCount + ", currentPage=" + currentPage + ", pageLimit=" + pageLimit
				+ ", boardLimit=" + boardLimit + ", maxPage=" + maxPage + ", startPage=" + startPage + ", endPage="
				+ endPage + "]";
	}

}
