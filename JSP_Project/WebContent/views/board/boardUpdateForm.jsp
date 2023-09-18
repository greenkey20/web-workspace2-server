<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.kh.board.model.vo.*" %>
<%
	ArrayList<Category> list = (ArrayList<Category>)request.getAttribute("list");
	Board b = (Board)request.getAttribute("b");
	Attachment at = (Attachment)request.getAttribute("at");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반게시판 게시글 수정</title>
<style>
	.outer {
        background-color: seagreen;
        color: mediumspringgreen;
        width: 1000px;
		height: 800px;
        margin: auto;
        margin-top: 50px;
    }

    #update-form>table {border: 1px solid mediumspringgreen;}
    
    #update-form input, #update-form textarea {
        width: 100%;
        box-sizing: border-box;
    }
</style>
</head>
<body>
    <!--2022.1.13(목) 16h5-->
	<%@ include file = "../common/menubar.jsp" %>

	<div class="outer">
		<br>
		<h2 align="center">일반게시판 게시글 수정</h2>
		<br>

		<!--15h 파일 첨부하는 요청을 하는 경우에는 form 태그 내에 꼭/반드시 enctype(속성)="multipart/form-data" 포함시켜야 함 -> request 객체에 자료 넘길 때 multipart라는 형식으로 변환해서 보냄; 부호화 방식을 지정하는 것임
			+ 사용자들이 첨부하는 파일들을 저장할 (내 hard disc 내)경로 미리 지정 -> WebContent > resources > board_upfiles-->
		<form id="update-form" action="<%= contextPath %>/update.bo" method="post" enctype="multipart/form-data">
			<!--boardEnrollForm.jsp에서 복사/붙여넣기한 내용 = 제목, 내용, 카테고리, 첨부파일 입력 받기 + 작성자의 회원 번호(userNo)를 hidden으로  같이 넘김-->
			<input type="hidden" name="bno" value="<%= b.getBoardNo() %>"> <!--필기 다 못함 -> boardEnrollForm.jsp에서 복사/붙여넣기한 value 값 수정 안 했다가, 2022.1.14(금) 11h 게시글 번호가 4(=user03의 회원 번호)인 글만 수정되는 오류 발생했음-->
			
			<table align="center" border="1">
				<tr>
					<th width="100">카테고리</th>
					<td width="500">
						<!--카테고리 자주 안 바뀔 것 같으면/고정된 카테고리 사용하는 경우, 이 jsp 문서에 입력해두면 됨 vs 별도의 테이블로 만들어 관리(추가/수정/삭제 등 유지/보수를 더 용이하게 하기 위해서)-->
						<select name="category">
							<!--2022.1.13(목) 16h30-->
							<% for (Category c : list) { %>
								<option value="<%= c.getCategoryNo() %>"><%= c.getCategoryName() %></option>
							<% } %>
						</select>
						
						<script>
                            $(function() { // 문서의 요소들(위 향상된 for문에서 동적으로 만들어진 요소 포함)이 모두 만들어지면
                                $("#update-form option").each(function() { // id가 'update-form'인 요소의 option 후손들에 하나씩 접근
                                    if ($(this).text() == "<%= b.getCategory() %>") { // 내가 금번/지금 접근한 요소의 값(val() 메소드를 쓰면 category 번호가 반환됨 vs text() 메소드를 통해 category명을 반환받음)이 글 작성 시 선택한 카테고리(Java 객체에 있으므로 스크립틀릿 출력식 필요)라면
                                    	$(this).attr("selected", true); // 접근한 요소의 selected 속성의 값을 true로 지정
                                    }
                                })

                            })
						
						</script>
						
					</td>
				</tr>
				<tr>
					<th>제목</th>
					<td><input type="text" name="title" value="<%= b.getBoardTitle() %>" required></td>
				</tr>
				<tr>
					<th>내용</th>
					<td>
						<textarea name="content" rows="10" style="resize: none;" required><%= b.getBoardContent() %></textarea>
					</td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td>
						<% if (at != null) { %> <!--기존의 파일이 있다면 -> 원본 파일명(e.g. aaa.jpg)을 보여줌 + -->
							<%= at.getOriginName() %>
							<input type="hidden" name="originFileNo" value="<%= at.getFileNo() %>">
							<input type="hidden" name="originFileName" value="<%= at.getChangeName() %>">
						<% } %> 
					
						<!--기존의 파일이 있든 없든 보여줄 내용-->
						<input type="file" name="reUpfile">
					</td>
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