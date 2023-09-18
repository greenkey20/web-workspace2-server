<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.kh.board.model.vo.*" %>
<%
	// 2022.1.17(월) 12h
	Board b = (Board)request.getAttribute("b");
	ArrayList<Attachment> list = (ArrayList<Attachment>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사진게시판 게시글 상세 조회</title>
<style>
    .outer {
        background-color: seagreen;
        color: mediumspringgreen;
        width: 1000px;
		height: 800px;
        margin: auto;
        margin-top: 50px;
    }
    table {border: 1px solid mediumspringgreen;}
</style>
</head>
<body>
    <!--2022.1.17(월) 10h40-->
	<%@ include file = "../common/menubar.jsp" %>

	<div class="outer">
		<br>
		<h2 align="center">사진게시판 게시글 상세 조회</h2>
        <!--http://localhost:8001/jsp/views/board/thumbnailDetailView.jsp-->
		<br>
			 
        <table align="center" border="1">
            <tr>
                <th width="100">제목</th>
                <td colspan="3"><%= b.getBoardTitle() %></td>
            </tr>
            <tr>
                <td>작성자</td>
                <td><%= b.getBoardWriter() %></td>
                <td>작성일</td>
                <td><%= b.getCreateDate() %></td>
            </tr>
            <tr>
                <th>내용</th>
                <td colspan="3">
                    <p style="height: 50px;"><%= b.getBoardContent() %></p>
                </td>
            </tr>
            <tr>
                <th>대표 이미지</th> <!--반드시 첨부해야 함 -> 무조건/항상 존재함-->
                <td colspan="3" align="center">
                    <img src="<%= contextPath %><%= list.get(0).getFilePath() + list.get(0).getChangeName() %>" width="500" height="300">
                </td>
            </tr>
            <tr>
                <th>상세 이미지</th> <!--선택적으로 첨부 가능 -> 있을 수도 있고, 없을 수도 있음; 12h10 강사님 설명 제대로 못 듣고 필기만 함-->
                <td colspan="3">
                	<% for (int i = 1; i < list.size(); i++) { %>
                		<img src="<%= contextPath %><%= list.get(i).getFilePath() + list.get(i).getChangeName() %>" width="160" height="100"> <!--강사님의 이미지 사이즈 필기는 제대로 못 봄-->
                	<% } %>
                </td>
            </tr>
        </table>

        <br><br>

        <div align="center">
            <a href="<%= contextPath %>/list.th" class="btn btn-sm btn-primary">목록으로 돌아가기</a>
        </div>

	</div>
</body>
</html>