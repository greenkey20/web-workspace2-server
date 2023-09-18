<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList, com.kh.model.vo.Person" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL core library</title>
</head>
<body>
	<!--2022.2.8(화) 11h45-->
	<h1>JSTL core library</h1>
	
	<h3>1. 변수/속성/attribute</h3>
	<pre>
		변수 선언(set): &lt; c:set var="변수명" value="리터럴" scope="scope 영역 지정(생략 가능)" /&gt;
		set 태그 = 변수를 선언하고 초기값을 대입해두는 기능 제공 + 해당 변수를 어떤 scope 영역에 담아둘 것인지 지정 가능함(생략 가능; scope 속성을 생략 시 pageScope(page 범위)에 담김) = 해당 scope 영역에 setAttribute라는 메소드를 이용해서 key(var)+value(value) 형태로 데이터를 담아두는 것
		c:set을 통해 선언된 변수는 EL로 접근해서 사용 가능 vs scripting 원소로는 접근 불가능
		
		주의 사항
		1. 변수의 타입을 별도로 지정하지 않음 + 초기값을 반드시 지정해두어야 함
		2. 해당 변수에 담아두고자 하는 값/value 속성에 담는 리터럴을 무조건/반드시 setting해야 함 &lt;- 변수 선언과 동시에 값 초기화
	</pre>
	
	<!--2022.2.8(화) 12h-->
	<c:set var="num1" value="10" /> <!--pageScope에 담김; pageContext.setAttribute("num1", "10");과 동일 -> 꼭 JSTL을 써야 하는 건 아니고, 대체 가능한 2가지 방법임-->
	<c:set var="num2" value="20" scope="request" /> <!--requestScope에 담김; request.setAttribute("num2", "20");과 동일-->
	num1 변수 값 = ${ num1 } <br> <!--10-->
	num2 변수 값 = ${ num2 } <br> <!--20-->
	
	<c:set var="result" value="${ num1 + num2 }" scope="session" /> <!--sessionScope에 담김(우측에 필기 더 있는지 잘 모르겠음 >.<)--> 
	result 변수 값 = ${ result } <br> <!--30-->
	
	<!--변수명만 제시하면 공유 범위가 작은 곳에서부터 찾아지게 됨 -> 티가 나지는 않지만 (검색)속도가 조금 느려질 수는 있음 -> 'scope 영역.변수명' 표기 권장-->
	${ pageScope.num1 } <br> <!--10-->
	${ requestScope.num2 } <br> <!--20-->
	${ sessionScope.result } <br> <!--30-->
	requestScope에서 result라는 변수명을 제시하면, ${ requestScope.result } <br><br> <!--requestScope에는 result라는 변수가 없기 때문에, 아무 것도 안 찍힘-->
	
	<c:set var="result" scope="request">9999</c:set>
	<!--value 속성 + 시작태그-종료태그 사이에도 대입할 값 기술 가능-->
	${ requestScope.result } <br> <!--9999-->
	${ sessionScope.result } <br> <!--30-->
	
	<hr>
	<pre>
		변수 삭제: &lt; c:remove var="제거하고자 하는 변수명" scope="scope 영역 지정(생략 가능)" /&gt;
		해당 변수를 (기재된 경우)해당 scope에서 찾아서 제거하는 태그 + scope 지정 생략 시, 모든 scope에서 해당 변수를 모두 찾아서 제거함
		해당 scope의 .removeAttribute("key 값"); 메소드를 이용해서 제거하는 것과 동일함
	</pre>
	삭제 전 result = ${ result } <br> <!--9999-->
	1. 특정 scope를 지정해서 삭제 <br>
	<c:remove var="result" scope="request" />
	request로부터 삭제 후 result = ${ result } <br> <!--30 = sessionScope.result-->
	2. 모든 scope에서 삭제 <br>
	<c:remove var="result" />
	모든 scope로부터 삭제 후 result = ${ result } <br> <!--아무 것도 안 뜸-->
	
	<hr>
	<pre>
		변수 출력 = 데이터를 출력하고자 할 때 사용하는 태그: &lt; c:out value="출력하고자 하는 값" default="기본 값"(생략 가능) escapeXml="true(=기본 값)/false"(생략 가능) /&gt;
		default = 기본 값 = value 속성에 출력하고자 하는 값이 없을 경우 대체해서 기본 값으로 출력할 내용물을 기술; 생략 가능
		escapeXml = 태그로써 해석할지(false) ou 텍스트/단순한 문자열로써 출력할지(true) 여부; 생략 가능(생략 시 true가 기본 값)
		<!--EL로 표현한 value 값 안에 'request.변수명'과 같은 표기로 scope 지정 가능-->
	</pre>
	result를 EL로 출력 -> ${ result } <br> <!--앞 문단에서 remove 태그로 모두 삭제한 바, 아무 것도 출력 안 됨-->
	result를 c:out으로 출력 -> <c:out value="${ result }" /> <br> <!--앞 문단에서 remove 태그로 모두 삭제한 바, 아무 것도 출력 안 됨-->
	result를 c:out으로 + default 지정해서 출력 -> <c:out value="${ result }" default="값이 없음" /> <br><br>
	
	<!--escapeXml 테스트-->
	<c:set var="outTest" value="<b>출력 테스트</b>" /> <!--테스트하기 위한 변수 선언-->
	outTest를 c:out으로 + escapeXml 속성을 생략하고(=true) 출력 -> <c:out value="${ outTest }" /> <br> <!--escapeXml 속성 생략 시 기본 값 = true -> html 태그가 해석 안 되고, 단순한 문자열로 취급-->
	outTest를 c:out으로 + escapeXml 속성을 false로 지정하고 출력 -> <c:out value="${ outTest }" escapeXml="false" /> <br>
	
	<!--2022.2.8(화) 14h10-->
	<hr>
	<h3>2. 조건문 if &lt;c:if test="조건식"&gt; 조건이 true일 때 실행할 코드 &lt;/c:if&gt;</h3>
	<pre>
		Java의 단일if문과 비슷한 역할을 하는 태그
		조건식은 test라는 속성에 작성 -> ☆☆☆ 조건 작성 시, 반드시 EL 구문으로 작성해야 함
	</pre>
	
	<%--기존 방식
		<% if (조건식) { %>
			조건이 true일 때 실행할 코드
		<% } %>
	--%>
	
	<c:if test="${ num1 gt num2 }"> <!--'num1 > num2'가 false이므로 아래 문구 표시 안 됨-->
		<b>num1이 num2보다 크다</b>
	</c:if>
	
	<c:if test="${ num1 le num2 }">
		<b>num1이 num2보다 작거나 같다</b>
	</c:if>
	<br>
	
	<!--str이라는 이름의 변수에 "안녕하세요" 입력-->
	<c:set var="str" value="안녕하세요" />
	
	<%--기존 방식
		<% if (str.equals("안녕하세요")) { %>
			조건이 true일 때 실행할 코드
		<% } %>
	--%>
	
	<c:if test="${ str eq '안녕하세요' }">
		<mark>hello, world!</mark>
	</c:if>
	
	<c:if test="${ str ne '안녕하세요' }">
		<mark>see you, world!</mark>
	</c:if>
	<br>
	
	<h3>3. 조건문 choose, when, otherwise</h3>
	<pre>
		Java의 if-else, switch문과 비슷한 역할을 하는 태그
		
		표현법:
		&lt;c:choose&gt;
			&lt;c:when test="조건1"&gt; 조건1이 true일 때 실행할 코드 &lt;/c:when&gt;
			&lt;c:when test="조건2"&gt; 조건2이 true일 때 실행할 코드 &lt;/c:when&gt;
			..
			&lt;c:otherwise&gt; 기타의 경우에 실행할 코드 &lt;/c:otherwise&gt; <!--else 블럭 또는 switch문의 default에 해당-->
		&lt;/c:choose &gt;
		
		각 조건들은 c:choose의 하위 요소로, c:when을 통해서 작성 -> c:otherwise에는 조건을 적어주지 않음 <!--조건이 달려있음 = 연산 1회 -> 마지막 case는 굳이 조건 달아주지 않고 otherwise로 제시하는 것이 프로그램/돌아가는 machine의 효율 향상에 좋음-->
		<!--choose-when의 관계 = html에서 select-option, 또는 switch-case 같은 관계-->
	</pre>
	
	<%--기존 방식
		<% if (조건1) { %>
			조건1이 true일 때 실행할 코드
		<% } else if (조건2) { %>
			조건2가  true일 때 실행할 코드
		<% } else { %>
			그 밖의 경우에 실행할 코드
		<% } %>
	--%>
	
	<!--c:choose의 소속으로/c:choose 안에 (html)주석 있/넣으면 500 error 발생 vs 주석은 c:when에 넣어야 함-->
	<!--if문 대용으로 사용 가능-->
	<c:choose>
		<c:when test="${ num1 eq 20 }"> <!--if 블럭-->
			<b>처음 뵙겠습니다</b>
		</c:when>
		<c:when test="${ num1 eq 10 }"> <!--else if 블럭-->
			<b>또 뵙네요~</b>
		</c:when>
		<c:otherwise> <!--else 블럭-->
			<b>안녕히 가세요</b>
		</c:otherwise>
	</c:choose>
	
	<hr>
	<h3>4. 반복문 forEach</h3>
	<pre>
		표현법:
		for loop문
		&lt;c:forEach var="변수명" begin="초기값" end="끝값" step="증가시킬 값"(생략 가능; 생략 시 기본 값 = 1)&gt;
			반복적으로 실행할 코드
		&lt;/c:forEach&gt;
		
		향상된 for문 - web 개발 시 ArrayList에 담아온 값들 순차적으로 접근해서 읽어내는(?) 경우에 쓸 일 많음(강사님 설명 제대로 못 들음 14h40)
		&lt;c:forEach var="변수명" items="순차적으로 접근할 배열/collection명"&gt;
			반복적으로 실행할 코드
		&lt;/c:forEach&gt;
		
		☆☆ var 속성으로 선언된 변수는 EL 구문(o) 표현/출력식(x)으로 접근
	</pre>
	
	<!--for loop문-->
	<%--기존 방식
		<% for (int i = 1; i <= 10; i++) { %>
			반복적으로 실행할 코드
		<% } %>
	--%>
	<!--2022.2.8(화) 15h-->
	<!--db 조회 결과 list를 servlet에서 request.setAttribute("list", list)로 넘겨받았다면, EL 구문 { list.size(강사님 필기 캡쳐 내용 확인해봐야 함) }로 그 list의 크기에 접근할 수 있음 -> 단, list에 담긴 객체에 순차적으로 접근할 경우, 향상된 for문 쓰는 것이 더 나을 수 있음-->
	<c:forEach var="i" begin="1" end="10">
		반복 확인 : ${ i } <br>
	</c:forEach>
	<br>
	
	<%--제어변수 i가 2씩 증가하는 반복문의 기존 방식
		<% for (int i = 1; i <= 10; i+=2) { %>
			i=1,3,5,7,9일 때 반복적으로 실행할 코드
		<% } %>
	--%>
	<c:forEach var="i" begin="1" end="10" step="2">
		반복 확인 : ${ i } <br>
	</c:forEach>
	<br>
	
	<c:forEach var="i" begin="1" end="6">
		<h${ i }>태그 안에도 EL 구문 적용 가능</h${ i }>
	</c:forEach>
	<br>
	
	<!--향상된 for문-->
	<!--테스트를 위한 배열 만들기 <- set 태그의 내부에 구분자 ,로 나열해서 값들 쓰면 '배열'과 같은 역할함 <- set 시작태그-종료태그 사이에도 대입할 값 기술 가능-->
	<c:set var="colors">
		green, limegreen, greenyellow, lightgreen, forestgreen, lime, olivegreen, yellowgreen
	</c:set>
	
	colors 값 = ${ colors }<br>
	
	<ul>
		<c:forEach var="c" items="${ colors }">
			<li style="color:${ c };">${ c }</li>
		</c:forEach>
	</ul>
	
	<!--여기서부터  jsp 문서가 시작 + 아래 리스트 list는 servlet으로부터 응답받았다고 가정================-->
	<%
		ArrayList<Person> list = new ArrayList<>();
	
		list.add(new Person("강토미", 5, "여자"));
		list.add(new Person("강혁", 5, "남자"));
		list.add(new Person("강미피", 4, "여자"));
		
		// 이와 같은 리스트 list는 servlet으로부터 응답받았다고 가정하기
		// request.setAttribute("pList", list);
	%>

	<!--2022.2.8(화) 15h35 나의 시도-->
	<c:set var="pList" value="${ list }" scope="request"></c:set>
	<!--2022.2.8(화) 15h45 강사님 힌트 = 순수 Java 객체인 list 쓰려면, 어쩔 수 없이 출력식 1번 써야 함-->
	<c:set var="pList" value="<%= list %>" scope="request"/>
	
	<table border="1">
		<thead>
			<th>index</th>
			<th>count</th>
			<th>이름</th>
			<th>나이</th>
			<th>성별</th>
		</thead>
		<tbody>
			<%--기존 방식
			<% if (pList.isEmpty) { %>
				조회할 친구가 없습니다
			<% } else { %>
				<% for (Person p : pList) { %>
					<tr>
						<td><%= p.getName() %></td>
						<td><%= p.getAge() %></td>
						<td><%= p.getGender() %></td>
					</tr>
				<% } %>
			<% } %>
			--%>
			
			<!--2022.2.8(화) 16h-->
			<!--아래와 같이 쓰면 jsp 사용이 많이 줄어듦-->
			<c:choose>
				<c:when test="${ empty requestScope.pList }">
					<tr align="center">
						<td colspan="5">조회 결과가 없습니다</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="p" items="${ requestScope.pList }" varStatus="status">
					<!--varStatus = 제어변수의 상태 속성을 어디에 담아둘 것인가 지정 -> 이 반복문의 경우 status라는 이름으로 담아둠-->
						<tr align="center">
							<td>${ status.index }</td>
							<td>${ status.count }</td>
							<td>${ p.name }</td>
							<td>${ p.age }</td>
							<td>${ p.gender }</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			
		</tbody>
	</table>
	<!--이상 변수~향상된 for문 아주아주 중요 -> 잘 기억해두기 vs 이하 내용은 사용 빈도 높지 않음================-->
	
	<hr>
	<h3>5. 반복문 forTokens</h3>
	<!--String? tokenizer 16h5 강사님 설명 제대로 못 들음-->
	<pre>
		표현법:
		&lt;c:forTokens var="각 값을 보관할 변수명" items="분리시키고자 하는 문자열" delims="구분자"&gt;
			반복적으로 실행(출력만 가능)할 문구
		&lt;/c:forTokens&gt;
		
		구분자를 통해서 분리된 각각의 문자열에 순차적으로 접근하면서, 반복 수행
		Java의 split('구분자'), StringTokenizer와 비슷한 역할
		구분자는 특수문자,숫자,알파벳 등 모두 가능
		<!--보통 서블릿에서 구분해서 자료 넘길 것이기 때문에, 여기서 이렇게 구분할 일 많지 않은 바, 사용 빈도 높지 않음-->
	</pre>
	<br>
	
	<!--테스트할 문자열 변수 만들기-->
	<c:set var="device" value="TV,컴퓨터.휴대폰,에어컨/냉장고.세탁기" />
	
	<ul>
		<c:forTokens var="d" items="${ device }" delims=",./">
			<li>${ d }</li>
		</c:forTokens>
	</ul>
	
	<hr>
	<h3>6. 쿼리스트링 관련 태그 = url, param</h3>
	<pre>
		url 경로를 생성하고, 쿼리스트링을 정의할 수 있는 태그; 넘겨야 할 쿼리스트링이 길 경우 사용하면 편함
		
		표현법:
		&lt;c:url var="변수명" value="요청할 url"&gt;
			&lt;c:param name="key 값" value="value 값"/&gt;
			&lt;c:param name="key 값" value="value 값"/&gt;
			..
		&lt;/c:url&gt;
	</pre>
	
	<a href="list.do?cPage=1&num=2&bWriter=3">기존 방식으로 요청</a><br>
	
	<!--c:url 이용-->
	<c:url var="query" value="list.do">
		<c:param name="cPage" value="1" />
		<c:param name="num" value="2" />
		<c:param name="bWriter" value="3" />
	</c:url>
	<!--만들어진 요청을 보낼 때-->
	<a href="${ query }">c:url 방식으로 요청</a>

</body>
</html>