<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 정보</title>
<!--회원 정보 수정, 탈퇴; 비밀번호 변경은 따로 빼기-->
<style>
    .outer {
        background-color: seagreen;
        color: mediumspringgreen;
        width: 500px;
        margin: auto;
        margin-top: 50px;
    }
    table {margin: auto;}
    input {margin: 5px;}
</style>
</head>
<body>
	<!--2022.1.5(수) 17h30-->
	<%@ include file="../common/menubar.jsp" %> <!--상대 경로; menubar.jsp 문서 상 session 객체에 담긴 로그인된 회원 정보를 담은 객체 loginUser 사용 가능-->
	
    <!--2022.1.7(금) 10h-->
    <!--로그인된 회원의 정보가 session 객체의 Attribute 영역에 "loginUser"라는 key 값으로 Member 객체 형태(변수명 loginUser)로 담겨있는 바, 거기서 빼옴-->
	<%
		String userId = loginUser.getUserId();
		String userName = loginUser.getUserName();
		
		// 입력이 필수가 아닌 사항들은 입력이 되지 않은 경우 null이 String 변수에 대입되고 null이 화면에 표시됨 vs 빈 문자열 "" 대입해서 공란으로 표시되도록 함
		// 방법1) if문 사용
		/*
		String phone = "";
		if (loginUser.getPhone() != null) {
			phone = loginUser.getPhone();
		}
		*/
		// 방법2) 3항연산자 사용
		String phone = (loginUser.getPhone() == null) ? "" : loginUser.getPhone(); // 3항연산자
		String email = (loginUser.getEmail() == null) ? "" : loginUser.getEmail();
		String address = (loginUser.getAddress() == null) ? "" : loginUser.getAddress();
		String interest = (loginUser.getInterest() == null) ? "" : loginUser.getInterest(); // "운동, 등산, .."	
	%>

	<div class="outer">
		<br>
		<h2 align="center">my page</h2>

		<!--회원 관련 이러이러한 데이터를 db에 수정해서 반영하고 싶다고 '요청' -> form 태그 안에 있어야 함-->
        <form action="<%= contextPath %>/update.me" method="post"> <!--회원 정보가 노출되지 않도록 post 방식으로 요청 보내고자 함-->
            <!--내가 이해한 logic = 이름, 전화번호, 이메일, 주소, 관심 분야 정보 수정 가능 vs 회원 번호+ID+가입일+status는 변경 불가능; 비밀번호 변경은 별도 화면에서 하도록 함; 정보수정일은 default값 지정되어 있음-->
            <table>
                <tr>
					<!--회원 정보 수정 페이지에서 id는 수정 불가해야 함 -> readonly 속성 부여-->
                    <td>* ID</td>
                    <td><input type="text" name="userId" maxlength="12" value="<%= userId %>" readonly></td>
                    <td><button class="btn btn-secondary btn-sm">(중복 확인 여기서 필요한가?)</button></td>
                </tr>           
                <tr>
                    <td>* 이름</td>
                    <td><input type="text" name="userName" maxlength="6" value="<%= userName %>" required></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;전화번호</td>
                    <td><input type="text" name="phone" placeholder="- 포함해서 입력" value="<%= phone %>"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;이메일</td>
                    <td><input type="email" name="email" value="<%= email %>"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;주소</td>
                    <td><input type="text" name="address" value="<%= address %>"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;관심 분야</td>
                    <td colspan="2">
                        <input type="checkbox" name="interest" value="운동" id="sports"><label for="sports">운동</label>
                        <input type="checkbox" name="interest" value="등산" id="hiking"><label for="hiking">등산</label>
                        <input type="checkbox" name="interest" value="산책" id="walking"><label for="walking">산책</label>
                        <br>
                        <input type="checkbox" name="interest" value="요리" id="cooking"><label for="cooking">요리</label>
                        <input type="checkbox" name="interest" value="독서" id="reading"><label for="reading">독서</label>
                        <input type="checkbox" name="interest" value="음악" id="music"><label for="music">음악</label>
                    </td>
                </tr>
            </table>
            
            <!--2022.1.7(금) 10h40-->
            <!--jQuery dns 방식 접속을 위한 links는 menubar.jsp에 넣음-->
            <script>
                $(function() {
                    var interest = "<%= interest %>";

                    // 모든 checkbox 선택하는 선택자 -> 모든 checkbox에 순차적으로 접근(jQuery의 반복문) -> 순차적으로 접근한 checkbox의 value 속성 값이 interest에 포함되어 있을 경우만 체크하고자 함
                    $("input[type=checkbox]").each(function() {
                        // attr(속성명, 속성값) -> attr("checked", true) = checked 속성 부여

                        // JavaScript의 indexOf(찾고자 하는 것의 index를 알려줌 -> 찾고자 하는 문자가 없을 경우는 -1을 return) = jQuery의 search() 메소드
                        // jQuery에서 value 속성 값을 return해주는 메소드 = val()
                        // jQuery에서 현재 접근한 요소를 지칭 = $(this)
                        if (interest.search($(this).val()) != -1) { // search() 메소드의 인자로 받은 내용이 interest(db의 '관심 분야'에 있는 데이터)에 있는지 탐색 -> 현재 순회 중인 checkbox의 value 값이 interest 문자열에 포함되어 있을 경우 vs 포함되지 않은 경우 -1을 반환(=이 조건에 )
                            $(this).attr("checked", true); // 해당 checkbox의 checked 속성 부여
                        }
                    })
                })
            </script>

            <br><br>
            <div align="center">
                <button type="submit" class="btn btn-info btn-sm">회원 정보 변경</button> <!--form 태그 내부의 submit 버튼인 바, 정보 변경 요청을 보냄; bootstrap 적용을 위해 클래스 속성 값 부여-->
                <button type="button" class="btn btn-secondary btn-sm" data-toggle="modal" data-target="#updatePwdForm">비밀번호 변경</button>
                <button type="button" class="btn btn-warning btn-sm" data-toggle="modal" data-target="#deleteMemForm">회원 탈퇴</button>
            </div>

            <br><br><br>
        </form>
	</div>

    <!--2022.1.7(금) 15h?-->
    <!-- Button to Open the Modal -> 내 어플리케이션에서는 필요 없는 바, 주석 처리함-->
    <!-- <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#updatePwdForm">
        Open modal
    </button> -->
    
    <!--The Modal = 비밀번호 변경용으로 쓸 (pop-up)modal -> id를 updatePwdForm으로 변경-->
    <div class="modal" id="updatePwdForm">
        <div class="modal-dialog">
        <div class="modal-content">
    
            <!--Modal Header-->
            <div class="modal-header">
                <h4 class="modal-title">비밀번호 변경</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button> <!--modal 닫기용 'x' 표시-->
            </div>
    
            <!--Modal body = 비밀번호 변경 form 태그 위치-->
            <div class="modal-body">
                <form action="<%= contextPath %>/updatePwd.me" method="post"> <!--이렇게 jsp 문서로 양식 만든 뒤, 이 요청 받아줄/url mapping 값으로 updatePwd.me를 가진 Servlet/controller 생성; 비밀번호와 같이 보안이 중요한 정보를 넘기므로 post 방식으로 전송-->
                    <!--현재 비밀번호, 변경할 비밀번호, 변경할 비밀번호 확인/재입력을 사용자로부터 입력 받음 + 비밀번호의 주인을 확실하게 판별할 수 있는 id도 함께 넘겨줌-->
                    <input type="hidden" name="userId" value="<%= userId %>">
                    <table>
                        <!--(tr>td*2)*3-->
                        <tr>
                            <td>현재 비밀번호</td>
                            <td><input type="password" name="userPwd" required></td>
                        </tr>
                        <tr>
                            <td>변경할 비밀번호</td>
                            <td><input type="password" name="updatePwd" required></td>
                        </tr>
                        <tr>
                            <td>새 비밀번호 재입력</td>
                            <td><input type="password" name="checkPwd" required></td>
                        </tr>
                    </table>
                    <br>
                    <button type="submit" class="btn btn-secondary btn-sm" onclick="return validatePwd();">비밀번호 변경</button> <!--이 버튼 누르면 비밀번호 변경 요청-->
                </form>
            </div>
    
            <!--Modal footer -> 내 어플리케이션에서는 필요 없는 바, 주석 처리함-->
            <!-- <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
            </div> -->    
        </div>
        </div>
    </div>

    <!--'변경할 비밀번호 == 새 비밀번호 재입력'인지 확인하는 script 태그 필기 못함 -> 2022.4.3(일) 13h55 추가-->
    <script>
    	function validatePwd() {
    		if ($("input[name=updatePwd]").val() != $("input[name=checkPwd]").val()) {
    			alert("재입력한 비밀번호가 일치하지 않습니다.")
    			return false;
    		}
    		
    		return true;
    	}
    </script>

    <!--2022.1.7(금) 17h 수업 실습 + 주말 숙제-->
    <!--The Modal = 회원 탈퇴용으로 쓸 (pop-up)modal -> id를 deleteMemForm으로 변경 cf. 강사님께서는 2022.1.10(월) 9h 수업시간에 id를 deleteForm으로 하심-->
    <div class="modal" id="deleteMemForm">
        <div class="modal-dialog">
        <div class="modal-content">
    
            <!--Modal Header-->
            <div class="modal-header">
                <h4 class="modal-title">회원 탈퇴</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button> <!--modal 닫기용 'x' 표시-->
            </div>
    
            <!--Modal body = 회원 탈퇴 form 태그 위치-->
            <div class="modal-body">
                <b>
                    탈퇴 후 복구가 불가능합니다.<br>
                    정말 탈퇴하시겠습니까?<br><br>
                </b>
                <form action="<%= contextPath %>/delete.me" method="post"> <!--이렇게 jsp 문서로 양식 만든 뒤, 이 요청 받아줄/url mapping 값으로 delete.me를 가진 Servlet/controller 생성; 비밀번호가 들어가는 바, post 방식으로 요청 보냄-->
                    <!--현재 비밀번호를 사용자로부터 입력 받음 -> 비밀번호가 맞으면 회원 탈퇴 진행-->
                    <!-- <input type="hidden" name="userId" value="<%= userId %>"> --> <!--2022.1.10(월) 10h 강사님께서는 이 방법 대신 session 객체 불러오는 방법으로 현재 로그인된 회원의 userId 정보 빼내오심-->
                    <!--비밀번호 확인을 통한 본인 증명/확인-->
                    비밀번호를 입력해주세요 <input type="password" name="userPwd" required>
                    <br><br>
                    <button type="submit" class="btn btn-warning btn-sm">회원 탈퇴</button> <!--이 버튼 누르면 회원 탈퇴 요청-->
                </form>
            </div>
        </div>
        </div>
    </div>

</body>
</html>