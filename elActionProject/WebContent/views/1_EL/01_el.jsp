<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.model.vo.Person" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EL 기본 구문</title>
</head>
<body>
    <!--2022.2.7(월) 15h25-->
    <!--1행, 3행 시작 부분에 있는 page = 지시어(o) page scope(x); page scope 사용하려면 객체 생성해야 함-->
    <h1>EL 기본 구문</h1>

    <h3>1. 기존 방식 = 스크립틀릿과 표현식을 이용 -> 각 영역(scope)에 담겨있는 값을 뽑아서 화면에 출력</h3>
        
    <!--
    <%
    	// requestScope에 담긴 값 뽑기
    	String classRoom = (String)request.getAttribute("classRoom");
    	Person student = (Person)request.getAttribute("student");
    	
    	// sessionScope에 담긴 값 뽑기
    	String academy = (String)session.getAttribute("academy");
    	Person teacher = (Person)session.getAttribute("teacher");
    %>

    <p>
        학원명 : <%= academy %> <br>
        강의장 : <%= classRoom %> <br>
        강사 : <%= teacher.getName() %> (<%= teacher.getAge() %>세, <%= teacher.getGender() %>)<br><br>

        수강생 정보<br>
        <ul>
            <li>이름 : <%= student.getName() %></li>
            <li>나이 : <%= student.getAge() %></li>
            <li>성별 : <%= student.getGender() %></li>
        </ul>
    </p>
    -->

    <hr>
    <h3>2. EL 이용 -> 보다 쉽게 해당 scope에 저장된 값을 출력</h3>
    <p>
        EL은 getXXX(getAttribute, getter 메소드)를 통해 값을 빼올 필요 없이, key 값만 제시하면 바로 값에 접근(+출력) 가능함<br>
	        내부적으로 해당 scope 영역의 해당 key 값에 해당하는 value 값을 가져올 수 있음<br>
	        기본적으로 EL은 JSP 내장 객체 종류(4가지)를 구분하지 않고, 자동적으로 모든 내장 객체의 key 값을 검색해서, 존재하는 경우 값을 가져옴
    </p>

    <p>
	        학원명: ${ academy } <br>
	        강의장: ${ classRoom } <br>
	        강사 : ${ teacher.name } (${ teacher.age }세, ${ teacher.gender }) <br><br>
        <!--2022.2.7(월) 16h
        	'teacher = 객체('teacher'에 접근했을 때 value 값 = Person 타입 객체)' + 'name 필드'는 private으로 캡슐화시켜두었기 때문에 getter로만 접근 가능함 -> teacher라는 객체의 'name 필드' 값에 직접 접근한 것은 아님
                           해당 Person 객체의 각 필드에 담긴 값을 출력하고자 한다면, 'key 값.필드명'으로 접근하면 됨 = 눈에 보이는 소스코드 상 직접 접근하는 것 같지만, 내부적으로 getter 메소드를 찾아 호출해서 값을 가져오는 구조임 -> 항상 명심해야 할 사항 = getter 메소드는 항상 만들어줘야 함-->
            
                 수강생 정보<br>
       <ul>
           <li>이름 : ${ student.name }</li>
           <li>나이 : ${ student.age }</li>
           <li>성별 : ${ student.gender }</li>
       </ul>
    </p>
    
    <hr>
    <h3>3. EL 사용 시, 내장 객체들에 저장된 key 값이 동일할 경우</h3>
    "scope" key 값에 담긴 value 값 = ${ scope } <br> <!--'request'가 찍힘-->
    <!--EL은 공유 범위가 가장 작은 scope에서부터 해당 key 값을 검색함 -> 'page -> request -> session -> application' 순으로 key 값을 찾음
    	만약에 모든 영역에서 해당 key에 매칭되는 값을 못 찾았을 경우, 아무 것도 출력 안 됨 + 오류 안 남-->
    	
          표현식으로 없는 key 값을 제시했을 경우, &lt;%= aaa %&gt; <br> <!--500 error 발생-->
	 EL로 없는 key 값을 제시했을 경우, ${ aaa } <br>
     
    <hr>
    <h3>4. 직접 scope 영역을 지정해서 접근</h3>
    <%
    	// pageScope에 담기
    	pageContext.setAttribute("scope", "page");
    %>
    
    QUIZ = ${ scope }를 작성하면, "scope" key 값에 해당하는 value 값으로 어떤/무슨 값이 나올까? -> ANSWER = page <- 공유 범위가 가장 작은 scope의 key 값 먼저 검색 <br><br>
    
    pageScope에 담긴  "scope" key 값에 해당하는 value + requestScope, sessionScope, applicationScope에 있는 "scope" key 값에 해당하는 values도 뽑고 싶은 경우<br>
    pageScope에 담긴 값 = ${ scope } 또는 ${ pageScope.scope } <br> <!--${ scope } = 정해진 것 vs ${ pageScope.scope } = 내가 정한 것? 2022.2.7(월) 16h25 강사님 설명 제대로 못 들음-->
    requestScope에 담긴 값 = ${ requestScope.scope } <br>
    sessionScope에 담긴 값 = ${ sessionScope.scope } <br>
    applicationScope에 담긴 값 = ${ applicationScope.scope } <br><br>
    <!--2022.2.8(화) 9h20 나의 질문 = pageScope, requestScope 등은 EL에서 정해진 키워드 같은 것인가?-->
    
         잘못된 접근 예시 = session 객체에서 'classRoom'이라는 key 값으로 접근 = ${ sessionScope.classRoom } -> 'classRoom' key 값은 request에 있는 것인 바, 아무 것도 출력되지 않음 <br><br>
    1개 scope에만 key 값이 존재하는 경우에 ${ 'key 값' }으로 써도 값 출력되지만, 정확한 scope 기재하지 않는 경우 page 객체부터 검/탐색하기 때문에 미세하지만 처리 성능/속도에 영향 미칠 수 있는 바, 내가 검색하고자 하는 scope 기재하는 것이 좋음

</body>
</html>