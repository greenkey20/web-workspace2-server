<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 작성</title>
<style>
    .outer {
        background-color: seagreen;
        color: mediumspringgreen;
        width: 1000px;
		height: 500px;
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
	<%@ include file="../common/menubar.jsp" %>

    <!--2022.1.10(월) 14h20-->
    <div class="outer">
        <br>
        <h2 align="center">공지사항 작성</h2>

        <!--공지사항 작성 후 등록해달라고 요청해야 하므로 form 태그 필요-->
        <form id="enroll-form" action="<%= contextPath %>/insert.no" method="post"> <!--게시글 내용이 너무 길 수 있는 바, header는 길이 제한이 있기 때문에 post 방식(=http body에 넣어 전송)으로 요청 보냄-->

            <!--2022.1.10(월) 15h10-->
            <input type="hidden" name="userNo" value="<%= loginUser.getUserNo() %>">

            <table align="center">
                <tr>
                    <th width="50">제목</th>
                    <td width="400"><input type="text" name="title" required></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <textarea name="content" rows="10" style="resize: none;" required></textarea>
                    </td>
                </tr>
            </table>
            <br><br>

            <div align="center">
                <button type="submit" class="btn btn-sm btn-primary">등록하기</button>
                <button type="button" class="btn btn-sm btn-secondary" onclick="history.back();">뒤로 가기</button> <!--history 객체(브라우저에서 왔다갔다 한 내용 저장)의 back(): 이전 페이지로 돌아가게 해주는 함수-->
            </div>
        </form>
    </div>

</body>
</html>