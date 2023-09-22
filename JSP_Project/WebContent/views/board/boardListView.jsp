<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.kh.board.model.vo.Board, com.kh.common.model.vo.PageInfo" %>
<%
	// 변수 선언 및 초기화 <- request 객체로부터 받아온 정보 뽑기
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");
	PageInfo pi = (PageInfo)request.getAttribute("pi");
	
	// paging bar 만들 때 필요한 변수 미리 세팅
	int currentPage = pi.getCurrentPage();
	int startPage = pi.getStartPage();
	int endPage = pi.getEndPage();
	int maxPage = pi.getMaxPage();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일(1)반게시판</title>
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
		border: 1px solid mediumspringgreen;
		text-align: center;
	}
	.list-area>tbody>tr:hover {
		cursor: pointer;
		background: darkgreen;
	}
</style>
</head>
<body>
	<!--2022.1.11(화) 16h20-->
	<%@ include file = "../common/menubar.jsp" %>

	<div class="outer">	
		<br>
		<h2 align="center">일반게시판</h2>
		<br>

		<!--2022.1.12(수) 12h25-->
		<!--로그인한 회원만 보여지는 버튼 <- loginUser 변수가 null인지 아닌지 판단-->
		<% if (loginUser != null) { %>
			<div align="right" style="width:830px">
				<a href="<%= contextPath %>/enrollForm.bo" class="btn btn-sm btn-primary">글 작성</a>
			</div>
		<% } %>
		<br><br>

		<table align="center" class="list-area">
			<thead>
				<tr>
					<th width="70">글 번호</th>
					<th width="70">카테고리</th>
					<th width="300">제목</th>
					<th width="100">작성자</th>
					<th width="50">조회수</th>
					<th width="100">작성일</th>
				</tr>
			</thead>
			<tbody>
				<!--게시글 출력-->
				<!--2022.1.12(수) 9h-->
				<% if (list.isEmpty()) { %> <!--list가 비어있는 경우-->
					<tr>
						<td colspan="6">조회된 게시글이 없습니다</td>
					</tr>
				<% } else { %>
					<!--반복문/향상된 for문 사용 -> 배열/컬렉션에 저장된 값이 매 반복마다 1개씩 순서대로 읽혀서 변수에 저장됨 -> list에 있는 요소들(의 값)을 순차적으로 접근해서 b로 뽑아와서, 화면에 보여주기-->
					<% for (Board b : list) { %>
						<tr>
							<td><%= b.getBoardNo() %></td>
							<td><%= b.getCategory() %></td>
							<td><%= b.getBoardTitle() %></td>
							<td><%= b.getBoardWriter() %></td>
							<td><%= b.getCount() %></td>
							<td><%= b.getCreateDate() %></td>
						</tr>
					<% } %>
				<% } %>
			</tbody>		
		</table>

		<br><br>
		<!--2022.1.13(목) 12h10-->
		<script>
			$(function() {
				$(".list-area>tbody>tr").click(function() { // 선택된 요소를 클릭하면 + event handler 부여/기술/기재
					// /jsp/detail.bo?bno=X로 요청 보낼 것임 <- 선택된 요소의 자식 요소(td들) 중 첫번째 요소의 text/내용물
					location.href = "<%= contextPath %>/detail.bo?bno=" + $(this).children().eq(0).text();
				})
			})
		</script>

		<!--paging bar-->
		<div class="paging-area" align="center">
			<!--2022.1.12(수) 12h-->
			<!--paging buttons-->
			<!--paging bar에서 '<'를 담당 -> 이전 페이지로 이동; 단, 1페이지의 경우에는 이전 페이지가 없으므로, 현재 1페이지에 있는 경우에는 '<' 버튼 보이지 않음-->
			<% if (currentPage != 1) { %>
				<button onclick="location.href='<%= contextPath %>/list.bo?currentPage=<%= currentPage - 1 %>'">&lt;</button>
			<% } %>
			
			<!--button{$}*10 -> 처음에 JSP 페이지 만들 때/화면 구현 시, 눈에 보이게만 하기 위해서/dummy로 만든 버튼 10개 vs Servlet을 통해 db에서 받아온 데이터를 바탕으로 동적으로 paging buttons 생성하고 보여주기-->
			<% for (int i = startPage; i <= endPage; i++) { %>
				<% if (i != currentPage) { %>
					<!--/jsp/list.bo?currentPage=XX-->
					<button onclick="location.href='<%= contextPath %>/list.bo?currentPage=<%= i %>'"><%= i %></button>
				<% } else { %> <!--내가 현재 몇 번 페이지에 위치하고 있는지 paging bar에서 볼 수 있도록 vs otherwise 주소창 url에 currentPage의 value 값 확인해야 함-->
					<button disabled><%= i %></button>
				<% } %>
			<% } %>
			
			<!--paging bar에서 '>'를 담당 -> 다음 페이지로 이동; 단, 마지막 페이지의 경우에는 더 다음 페이지로 넘어가면 안 되므로, 현재 마지막 페이지에 있는 경우에는 '>' 버튼 보이지 않음-->
			<% if (currentPage != maxPage) { %>
				<button onclick="location.href='<%= contextPath %>/list.bo?currentPage=<%= currentPage + 1 %>'">&gt;</button>
			<% } %>
		</div>
	</div>

</body>
</html>