<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL</title>
</head>
<body>
	<!--2022.2.8(화) 11h25-->
	<h1>JSTL</h1>
	<p>
		JSTL = JSP Standard Tag Library의 약자 = JSP에서 사용되는 custom action tag <br>
		공통적으로 사용되는 코드들의 집합을 보다 쉽게 사용할 수 있도록 태그화해서 표준으로 제공하는 library
	</p>
	
	<hr>
	<h3>library 다운로드 후 추가 방법</h3>
	1. https://tomcat.apache.org/download-taglibs.cgi 접속<br>
	2. Standard-1.2.5 jar files 4개 모두 다운로드<br>
	3. WEB-INF\lib 폴더에 추가<br>
	
	<h3>JSTL 선언 방법</h3>
	<p>
		JSTL을 사용하고자 하는 페이지 상단에 taglib 지시어를 사용해서 선언<br><br>
		
		표현법<br>
		&lt;%@ taglib prefix="접두어" uri="library 파일 상의 uri 주소" %&gt;<br>
		접두어는 자유이긴 하나, core library 사용 시에는 보편적으로(=강제) c<br>
		uri(좀 더 큰 개념) != url; JSTL 사용하는 문서 상단 taglib 지시어에 쓰는 uri는 외워야 함
	</p>
	
	<hr>
	<h3>JSTL 분류</h3>
	<h4>1. JSTL core library</h4>
	<p>
		변수, 조건문, 반복문 등 logic과 관련된 문법 제공 <!--변수, 조건문, 반복문 = 프로그래밍의 핵심-->
	</p>
	<a href="01_core.jsp">core library</a>
	<br>
	
	<!--2022.2.8(화) 16h35-->
	<h4>2. JSTL formatting library</h4>
	<p>
		숫자, 날짜, 시간 데이터의 출력 형식을 지정할 때 사용하는 문법 제공
	</p>
	<a href="02_formatting.jsp">formatting library</a>
	<br>
	
	<!--2022.2.8(화) 17h20-->
	<h4>3. JSTL functions library</h4>
	<p>
		EL 구문 안에 사용할 수 있는 메소드들 제공
	</p>
	<a href="03_functions.jsp">functions library</a>
	<br>
	
	
	
	

</body>
</html>