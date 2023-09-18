package com.kh.member.model.vo;

import java.sql.Date;

// 2022.1.4(화) 14h25
/**
 * @author greenpianorabbit
 *
 */
public class Member {
	// 클래스의 필드 선언 <- DB의 Member 테이블의 컬럼 정보대로
	private int userNo; // USER_NO	NUMBER primary key/sequence로 자동 입력됨 회원번호
	private String userId; // USER_ID	VARCHAR2(30 BYTE) not null unique 회원아이디
	private String userPwd; // USER_PWD	VARCHAR2(100 BYTE) not null 회원비밀번호
	private String userName; // USER_NAME	VARCHAR2(15 BYTE) not null 회원명
	private String phone; // PHONE	VARCHAR2(13 BYTE) 전화번호
	private String email; // EMAIL	VARCHAR2(100 BYTE) 이메일
	private String address; // ADDRESS	VARCHAR2(100 BYTE) 주소
	private String interest; // INTEREST	VARCHAR2(100 BYTE) 취미
	private Date enrollDate; // ENROLL_DATE	DATE default SYSDATE 회원가입일
	private Date modifyDate; // MODIFY_DATE	DATE default SYSDATE 정보수정일
	private String status; // STATUS	VARCHAR2(1 BYTE) check Y/N default Y상태값(Y/N)	
	
	// 매개변수 없는 + 모든 필드에 대해 매개변수가 있는 생성자
	public Member() {
		super();
	}

	public Member(int userNo, String userId, String userPwd, String userName, String phone, String email,
			String address, String interest, Date enrollDate, Date modifyDate, String status) {
		super();
		this.userNo = userNo;
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.interest = interest;
		this.enrollDate = enrollDate;
		this.modifyDate = modifyDate;
		this.status = status;
	}
	
	// 2022.1.5(수) _h
	// 회원 가입/추가 시 필요한, 매개변수 7개만 받는, 생성자
	public Member(String userId, String userPwd, String userName, String phone, String email, String address,
			String interest) {
		super();
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.interest = interest;
	}

	// 2022.1.7(금) 11h30
	// 나의 회원 정보 수정 시 필요한, 매개변수 6개만 받는, 생성자
	public Member(String userId, String userName, String phone, String email, String address, String interest) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.interest = interest;
	}

	// 필드별 getter 및 setter
	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// toString()
	@Override
	public String toString() {
		return "Member [userNo=" + userNo + ", userId=" + userId + ", userPwd=" + userPwd + ", userName=" + userName
				+ ", phone=" + phone + ", email=" + email + ", address=" + address + ", interest=" + interest
				+ ", enrollDate=" + enrollDate + ", modifyDate=" + modifyDate + ", status=" + status + "]";
	}	

}