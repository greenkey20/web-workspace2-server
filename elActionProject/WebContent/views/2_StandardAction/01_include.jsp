<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>표준 액션 태그 - jsp:include</title>
</head>
<body>
    <!--2022.2.8(화) 10h5-->
    <h3>include</h3>

    <p>
    	JSP 파일에 또다른 JSP 파일을 포함시키고자 할 때 사용하는 지시어 -> 또다른 페이지를 포함하고자 할 때 쓰이는 방식
    </p>

    <h4>1. 기존의 include 지시어를 이용한 방식 = 정적 include 방식 = 컴파일 시 포함되는 형태</h4>
    <!--Java 소스코드 포함 -> 컴파일 필요 -> JSP도 컴파일됨-->
    <!--이건 html 주석을 표시하는 바, jsp 코드의 주석 처리는 안 됨 -> include 지시어, 표현/출력식 그대로 두면 38행의 변수 선언 관련하여 duplicate local variable 'year' 선언 안 된다고 500 error 발생
    jsp 방식 @ include file="footer.jsp"
    <br><br>    
	특징: include하고 있는 페이지 상에 선언되어 있는 변수를 현재 이 페이지에서도 사용 가능함<br>
	 e.g. include한 페이지의 year 변수 값 = jsp 표현/출력식 'year'<br>
	단, 현재 이 페이지에서 동일한 이름의 변수를 선언할 수 없음 -> 아래 스크립틀릿 = duplicate local variable 'year'<br>
	<% // String year = "2023"; %>
    -->

    <hr>
    <h4>2. JSP 표준 액션 태그를 이용한 방식 = 동적 include 방식 = runtime 시 포함되는 형태</h4>
    <!--XML 기술을 이용했으므로, 반드시 시작태그와 종료태그를 함께/같이 써야 함
    	단, 시작태그와 종료태그 사이에 넣을 값이 따로 없다면 <시작태그 />와 같이 표현; / 없으면 오류 발생
    	action tag 안에 주석 달면 오류 발생
    	cf. html 빈 태그(시작 태그만 있고, 종료 태그 없이 홀로 있는 태그) 표시 = <br/>-->
	<jsp:include page="footer.jsp" />
	<br><br>
	특징1: include하고 있는 페이지에 선언된 변수를 공유하지 않음 -> 동일한 이름의 변수 선언 가능<br>
	<%
		String year = "2022년";
	%>
	특징2: 포함 시 include하는 페이지로 값을 전달할 수 있음<br>
	<br>
	-- include 테스트 --<br>
	<!--01.include.jsp로부터 footer.jsp로 값 전달-->
	<jsp:include page="footer.jsp">
		<jsp:param value="hej ^^" name="test" />
		<jsp:param value="1111" name="test" />
	</jsp:include>
	<!--param 태그의 경우 name이 겹치는 경우 뒤에 부여된 value는 무시됨 -> 1111 출력 안 됨-->
	<br>
	<jsp:include page="footer.jsp">
		<jsp:param value="see you~" name="test" />
	</jsp:include>

</body>
</html>