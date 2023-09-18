<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main 화면</title>
</head>
<body>
	<!--2022.1.4(화) 10h45
		사용자가 context path(localhost:8001/jsp/) 요청하면 이 welcome page 화면이 보임-->
	
	<!--이제부터 할 일 = CRUD: 대부분의 컴퓨터 소프트웨어가 가지는 기본적인 데이터 처리 기능을 묶어서 일컫는 말(-> 세상 모든 software는 CRUD) + 사용자 인터페이스가 갖추어야만 하는 기능을 가리키는 용어로써도 사용됨
		Create(생성; INSERT) + Read(읽기/인출; SELECT) + Update(갱신; UPDATE) + Delete(파괴/삭제; DELETE)
		
		회원 서비스: 로그인(R), 회원 가입(C) + ID 중복 체크(R), my page(R; 회원 정보에 있는 내용 읽어서 보여줌), 내 정보 변경(U), 회원 탈퇴(U(status 값을 N으로 변경해두고, 보통 90일 정도는 데이터 남겨둠 vs 데이터베이스 포렌식해서 삭제 데이터 복원해야 하는데 비용 소요) 또는 D)
		공지사항 서비스: 공지사항 리스트 조회(R), 공지사항 상세 조회(R), 공지사항 등록(C), 공지사항 수정(U), 공지사항 삭제(U 또는 D)
		일반게시판 서비스: 일반게시판 리스트 조회(R) + 페이징 처리, 일반게시판 상세 조회(R), 일반게시판 작성(C) + 첨부파일 1개 업로드, 게시판 수정(U), 게시판 삭제(U 또는 D), 댓글 리스트 조회(R), 댓글 작성(C)
		사진게시판 서비스: 사진게시판 리스트 조회(R) + 썸네일, 사진게시판 상세 조회(R), 사진게시판 작성(C) + 다중 파일 업로드
	-->
	
	<!--jdbc template 파일 > Connection 객체 생성 후 확인 차 찍어봄-->
	<!--잘 뜨나요?
		&lt;% com.kh.common.JDBCTemplate.getConnection(); %&gt;-->	
	
	<!--상단에는 menubar.jsp가 보이게 해 줄 것임; 상단 menubar는 하나의 jsp 파일-->
	<%@ include file="views/common/menubar.jsp" %>

</body>
</html>