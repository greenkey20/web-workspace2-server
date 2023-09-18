<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL functions library</title>
</head>
<body>
	<!--2022.2.8(화) 17h20-->
    <h1>JSTL functions library</h1>
    <p>
        functions library는 따로 태그를 제공하는 형태는 아님 -> EL 구문 안에서 메소드를 호출하는 형태로 사용<br>
                  주로 문자열과 관련된 메소드들을 제공함
    </p>
    
    <c:set var='str' value="How are you?"/>
    str = ${ str }<br>
    
         문자열의 길이 = ${ fn:length(str) }글자<br> <!--JSTL 이용-->
         문자열의 길이 = ${ str.length() }글자<br> <!--Java 메소드 이용-->
    
         모두 대문자로 -> ${ fn:toUpperCase(str) } <br>
         모두 소문자로 -> ${ fn:toLowerCase(str) } <br>
         
    are의 시작 인덱스 = ${ fn:indexOf(str, "are") }번째<br>
    are을 were로 변환 = ${ fn:replace(str, "are", "were") }<br> <!--17h30 나의 질문 = 원본 문자열에 영향 안 주는 것인가?-->
    
    str에 "are"이라는 문자열이 포함되어 있는가?
    <c:if test="${ fn:contains(str, 'are') }">
    	응~
    </c:if>

</body>
</html>