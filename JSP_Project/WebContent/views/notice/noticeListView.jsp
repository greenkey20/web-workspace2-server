<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.kh.notice.model.vo.Notice" %>
<% 
	ArrayList<Notice> list = (ArrayList<Notice>)request.getAttribute("list"); // getAttribute()의 반환형 = Object -> 강제 형 변환해서 ArrayList<Notice> 자료형의 변수에 담아줌
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<style>
    .outer {
        background-color: seagreen;
        color: mediumspringgreen;
        width: 1000px;
		height: 500px;
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
<!--2022.1.10(월) 11h25-->
<body>
	<!--공지사항 화면 상단에도 menu bar 띄워주고자 함-->
	<%@ include file = "../common/menubar.jsp" %>

	<div class="outer">
		<br>
		<h2 align="center">공지사항</h2>
		<br>

		<!--관리자가 로그인했을 시/로그인한 user가 관리자일 때 '글 작성' 버튼이 보이도록 함-->
		<!--2022.1.10(월) 14h-->
		<% if (loginUser != null && loginUser.getUserId().equals("admin")) { %> <!--로그인이 되었고 + 관리자일 경우-->
			<div align="right" style="width:850px;">
				<!--글 쓰기 버튼 만들기-->
				<!--방법1) button 요소에는 href 속성이 없기 때문에, 버튼을 눌러서 페이지를 이동시키려면 onclick="location.href='요청url'"/onclick 속성에 직접적으로 JavaScript 코드를 이용해서 요청해야 함 -> 번잡스러움-->
				<!--<button onclick="location.href='이동할 페이지'">글 작성</button>-->
				<!--방법2) a 태그를 쓰고도 버튼 모양으로 만들고 싶다면 bootstrap 활용 -> a 태그에 style 주어서 버튼처럼 보이게 함-->
				<a href="<%= contextPath %>/enrollForm.no" class="btn btn-sm btn-primary">글 작성</a>
				<br><br>	
			</div>
		<% } %>

		<table align="center" class="list-area">
			<thead>
				<tr>
					<th>글 번호</th>
					<th width="400">글 제목</th> <!--inline 방식일 때는 기본 단위가 px인 바, px 안 써도 스타일 적용됨-->
					<th width="100">작성자</th>
					<th>조회수</th>
					<th width="100">작성일</th>
				</tr>
			</thead>
			<tbody>
				<% if (list.isEmpty()) { %> <!--공지사항이 하나도 존재하지 않을 경우 = list가 비어있을 경우-->
					<tr>
						<td colspan="5">공지사항이 없습니다</td>
					</tr>
				<% } else { %> <!--공지사항이 존재하는 경우 = list가 차 있을 경우-->
					<% for (Notice n : list) { %> <!--향상된 for문-->
						<tr>
							<td><%= n.getNoticeNo() %></td>
							<td><%= n.getNoticeTitle() %></td>
							<td><%= n.getNoticeWriter() %></td>
							<td><%= n.getCount() %></td>
							<td><%= n.getCreateDate() %></td>
						</tr>
					<% } %>
				<% } %>
			</tbody>
		</table>

		<!--2022.1.10(월) 16h?-->
		<!--JavaScript 언어를 쓰기 위한 script 영역-->
		<script>
			$(function() {
				$('.list-area>tbody>tr').click(function() {
					// console.log("뭔진 몰라도 클릭됨");

					// 클릭했을 때 해당/클릭된 공지사항의 번호를 넘기고자 함 = 해당 tr 태그의 자손 중에서 첫번째 td의 내용물/값 필요함/뽑아야 함
					var nno = $(this).children().eq(0).text(); // 글 번호; 2021.12.24(금) 15h30 jQuery "11_응용 예시.html" > '게시판 list' 예제 참고
					console.log(nno);

					// 글 번호는 primary key인 바, 글 번호 가지고 요청하고자 함 -> localhost:8001/jsp/detail.no?nno=글번호 -> "/detail.no" mapping 값을 갖는 Servlet이 처리 + request의 parameter 영역에 'key = nno, value = 글번호'로 들어감
					// (16h40 강사님 설명 제대로 못 들음) 대놓고 요청 = url에 key와 value를 대놓고 작성해서 요청 보냄 = get 방식으로 요청 보낼 때 "요청할url?key=value&key=value&key=value.." = query string -> ? 뒤의 내용을 직접 만들어서 요청 보내보기 -> get 방식 요청 시, (query string@)url 조작해서 내가 원하는 요청 보낼 수 있음
					location.href = "<%= contextPath %>/detail.no?nno=" + nno;
				})
			})
		</script>

	</div>

</body>
</html>