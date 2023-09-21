<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>expression lg action project</title>
</head>
<body>
	<!--2022.2.7(월) 14h30-->
	<!--http://localhost:8003/el/로 접속 확인-->
	<!--이 index.jsp/welcome file은 WebContent 아래 + WEB-INF 외부에 있어야 함 <- WEB-INF에서 참조는 가능하나(? 제대로 못 들음) 직접 접근 불가능(14h35 강사님의 설명/논리 제대로 이해 못함)-->
	<h1>안녕 ^^</h1>

	<h1>EL(Expression Language, 표현 언어)</h1>
	<p>
		기존에 사용했던 표현식(출력식) &lt;%= name %&gt;과 같이 JSP 상에서 표현하고자 하는 값을 ${ name }의 형식으로 표현해서 작성하는 것
	</p>
	
	<h3>1. EL 기본 구문</h3>
	<a href="/el/el.do">01_EL 기본 구문</a>
	
	<!--2022.2.7(월) 16h35-->
	<h3>2. EL의 연산자</h3>
	<a href="/el/operation.do">02_EL의 연산자</a>
	<!--프로그래밍에서 가장 중요한 연산자 = 조건문에 조건으로 사용하기 때문에, 비교 연산자-->
	
	<!--2022.2.8(화) 9h35-->
	<hr>
	<!--JSP를 이루는 구성 인자
		1. JSP scripting 원소 = JSP 페이지에서 Java 코드를 직접 기술할 수 있게 하는 기능/기술
		 e.g. scriptlet, 표현식, 선언문
		2. 지시어 = JSP 페이지 정보에 대한 내용을 포함하거나(page 지시어), 또 다른 페이지를 포함할 때 사용(include 지시어) <- JSP page 전체에 영향을 미치는 정보를 기술할 때 쓰임
		 e.g. page 지시어, include 지시어, taglib 지시어(libraries 추가 시 사용)
		3. JSP action tag = xml 기술을 이용해서 기존의 JSP 문법을 확장하는 기술을 제공하는 태그 -> JSP 페이지 간 데이터 가지고 넘어갈 수 있음 vs semi-project까지는 session에 담거나, request에 담아 servlet 갔다가 다시 request에 담아 다른 jsp 페이지로 forwarding
		 cf. xml(extensible markup language) 문서 = 태그로 이루어져 있음
		 - 표준(standard) action tag = JSP 페이지 내에서 바로 사용 가능한 태그 -> 별도의 연동 필요 없음 + taglib 지시어 사용x
		   표현법: 모든 태그명 앞에 jsp:라는 접두어가 붙음
		   &lt;jsp:태그명&gt;

		   &lt;/jsp:태그명&gt;
		   cf. 주석 내부인데도 표준 액션 태그로 인식했는데, 일반 꺾쇠를 썼더니 '유효하지 않은 표준 액션'이라는 500 error 발생
		 - custom action tag = JSP 페이지 내에서 바로 사용 불가능한 태그 -> 별도의 연동이 필요함 + taglib 지시어 사용; 모든 태그명 앞에 jsp: 이외의 다른 접두어(종류는 매우 다양함)가 붙음 -> 제공되고 있는 대표적인 유용한 library가 있음 = JSTL
	-->

	<!--2022.2.8(화) 10h-->
	<h1>JSP action tag</h1>
	<p>
		XML 기술을 이용해서 기존의 JSP 문법을 확장시키는 기술을 제공하는 태그들
	</p>

	<h3>1. 표준 액션 태그</h3>
	<p>
		JSP 페이지에서 별도의 library 연동 없이 곧바로 사용 가능한 액션 태그<br>
		태그 앞에 jsp:라는 접두어가 붙음
	</p>
	<a href="views/2_StandardAction/01_include.jsp">01_jsp:include</a><br>
	<a href="views/2_StandardAction/02_forward.jsp">02_jsp:forward</a>
	
	<!--2022.2.8(화) 11h25-->
	<h3>2. custom action tag</h3>
	<a href="views/3_CustomAction/jstl.jsp">JSTL</a>
	
</body>
</html>