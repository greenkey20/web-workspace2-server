<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.notice.model.vo.Notice" %>
<% 
	Notice n = (Notice)request.getAttribute("n"); // 2022.1.11(화) 10h15 getAttribute()의 반환형 = Object -> 강제 형 변환해서 Notice 자료형 변수에 담음 -> Notice n 객체에 공지 글 번호, 제목, 내용, 작성자 ID, 작성일이 담겨있음
	int nno = n.getNoticeNo();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 상세 보기</title>
<style>
	.outer {
        background-color: seagreen;
        color: mediumspringgreen;
        width: 1000px;
		height: 500px;
        margin: auto;
        margin-top: 50px;
    }

	table {border: 1px solid mediumspringgreen;}
</style>
</head>
<body>
	<!--2022.1.10(월) 16h25-->
	<%@ include file="../common/menubar.jsp" %>

	<div class="outer">
		<br>
		<h2 align="center">공지사항 상세 보기</h2>
		<br>

		<table align="center" border="1">
			<tr>
				<th width="70">제목</th>
				<td width="400" colspan="3"><%= n.getNoticeTitle() %></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><%= n.getNoticeWriter() %></td>
				<th>작성일</th>
				<td><%= n.getCreateDate() %></td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3">
					<p style="height: 250px;"><%= n.getNoticeContent() %></p>
				</td>
			</tr>
		</table>
		<br><br>

		<!--2022.1.11(화) 10h20-->
		<div align="center">
			<a href="<%= contextPath %>/list.no" class="btn btn-sm btn-primary">목록 가기</a>
			
			<!--2022.1.11(화) 11h 작성자만(로그인이 되어있고 + 현재 로그인된 사용자가 작성자와 동일할 경우에만) 아래 버튼이 보여야 함-->
			<% if (loginUser != null && loginUser.getUserId().equals(n.getNoticeWriter())) { %>
				<a href="<%= contextPath %>/updateForm.no?nno=<%= nno %>" class="btn btn-sm btn-secondary">수정하기</a> <!--a 태그를 통해, 'default = get 방식'으로 요청 보냄-->
				
				<!--2022.1.11(화) 14h 실습 -> 14h45 강사님 설명-->
				<a href="<%= contextPath %>/delete.no?nno=<%= nno %>" class="btn btn-sm btn-warning">삭제하기</a> <!--url로 요청 = get 방식; 'localhost:8001/jsp/delete.no?nno(request 객체의 attribute 영역의 key값)=삭제하고자 하는 공지 글 번호(value값)'로 요청 보냄-->
				<!--나의 질문 = a 태그에도 modal 창 띄우는 속성 붙일 수 있을까? -> 강사님 답변 = 해보지는 않았지만/해봐야 알겠지만, 안 될 것 같다-->
			<% } %>
		</div>
	</div>

	<!--2022.1.11(화) 14h 실습 시 내가 쓴 것 + 삭제 버튼 id="deleteNotice" -> 필요 없는 듯 ㅠ.ㅠ
	<script>
		$(function() {
			$('#deleteNotice').click(function() {
				var result = window.confirm("현재 공지 글을 삭제하시겠습니까?");

				if (result) {
					location.href = "<%= contextPath %>/delete.no?nno=<%= nno %>;
				}
			})
		})
	</script>
	  -->

</body>
</html>