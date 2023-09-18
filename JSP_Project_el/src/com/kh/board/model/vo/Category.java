package com.kh.board.model.vo;

public class Category {
	// 2022.1.12(수) 14h15 일반게시판 글 작성 화면에 카테고리 정보 받아올 때 ArrayList<Category>로 담아오기 위해서, 이 vo 생성 
	private int categoryNo; // CATEGORY_NO	NUMBER	No		1	카테고리번호
	private String categoryName; // CATEGORY_NAME	VARCHAR2(30 BYTE)	No		2	카테고리명
	
	public Category() {
		super();
	}

	public Category(int categoryNo, String categoryName) {
		super();
		this.categoryNo = categoryNo;
		this.categoryName = categoryName;
	}

	public int getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(int categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return "Category [categoryNo=" + categoryNo + ", categoryName=" + categoryName + "]";
	}

}
