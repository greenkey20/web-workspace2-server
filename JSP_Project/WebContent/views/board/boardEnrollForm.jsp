<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.kh.board.model.vo.Category" %>
<%
	ArrayList<Category> list = (ArrayList<Category>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반게시판 게시글 작성</title>
<style>
	.outer {
        background-color: seagreen;
        color: mediumspringgreen;
        width: 1000px;
		height: 800px;
        margin: auto;
        margin-top: 50px;
    }

    #enroll-form>table {border: 1px solid mediumspringgreen;}
    
    #enroll-form input, #enroll-form textarea {
        width: 100%;
        box-sizing: border-box;
    }
</style>
</head>
<body>
	<!--2022.1.12(수) 12h35-->
	<%@ include file = "../common/menubar.jsp" %>

	<div class="outer">
		<br>
		<h2 align="center">일반게시판 게시글 작성</h2>
		<br>

		<!--15h 파일 첨부하는 요청을 하는 경우에는 form 태그 내에 꼭/반드시 enctype(속성)="multipart/form-data" 포함시켜야 함 -> request 객체에 자료 넘길 때 multipart라는 형식으로 변환해서 보냄; 부호화 방식을 지정하는 것임
			+ 사용자들이 첨부하는 파일들을 저장할 (내 hard disc 내)경로 미리 지정 -> WebContent > resources > board_upfiles-->
		<form id="enroll-form" action="<%= contextPath %>/insert.bo" method="post" enctype="multipart/form-data">
			<!--제목, 내용, 카테고리, 첨부파일 입력 받기 + 작성자의 회원 번호(userNo)를 hidden으로  같이 넘김-->
			<input type="hidden" name="userNo" value="<%= loginUser.getUserNo() %>">
			
			<table align="center" border="1">
				<tr>
					<th width="100">카테고리</th>
					<td width="500">
						<!--카테고리 자주 안 바뀔 것 같으면/고정된 카테고리 사용하는 경우, 이 jsp 문서에 입력해두면 됨 vs 별도의 테이블로 만들어 관리(추가/수정/삭제 등 유지/보수를 더 용이하게 하기 위해서)-->
						<select name="category">
							<!--2022.1.12(수) 14h35-->
							<% for (Category c : list) { %>
								<option value="<%= c.getCategoryNo() %>"><%= c.getCategoryName() %></option>
							<% } %>
						</select>
					</td>
				</tr>
				<tr>
					<th>제목</th>
					<td><input type="text" name="title" required></td>
				</tr>
				<tr>
					<th>내용</th>
					<td>
						<textarea name="content" rows="10" style="resize: none;" required></textarea>
					</td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td><input type="file" name="upfile"></td>
					<!--나의 질문 = 파일 확장자명 제한하는 것은 어떻게 할까?-->
				</tr>
			</table>

			<br>
			<div align="center">
				<button class="btn btn-sm btn-primary" type="submit">작성</button>
				<!--작성 요청 보내며 전달하는 값 = category+value 값, title+value 값, content+textarea에 입력하는 내용, upfile+첨부된 파일
					위 4가지 정보만으로는 BOARD 테이블을 채울 수 없음 -> 작성자 정보 필요
					방법1) 여기서 요청 시 hidden으로 넘기기
					방법2) Servlet에서 session 객체로부터 빼기-->
				<button class="btn btn-sm btn-secondary" type="reset">취소</button>
			</div>
		</form>
	</div>

</body>
</html>