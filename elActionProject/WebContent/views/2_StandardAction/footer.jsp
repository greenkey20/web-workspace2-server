<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat, java.util.Date" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>footer</title>
</head>
<body>
    <!--2022.2.8(화) 10h10-->
    <%
    	// Java Date 객체 -> new Date() 객체 = 오늘 날짜 나옴 -> 오늘 날짜 기준으로, SimpleDateFormat 객체를 이용해서 연도만 뽑아서 채워넣기 + java.lang에 있는 클래스가 아니기 때문에 import 필요함
    	String year = new SimpleDateFormat("yyyy").format(new Date()); 
    %>
    
    <!--1998 = 시작 연도 ~ 2022 = 현재 연도; 매년 해가 바뀔 때마다 직접 연도를 바꾸는 것은 까먹을 수도 있고 번거로움 vs 동적인 data로 변경-->
    Copyright © 1998-<%= year %> KH Information Educational Institute All Right Reserved
    
    <br>
    01_include.jsp로부터 전달받은 'test'라는 key 값의 value를 출력<br>
    test : ${ param.test }
    
</body>
</html>