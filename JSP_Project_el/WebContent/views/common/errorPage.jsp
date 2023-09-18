<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 2022.1.5(수) 12h15
	String errorMsg = (String)request.getAttribute("errorMsg"); // 이 key와 세트인 value는 String(문자열) 자료형; request 객체에 담겨서 왔기 때문에, 위임받은 응답 페이지/이 jsp 파일에서만 사용 가능함
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>error 발생</title>
</head>
<body>
	<h1 align="center" style="color:mediumseagreen"><%= errorMsg %></h1>
</body>
</html>