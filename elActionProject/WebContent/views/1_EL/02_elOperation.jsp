<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EL의 연산자</title>
</head>
<body>
	<!--2022.2.7(월) 17h-->
	<!--현재 웹 표준 = html5 vs 회사 가면 html4 쓰는 경우도 있을 것임-->
	<h1>EL 연산</h1>

	<h3>1. 산술 연산</h3>

	<p>
		기존 방식: 10 + 3 = <%= (int)request.getAttribute("big") + (int)request.getAttribute("small") %>
	</p>

	<p>
		EL 연산<br>
		<!--EL 구문은 jsp 상에서 쓰는 것인 바(17h5 강사님 설명/논리가 잘 이해 안 됨), 자료형 따로 없음-->
		10 + 3 = ${ big + small } <br>
		10 - 3 = ${ big - small } <br>
		10 * 3 = ${ big * small } <br>
		10 / 3 = ${ big / small } 또는 ${ big div small } <br> <!--division-->
		10 % 3 = ${ big % small } 또는 ${ big mod small } <br> <!--modular-->
	</p>

	<hr>
	<h3>2. 숫자 간 대소 비교 연산 -> 결과 값 = boolean/논리형 자료 -> 조건문이나 반복문에 사용 가능함</h3>
	<!--2022.2.8(화) 9h20 나의 질문 = '문자' 대소 비교는 가능할까? 해 봐야지..-->

	<p>
		기존 방식: 10 &gt; 3 &lt;=&gt; <%= (int)request.getAttribute("big") > (int)request.getAttribute("small") %> <!--그냥 꺾쇠로 쓰면 태그로 인식될 가능성 있는 바, 주의해서 표기하기; true-->
	</p>

	<p>
		EL 연산<br>
		10 &gt; 3 &lt;=&gt; ${ big > small } 또는 ${ big gt small } <br> <!--true-->
		10 &lt; 3 &lt;=&gt; ${ big < small } 또는 ${ big lt small } <br> <!--false-->
		10 &gt;= 3 &lt;=&gt; ${ big >= small } 또는 ${ big ge small } <br> <!--true <- greater than or equals(크거나 같은가); equals = 같은가? = 동등 비교-->
		10 &lt;= 3 &lt;=&gt; ${ big <= small } 또는 ${ big le small } <!--false <- less than or equals(작거나 같은가)-->
	</p>

	<hr>
	<h3>3. 동등 비교 연산</h3>

	<p>
		기존 방식<br>
		숫자 비교: 10이 3과 같은가? -> <%= (int)request.getAttribute("big") == (int)request.getAttribute("small") %> <!--false--> <br><br>
		
		문자열 '주소' 비교: sOne과 sTwo(의 주소)가 일치하는가? -> <%= (String)request.getAttribute("sOne") == (String)request.getAttribute("sTwo") %> <!--false--> <br>
		<!--StringPool과 비슷한 개념 = connection pool; 'Connection 객체 생성했다 close' 반복하면 , 자원 소모 큼 vs jdbc에서는 connection pool 기능 제공 -> connection 자원 반납x + pool에 가지고 있다가 줌 -> 자원 관리 효율적으로/편하게 할 수 있음-->
		문자열 '내용물' 비교: sOne과 sTwo(의 내용물)가 일치하는가? -> <%= ((String)request.getAttribute("sOne")).equals((String)request.getAttribute("sTwo")) %> <!--true-->
	</p>
	
	<p>
		EL 연산<br>
		숫자 비교: 10과 3이 일치하는가? -> ${ big == small } 또는 ${ big eq small } <!--false--> <br>
		big에 담긴 값과 10이 일치하는가? -> ${ big == 10 } 또는 ${ big eq 10 } <!--true--> <br><br>
		
		sOne과 sTwo가 일치하는가? -> ${ sOne == sTwo } 또는 ${ sOne eq sTwo } <br> <!--EL에서 문자열의 == 비교는 Java에서 equals()와 같은 동작을 함 -> true-->
		sOne과 sTwo가 일치하지 않는가? -> ${ sOne != sTwo } 또는 ${ sOne ne sTwo } <br> <!--ne = not equals -> false-->
		sOne에 담긴 값과 "안녕"이 일치하는가? -> ${ sOne == '안녕' } 또는 ${ sOne eq "안녕" } <!--EL에서 문자열 literal 제시 시, 홑/쌍따옴표 상관 없음 -> true-->
	</p>

	<hr>
	<h3>4. 객체가 null인지, 또는 list가 비어있는지, 체크</h3>

	<p>
		기존 방식<br>
		객체가 null인지 알고 싶은 경우, 객체명 == null로 비교 -> true 또는 false가 나옴<br>
		리스트가 비어있는지 알고 싶은 경우, 리스트명.isEmpty()나 리스트명.size() == 0을 사용 -> true 또는 false가 나옴<br>
	</p>

	<p>
		EL 연산<br>
		객체 pTwo가 null인가? -> ${ empty pTwo } 또는 ${ pTwo == null } 또는 ${ pTwo eq null } <!--true--> <br>
		객체 pOne이 null인가? -> ${ empty pOne } 또는 ${ pOne == null } 또는 ${ pOne eq null } <!--false--> <br>
		객체 pOne이 null이 아닌가? -> ${ !empty pOne } 또는 ${ not empty pOne } 또는 ${ pOne != null } 또는 ${ pOne ne null } <!--true--> <br><br>
		
		리스트 lOne이 텅 비어있는가? -> ${ empty lOne } <!--true--> <br>
		리스트 lTwo가 텅 비어있는가? -> ${ empty lTwo } <!--false-->
	</p>

	<hr>
	<h3>5. 논리 연산자</h3>

	<p>
		기존 방식: &&(AND), ||(OR)
	</p>

	<p>
		EL 연산<br>
		AND 연산: ${ true and true } 또는 ${ true && true } <br>
		OR 연산: ${ true or false } 또는 ${ true || false } <!--여기서 'false' = dead code cf. dead codes 최소화하는 것이 좋음 + 변수도 꼭 필요한 변수만 선언해두고, 추후 필요할 때 추가-->
	</p>
	
	<hr>
	<!--2022.2.7(월) 17h40 직접 풀어보기 -> 2022.2.8(화) 9h25 강사님 설명-->
	<h3>연습 문제 = 최대한 EL 연산에서 배운 키워드만 가지고 써보기</h3>

	<p>
		big이 small보다 크고, lOne이 텅 비어있는가? -> ${ (big gt small) and (empty lOne) } <br> <!--true-->
		big과 small의 곱은 4의 배수(4로 나눈 나머지가 0)인가? -> ${ (big * small mod 4) eq 0 } <br> <!--false; 나는 ((big * small) mod 4)와 같이 * 연산 먼저 괄호로 묶었었는데, 괄호 필요 없음-->
		lTwo가 텅 비어있지 않거나, 또는 sOne에 담긴 값이 "안녕하세요"와 일치하는가? -> ${ (not empty lTwo) or (sOne eq "안녕하세요") } <!--true; 별개의 연산은 ()로 묶어주고, '또는' 연산자로 연결; ${ !empty lTwo }이 true이므로, ||의 두번째 피연산자 값에 상관 없이 true임-->
	</p>

</body>
</html>