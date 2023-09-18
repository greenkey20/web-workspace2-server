<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL formatting library</title>
</head>
<body>
	<!--2022.2.8(화) 16h35-->
	&lt; &gt;
    <h1>1. formatNumber 태그</h1>

    <p>
    	숫자형 데이터의 format/형식을 지정 -> 표현하고자 하는 숫자 데이터의 형식을 통화 기호, % 등 원하는 쓰임에 맞게 지정하는 태그<br><br>
    	
    	표현법:<br>
    	&lt;fmt:formatNumber value="출력할 값" groupingUsed="true/false"(생략 가능) type="percent/currency"(생략 가능) currencySymbol="$"(생략 가능)/&gt;<br><br>
    	
    	groupingUsed 속성 = 3자리마다 ,로 구분해주는 속성; 생략 가능(생략 시 기본 값 = true)<br>
    	type 속성 "percent" = 소수점을 백분율로 변경해서 보여줌<br>
    	type 속성 "currency" = 현재 내가 쓰고 있는 컴퓨터의 local 정보에 따라 정해진 통화/화폐/돈 단위로 보여짐<br>
    	currencySymbol 속성 = 통화 기호 문자의 종류를 지정하는 속성
    </p>
    
    <!--테스트할 변수 생성-->
    <!--JSTL 사용 시 발생하는 대표적인 오류들
    	1. Unknown tag = library 선언을 안 해서 나는 오류 -> taglib 지시어를 사용하면 해결
    	2. No end tag = 종료 태그를 안 닫아준 것-->
    <c:set var="num1" value="123456789"/>
    <c:set var="num2" value="0.7567"/>
    <c:set var="num3" value="50000"/>
    
    num1의 경우,<br>
         그냥 출력 = ${ num1 }<br>
    3자리마다 구분해서 출력 = <fmt:formatNumber value="${ num1 }"/><br> <!--16h50 나의 질문 = 왜 나는 123,456,789(강사님 화면)가 아닌, 123 456 789로 출력되지? >.<-->
         숫자 그대로 출력 = <fmt:formatNumber value="${ num1 }" groupingUsed="false"/><br><br>
    
    num2의 경우,<br>
    percent = <fmt:formatNumber value="${ num2 }" type="percent"/><br><br> <!--76 %-->
    
    <!--2022.2.8(화) 17h-->
    num3의 경우,<br>
    currency = <fmt:formatNumber value="${ num3 }" type="currency"/><br> <!--groupingUsed 속성의 기본 값이 true이기 때문에 3자리마다 ,도 찍혀있음; 나의 질문 = 오라클에서도 형식 바꿀 수 있는데, 여기서 바꾸는 게 더 좋은 것일까요?-->
    currency $ = <fmt:formatNumber value="${ num3 }" type="currency" currencySymbol="$"/><br>
    currency KRW = <fmt:formatNumber value="${ num3 }" type="currency" currencySymbol="KRW"/><br><br>
    
    <hr>
    <h3>2. formatDate 태그</h3>
    <!--db에서 toChar(?) 함수 이용해서 변환해서 가져오는 게 더 편리하고 나은 방법임 -> 이 태그 사용 권장하지는 않음-->
    <p>
    	날짜 및 시간 데이터의 format/형식 지정<br>
    	단, java.util.Date 클래스의 객체 이용해야 함
    </p>
    
    <%
    	// 필기 다 못함
		String current = (String)new Date();
    %>
    
         그냥 출력 = ${ current }<br>
    <ul>
    	<li>
    		현재 날짜 = <fmt:formatDate value="${ current }"/>
    		<!--type 속성은 생략 가능 + 생략 시 기본 값은 type="date"-->
    	</li>
    		현재 시간 = <fmt.formatDate value="${ current }" type="time"/>
    		<!--type "time" = 시간 출력-->
    	<li>
    		현재 날짜 및 시간 = <fmt.formatDate value="${ current }" type="both"/>
    		<!--type "both" = 날짜 및 시간 둘 다 출력-->
    	</li>
    	
    	<li>
    		medium = <fmt.formatDate value="${ current }" type="both" dateStyle="medium" timeStyle="medium"/>
    		<!--dateStyle, timeStyle이 medium인 경우 = 기본 설정, 기본 길이 형식-->
    	</li>
    	
    	<li>
    		long = <fmt.formatDate value="${ current }" type="both" dateStyle="long" timeStyle="long"/>
    	</li>
    	
    	<li>
    		full = <fmt.formatDate value="${ current }" type="both" dateStyle="full" timeStyle="full"/>
    	</li>
    	
    	<li>
    		short = <fmt.formatDate value="${ current }" type="both" dateStyle="short" timeStyle="short"/>
    	</li>
    	
    	<li>
    		dateStyle,timeStyle 혼합(?) 가능 = <fmt.formatDate value="${ current }" type="both" dateStyle="short" timeStyle="full"/>
    	</li>
    	
    	<li>
    		customized = <fmt.formatDate value="${ current }" type="both" pattern="yyyy-MM-dd(E) a HH:mm:ss"/>
    		<!--17h15 강사님의 생각 = 아마도 Java의 SimpleDateFormat(?) 호출해서 이렇게 변환해주는 것 같다-->
    	</li>
    </ul>

</body>
</html>