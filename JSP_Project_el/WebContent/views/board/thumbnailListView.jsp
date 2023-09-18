<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.kh.board.model.vo.Board" %>
<%
	// 2022.1.17(월) 10h15
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list"); // getAttribute()의 반환형 = Object
	// get class, instance of 등의 방법 -> Java 자료형 확인 가능
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사진게시판</title>
<style>
	.outer {
        background-color: seagreen;
        color: mediumspringgreen;
        width: 1000px;
		height: 800px;
        margin: auto;
        margin-top: 50px;
    }
	.list-area {
		margin: auto;
		width: 800px;
	}
	.thumbnail {
		border: 1px solid mediumspringgreen;
		width: 220px;
		display: inline-block;
		margin: 15px;
	}
	.thumbnail:hover {
		cursor: pointer;
		opacity: 0.8; /* 투명도 속성*/
	}
</style>
</head>
<body>
	<!--2022.1.14(금) 12h5-->
	<%@ include file = "../common/menubar.jsp" %>

	<div class="outer">
		<br>
		<h2 align="center">사진게시판</h2>
		<br>

		<!--로그인한 사용자는 사진게시판에 글쓰기 가능-->
		<% if (loginUser != null) { %>
			<div align="right" style="width: 850px;">
				<a href="<%= contextPath %>/enrollForm.th" class="btn btn-sm btn-primary">글 작성</a>
			</div>
		<% } %>

		<div class="list-area">
			<!--dummy data 3개 -> 2022.1.17(월) 10h20 수정-->			
			<% if (list != null) { %>
				<% for (Board b : list) { %>
					<div class="thumbnail" align="center" style="background: darkgreen;">
						<input type="hidden" value="<%= b.getBoardNo() %>">
						<img src="<%= contextPath %><%= b.getTitleImg() %>" width="200px" height="150px" style="margin-top: 5px;"> <!--/resources/thumbnail_upfiles/대표 이미지 파일명-->
						<p>
							No. <%= b.getBoardNo() %> <%= b.getBoardTitle() %><br>
							조회 수 : <%= b.getCount() %>
						</p>
					</div>
				<% } %>
			<% } else { %> <!--조회된 결과가 없거나, db 조회 결과를 잘못된 key 값/"list"가 아닌 것으로 받아온 경우 등-->
				등록된 게시글이 없습니다
			<% } %>
		</div>

	</div>

	<script>
		$(function() {
			$(".thumbnail").click(function() { // div ".thumbnail" 요소를 클릭했을 때, 아래와 같은 event handler를 실행
				// 클릭될 때마다 url(/jsp/detail.th?bno=x) 요청 <- location.href 속성 이용
				// x = div ".thumbnail" 요소의 p 태그 내의  boardNo -> 이 요소는 선택자로 꺼내오기 어려우니까, 접근이 비교적 쉬운 요소 1개(hidden input 요소)를 만듦
				var bno = $(this).children().eq(0).val();
				location.href = "<%= contextPath %>/detail.th?bno=" + bno; // 동기식 요청 = 페이지가 새로고침되었을 때와 같은 효과로 요청(? 11h15 강사님께서 간단히 설명해주심)
			})
		})
	</script>

</body>
</html>