<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.notice.model.vo.Notice" %>
<% 
	Notice n = (Notice)request.getAttribute("n"); // 2022.1.11(화) 11h35 getAttribute()의 반환형 = Object -> 강제 형 변환해서 Notice 자료형 변수에 담음 -> Notice n 객체에 공지 글 번호, 제목, 내용, 작성자 ID, 작성일이 담겨있음 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 수정</title>
<style>
	.outer {
		background-color: seagreen;
		color: mediumspringgreen;
		width: 1000px;
		height: 500px;
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
	<!--2022.1.11(화) 11h10-->
	<%@ include file="../common/menubar.jsp" %>

	<div class="outer">
        <br>
        <h2 align="center">공지사항 수정</h2>

        <!--공지사항 수정 후 등록해달라고 요청해야 하므로 form 태그 필요-->
        <form id="update-form" action="<%= contextPath %>/update.no" method="post"> <!--게시글 내용이 너무 길 수 있는 바, header는 길이 제한이 있기 때문에 post 방식으로 요청 보냄-->
        
        	<!--input hidden으로 userNo 받아올 필요 없으므로 삭제함 vs 공지사항 등록 시에는 있었음 -> 12h15 대신, 여기에서는 nno 받아옴; 화면에는 안 보이고, post 방식 요청이니까 url에도 안 보이고, 몰래/숨겨서 넘김-->
        	<input type="hidden" name="nno" value="<%= n.getNoticeNo() %>">

            <table align="center">
                <tr>
                    <th width="50">제목</th>
                    <td width="700"><input type="text" name="title" value="<%= n.getNoticeTitle() %>" required></td>
                    <!--나의 질문 = 내 양식은 왜 폭이 좁아보이지? ㅠ.ㅠ -> 나의 관찰 = 강사님께서는 width를 400으로부터 700으로 변경하셨음-->
                </tr>
                <tr>
                    <th>내용</th>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <textarea name="content" rows="10" style="resize: none;" required><%= n.getNoticeContent() %></textarea>
                    </td>
                </tr>
            </table>
            <br><br>

            <div align="center">
                <button type="submit" class="btn btn-sm btn-primary">수정하기</button>
                <button type="button" class="btn btn-sm btn-secondary" onclick="history.back();">뒤로 가기</button> <!--history 객체(브라우저에서 왔다갔다 한 내용 저장)의 back(): 이전 페이지로 돌아가게 해주는 함수-->
            </div>
        </form>
    </div>

</body>
</html>