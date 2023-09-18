<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.member.model.vo.Member" %>
<%
	//2022.1.5(수) 14h15
	String contextPath = request.getContextPath();
	
	// 2022.1.5(수) 12h
	Member loginUser = (Member)session.getAttribute("loginUser"); // 이 key 값과 세트인 value 'loginUser' 객체는 Member 형태 -> java.lang(?)에 있는(x) 내가 만든(o) 클래스이므로 import 필요; getAttribute()의 반환형 = Object -> 대입을 위해서는 강제 형 변환 필요
	// 로그인 전 menubar.jsp가 로딩될 때 loginUser는 null
	// 로그인 후 menubar.jsp가 로딩될 때 loginUser에는 로그인한 회원의 정보가 담겨있음
	
	String alertMsg = (String)session.getAttribute("alertMsg");
	// 서비스(나의 질문 = 어떤 서비스? 로그인?) 요청 전 alertMsg는 null -> 나의 질문 = null Object를 String으로 (강제)형 변환하면 왜 빈문자열("")이 아닌, null인가?
	// 서비스 요청 후 성공 시 alertMsg는 메시지 문구
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>my first app</title>
<!--2022.1.7(금) 앞으로 jQuery 및 bootstrap 사용할 것이라, w3schools.com으로부터 아래 4줄 복사+붙여넣기함-->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<!-- <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script> --> <!--bootstrap에서 copy&paste한 jQuery link-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> <!--2022.1.17(월) AJAX로  id 중복 확인 기능 구현 시, 이걸로 jQuery link 변경-->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<style>
    .login-area, #user-info {float: right;}
    h1 {color: palegreen;}
    .nav-area {background-color: seagreen;}
    .menu {
        display: table-cell; /*inline 요소처럼 배치 가능*/
        height: 50px;
        width: 150px;
    }
    .menu a {
        text-decoration: none;
        color: mediumspringgreen;
        font-size: 20px;
        font-weight: bold;
        display: block;
        width: 100%;
        height: 100%;
        line-height: 50px;
    }
    .menu a:hover {background-color: darkgreen;}
</style>
</head>
<body>
	<script>
		// script 태그 위치 신경쓰기 싫으면 window.onload() 써두기
		// script 태그 내에서도 scriptlet과 같은 jsp 요소 쓸 수 있음
		var msg = "<%= alertMsg %>"; // 메시지 문구 "성공적으로 로그인하셨습니다" 또는 "null"
		
		if (msg != "null") { // msg에 성공 메시지 문구가 담겨있을 경우
			alert(msg);
			
			// 위 줄만 쓰면 menubar.jsp가 로딩될 때마다 매번 alert가 뜸 vs 해결방법 = session에 들어있는 'alertMsg' key 값에  대한 value를 지워줌(객체명.removeAttribute("key 값"))
			<% session.removeAttribute("alertMsg"); %>
		}
	</script>

	<!--2022.1.4(화) 12h25-->
    <h1 align="center">my first WEB app ^O^</h1>

    <div class="login-area">
    
    <!--2022.1.5(수) 12h10-->
   	<% if (loginUser == null) { %>
   		<!--로그인 전에 보여지는 로그인 form-->
        
        <!--로그인 시 데이터(id, 비밀번호)와 함께(?설명 제대로 못 들음 2022.1.4(화) 12h45) 요청을 보내야 하므로 form 태그 사용 -> 정보가 노출되지 않는 post 방식으로 전달-->
        <form action="<%= contextPath %>/login.me" method="post"> <!--/jsp = context path/root; method 안 쓰면 default/기본 = get 방식으로 요청보냄-->
            <table>
                <tr>
                    <th>ID</th>
                    <td><input type="text" name="userId" required></td>
                </tr>
                <tr>
                    <th>비밀번호</th>
                    <td><input type="password" name="userPwd" required></td>
                </tr>
                <tr>
                    <th colspan="2">
                        <button type="submit">로그인</button>
                        <button type="button" onclick="enrollPage();">회원 가입</button>
                    </th>
                </tr>
            </table>
        </form>
        
        <!--2022.1.5(수) 14h45-->
        <script>
        	function enrollPage() {
        		// 페이지 이동 -> localhost:8001/jsp/views/member/memberEnrollForm.jsp
        		// location.href = "<%= contextPath %>/views/member/memberEnrollForm.jsp"; // 내 web application/프로젝트의 directory/폴더 구조가 url에 보임/노출됨 -> 취약점이 생김
        		// vs 단순한 정적인 웹페이지 요청이라고 하더라도 Servlet을 거쳐서 화면 띄워주기 -> url에 Servlet mapping 값(localhost:8001/jsp/mapping값)만 노출됨 -> Servlet "MemberEnrollFormController" 생성/활용
        		location.href = "<%= contextPath %>/enrollForm.me";
        	}
        </script>

        <!--2022.1.4(화) 14h-->
        <!--이 페이지에서의 로그인 요청 = id 및 비밀번호 입력 후 '로그인' 버튼 누르면 로그인 요청이 감-->
        
	<!--2022.1.5(수) 12h10-->
	<% } else { %>		
        <div id="user-info">
            <b><%= loginUser.getUserName() %> 님</b>, 환영합니다<br><br>
            <div>
                <a href="<%= contextPath %>/myPage.me">my page</a> <!--내 application directory 구조 들키지 않도록 Servlet 이용-->
                <!--<a href="<%= contextPath %>/logout.me">로그아웃</a>--><!--default/기본 = get 방식으로 요청보냄-->
                <!--2022.1.18(화) 12h20 server 문제해결 scenario 풀이 시 시도-->
                <button id="logoutBtn" onclick="logout();">로그아웃</button>
            </div>
        </div>
	<% } %>

    <script>
        function logout() {
            location.href = "<%= contextPath %>/logout.me";
        }
    </script>	

    </div>

    <br clear="both"><br> <!--14h15 clear 속성이 뭔지 잘 기억이 안 남 ㅠ.ㅠ -> 2023.9.17(일) 14h30 오른쪽+왼쪽 float 속성 취소(clear) https://aboooks.tistory.com/79-->

    <!--.nav-area>(div.menu>a)*4-->
    <div class="nav-area" align="center">
        <div class="menu"><a href="<%= contextPath %>">home</a></div>
        <div class="menu"><a href="<%= contextPath %>/list.no">공지사항</a></div>
        <div class="menu"><a href="<%= contextPath %>/list.bo?currentPage=1">일반게시판</a></div> <!--1번 페이지로 요청; paging 처리 + 카테고리 있음 + 첨부파일 0 또는 1개 가능-->
        <div class="menu"><a href="<%= contextPath %>/list.th">사진게시판</a></div> <!--paging 처리 + 카테고리 없음 + 첨부(image)파일 1~4개 가능 + 대표 image를 thumbnail로 보여줌-->
    </div>

</body>
</html>