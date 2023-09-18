<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "com.kh.board.model.vo.*" %>
<%
	Board b = (Board)request.getAttribute("b"); // getAttribute() 반환형 = Object -> 강제 형 변환해서 vo 자료형 변수에 담음
	// Board 객체 b -> 게시글 번호, 카테고리명, 제목, 내용, 작성자 id, 작성일 들어있음
	
	Attachment at = (Attachment)request.getAttribute("at"); // getAttribute() 반환형 = Object -> 강제 형 변환해서 vo 자료형 변수에 담음
	// Attachment 객체 at -> 파일 번호, 원본명, 수정명, 저장 경로 들어있음

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반게시판 게시글 상세 조회</title>
<style>
	.outer {
        background-color: seagreen;
        color: mediumspringgreen;
        width: 1000px;
        margin: auto;
        margin-top: 50px;
    }

	table {border: 1px solid mediumspringgreen;}
</style>
</head>
<body>
	<!--2022.1.13(목) 11h20-->
	<%@ include file = "../common/menubar.jsp" %>

	<div class="outer">
		<br>
		<h2 align="center">일반게시판 게시글 상세 조회</h2>
		<br>

		<table id="detail-area" align="center" border="1">
			<tr>
				<th width="70">카테고리</th>
				<td width="70"><%= b.getCategory() %></td>
				<th width="70">제목</th>
				<td width="350"><%= b.getBoardTitle() %></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><%= b.getBoardWriter() %></td>
				<th>작성일</th>
				<td><%= b.getCreateDate() %></td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3">
					<p style="height: 200px;"><%= b.getBoardContent() %></p>
				</td>
			</tr>
			<tr>
				<th></th>
				<td colspan="3">
					<% if (at == null) { %> <!--첨부 파일이 없을 경우-->
						첨부 파일이 없습니다
					<% } else { %> <!--첨부 파일이 있을 경우-->
						<a download="<%= at.getOriginName() %>" href="<%= contextPath + at.getFilePath() + at.getChangeName() %>"> <!--download 속성 값 = 파일을 어떤 이름으로 download 받을 수 있게 할 것인지 지정 vs download 속성 부여하지 않고 파일명 클릭하면 그 파일이 브라우저에서 열림-->
						<!--강사님 설명 = href 속성은 ip(localhost:8001) 뒤에 붙여주는 것이기 때문에, contextPath(/jsp; 이 서버에 applications 여러 개 올라가 있을 때 식별하는 역할)부터 경로 써줘야 함 vs 나의 관찰 = workspace1 > 2_JSP 프로젝트 index file상 a href에는 localhost:8888/2_JSP(contextPath)/ 뒤의 경로만 기재해줌 -> 나의 관찰 = 윗줄 경로에 contextPath 미포함 시, 파일 다운로드 실패 + 이 문서 하단 버튼들에 contextPath 미포함 시, 404 error-->
						<!--"<%= contextPath %>/<%= at.getFilePath() + at.getChangeName() %>" 이렇게 중간에 slash 2개 들어갔는데, 정상 접근이 되었음 -> 강사님의 설명 = slashes 사이에 공백으로 인식해서 그런 듯-->
							<%= at.getOriginName() %>
						</a>
					<% } %>
				</td>
			</tr>
		</table>

		<br>
		<div align="center">
			<a href="<%= contextPath %>/list.bo?currentPage=1" class="btn btn-primary btn-sm">목록 가기</a>
			<!--나의 질문 = 게시글 상세 조회 페이지에서 주변(e.g. 이전 2 + 이후 2개) 글 목록 보여주기는? + 이전/다음 글 가기 버튼 만들기는? + 본인의 글을 조회 시 increaseCount가 되지 않도록?-->
			
			<!--2022.1.13(목) 15h45 작성자만(로그인이 되어있고 + 현재 로그인된 사용자가 작성자와 동일할 경우에만) 아래 버튼이 보여야 함-->
			<% if (loginUser != null && loginUser.getUserId().equals(b.getBoardWriter())) { %>
				<a href="<%= contextPath %>/updateForm.bo?bno=<%= b.getBoardNo() %>" class="btn btn-sm btn-secondary">수정하기</a> <!--현재 상세 조회한 글의 수정 요청 -> 현재 조회하고 있는 게시글의 번호를 함께 보냄; a 태그를 통해, 'default = get 방식'으로 요청 보냄-->
				
				<!--2022.1.13(목) 숙제 -> 14h45 강사님 설명-->
				<a href="<%= contextPath %>/delete.bo?bno=<%= b.getBoardNo() %>" class="btn btn-sm btn-warning">삭제하기</a> <!--현재 상세 조회한 글의 삭제 요청 -> 현재 조회하고 있는 게시글의 번호를 함께 보냄; url로 요청 = get 방식; 'localhost:8001/jsp/delete.no?nno(request 객체의 attribute 영역의 key값)=삭제하고자 하는 공지 글 번호(value값)'로 요청 보냄-->
				<!--나의 질문 = 팝업창 띄워 '정말 삭제하시겠습니까?' 확인 받고 삭제하고 싶은데(지난 번 공지 글 삭제 시 구현 못했던 것) >.<-->
			<% } %>
		</div>
		<br>		
	</div>

	<br>
	<!--2022.1.18(화) 16h40 AJAX 이론 수업 및 실습 후, 댓글창 만들기-->
	<!--17h 댓글 영역 style 강사님께서 어떻게 수정하셨는지 제대로 못 봄-->
	<div id="reply-area" style="background-color: limegreen;">
		<table border="1" align="center">
			<thead>
				<!--댓글 작성 영역-->
				<% if (loginUser != null) { %> <!--로그인이 되어있을 경우에만 댓글 작성 가능-->
					<tr>
						<th>댓글 작성</th>
						<td>
							<textarea id="replyContent" cols="50" rows="3" style="resize: none;"></textarea>
						</td>
						<td><button onclick="insertReply();" class="btn btn-sm btn-info">댓글 등록</button></td>
					</tr>
				<% } else { %> <!--로그인이 안 되어있을 경우에는, 댓글 작성 불가능-->
					<tr>
						<th>댓글 작성</th>
						<td>
							<textarea readonly cols="50" rows="3" style="resize: none;">댓글 작성을 위해서는 로그인해 주세요~</textarea>
						</td>
						<td><button class="btn btn-sm btn-secondary">댓글 등록</button></td>
					</tr>
				<% } %>
			</thead>
			<tbody>
				<!--dummy data * 3개-->				
				<!-- <tr>
					<td>user01</td>
					<td>헤헤, 댓글 창이네요~</td>
					<td>2022/01/18</td>
				</tr> -->
			</tbody>
		</table>
		<br><br><br>
	</div>

	<script>
		function selectReplyList() {
			$.ajax({
				url : "rlist.do", // url 속성은 반드시 기재해야 함
				data : {bno : <%= b.getBoardNo() %>}, // 현재 페이지에서 조회 중인 게시글의 게시글 번호를 넘겨야 하므로, data 속성 기재해야 함
				success : function(list) {
					// 댓글 개수만큼 반복 -> 문자열로 누적 -> html 속성 값(tbody 태그의 content 부분 내용물)으로 넣어줌
					var result = "";
					for (var i in list) { // for in문
						result += "<tr>"
							   + "<td>" + list[i].replyWriter + "</td>"
							   + "<td>" + list[i].replyContent + "</td>"
							   + "<td>" + list[i].createDate + "</td>"
							   + "</tr>";
					}
					
					$("#reply-area tbody").html(result);
				},
				error : function() {
					console.log("db로부터 댓글 목록 읽어오기 실패");
				}
			})
		}
		
		// selectReplyList() 함수를 누군가가 호출해야 댓글 목록이 보임 + 댓글은 화면이 로딩되었을 때 곧바로 뿌려줘야 함 <- window.onload = $(function() {})
		$(function() {
			selectReplyList(); // 이 문서가 읽어졌을/로딩되었을 때 + 댓글 등록(insertReply() 호출) 시, 댓글 목록 불러옴 -> 만약에 이 코드 생략해도 괜찮긴 할텐데, 문서 로딩 1초 후에(o) 문서 로딩과 동시에(x) 목록이 뜰 것임
			
			// 2022.1.19(수) 10h20
			// 타인의 댓글 작성이 실시간으로 내 브라우저에서 보이도록, 1초마다 '댓글 목록 조회' 함수 selectReplyList() 호출 -> 야매 채팅 기능 구현 가능
			setInterval(selectReplyList, 1000); // ms(밀리초) 단위는 알아서 붙여주는 바, ms 표기하면 syntax error 발생; 나의 질문 = 함수 호출 시 강사님께서 () 붙이셨는지 확인 필요 -> 2022.2.6(일) 18h35 당시 수업 영상 다시 보니 () 안 붙이셨음; setInterval()의 인자로써 함수는 () 안 붙여도 됨
		})
		
		// 10h30 강사님 설명 다시 들어보고 싶음(-> 2022.2.6(일) 18h40 확인해보니 녹화 안 되었음): AJAX 통신하면 브라우저에 리소스 쌓이는 것 -> 로컬 메모리 용량 소모 + 서버에 무리 가는 것은 아님
		
		// 2022.1.19(수) 9h15
		function insertReply() {
			$.ajax({
				url : "rinsert.bo", // 10h 나의 오류 = 이 행 가장 오른쪽에 ,를 안 써서 insertReply() undefined 오류 발생 -> Eclipse에는 오류 안 보였고, 브라우저 개발자 도구 console에 오류 뜸
				data : {
					content : $("#replyContent").val(), // text()가 아닌, val()로 가져와야 함 -> 요소에 따라 다름; 각각 해보고 되는 것 사용하는 수 밖에 없음
					bno : <%= b.getBoardNo() %>
				},
				type : "post", // 댓글 내용이 긴 경우 get 방식으로 요청 시 내용이 잘릴 수도 있으므로, post 방식으로 요청 보냄
				success : function(result) {
					// result 값에 따라서, 성공했으면 성공 메시지 alert() 띄워줌
					if (result > 0) {
						alert("댓글 작성에 성공했습니다");
						selectReplyList();
						$("#replyContent").val(""); // 댓글 작성란에 있던 내용 지워줌 -> 초기화
					}
				},
				error : function() {
					console.log("댓글 삽입용 AJAX 실패");
				}
			})
		}
	</script>

</body>
</html>