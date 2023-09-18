<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--2022.1.5(수) 14h20-->
<title>회원 가입 진행</title>
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

	<%@ include file="../common/menubar.jsp" %> <!--나(memberEnrollForm.jsp)를 기준으로 경로 찾아감 = 상대경로; menubar에 있는 모든 요소를 이 문서도 쓸 수 있음-->

    <div class="outer">
        <br>
        <h2 align="center">회원 가입 진행</h2>
        
        <!--회원 관련 이러이러한 데이터를 db에 추가하고 싶다고 '요청' -> form 태그 안에 있어야 함-->
        <form id="enroll-form" action="<%= contextPath %>/insert.me" method="post"> <!--회원 정보가 노출되지 않도록 post 방식으로 요청 보내고자 함-->
            <!--id, 비밀번호, 이름, 전화번호, 이메일, 주소, 취미 입력받고자 함 vs 회원 번호는 sequence에 의해 매겨짐 + 가입일, 정보수정일, status는 default값 지정되어 있음-->
            <table>
                <tr>
                    <td>* ID</td>
                    <td><input type="text" name="userId" maxlength="12" required></td>
                    <!--2022.1.17(월) 15h30-->
                    <td><button type="button" onclick="idCheck();" class="btn btn-sm btn-warning">ID 중복 확인</button></td> <!--중복 확인은 추후 Ajax 배우고 나서 구현할 것임-->
                </tr>
                <tr>
                    <td>* 비밀번호</td>
                    <td><input type="password" name="userPwd" maxlength="15" required></td>
                    <td></td>
                </tr>
                <tr>
                    <td>* 비밀번호 확인</td>
                    <td><input type="password" maxlength="15" required></td>
                    <td></td>
                </tr>
                <tr>
                    <td>* 이름</td>
                    <td><input type="text" name="userName" maxlength="6" required></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;전화번호</td>
                    <td><input type="text" name="phone" placeholder="- 포함해서 입력"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;이메일</td>
                    <td><input type="email" name="email"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;주소</td>
                    <td><input type="text" name="address"></td>
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

            <br><br>
            <div align="center">
                <button type="submit" disabled class="btn btn-sm btn-primary">회원 가입</button> <!--id 중복 확인이 완료되어야만 '회원 가입' 버튼 누를 수 있게 함 <- disabled 속성 부여-->
                <button type="reset" class="btn btn-sm btn-secondary">초기화</button>
            </div>

            <script>
                function idCheck() {
                    // 사용자가 입력한 id를 input 태그로부터 뽑아와야 함 -> 우선 해당 input 태그 요소를 뽑아서 jQuery 변수 '$userId'에 담기
                    var $userId = $("#enroll-form input[name=userId]"); // 요소에 대한 id는 문서에서 중복되면 안 되는 바, 신중하게 부여해야 함 + menubar.jsp에도 input[name=userId](name이 userId인 input 요소)가 존재하기 때문에, 조금 더 구체적으로 선택해야 함 -> #enroll-form의 후손 중에서 선택

                    // AJAX로 controller에 요청하기 -> AJAX 처리 오류 내용 확인하려면 브라우저 console(브라우저 개발자 도구 띄우기)을 확인해야 함
                    $.ajax({
                        url : "idCheck.me", // 이 url 처리해 줄 controller/servlet 필요함
                        data : {checkId : $userId.val()},
                        success : function(result) { // result 경우의 수 = "NNNNN" 또는 "NNNNY" 2가지 -> 문자열 동등 비교로 따지기
                        	if (result == "NNNNN") { // 중복된 id가 있는 경우 -> 사용자가 입력한 이 id는 사용 불가
                        		alert("이미 존재하거나 탈퇴한 회원의 ID입니다");
                        		$userId.focus(); // id 재입력 유도
                        	}
                        	else { // 중복된 id가 없는 경우/중복되지 않은 id인 경우 -> 사용 가능 + confirm() 알림창 띄워서 사용자로부터 사용 의사 확인 받기
                        		if (confirm("사용 가능한 ID입니다. 사용하시겠습니까?")) { // 사용자가 사용하겠다고 '확인' 버튼 누르면 = true 반환∂
                        			// 'id 중복 확인' 전까지는 submit 버튼을 (못 눌리게)막았다가/비활성화 시켰다가 -> 이 if문 내부로 오면/사용자가 입력한 id를 사용하기로 결정하면, submit 버튼 활성화
                        			$("#enroll-form button[type=submit]").removeAttr("disabled");
                        			
                        			// + id 값을 더 이상/이후로 바꾸지 못 하도록 확정 <- id input 요소에 readonly 속성 부여
                        			$userId.attr("readonly", true);
                        		}
                        		else { // '취소' 선택/사용자가 입력한 id를 사용하지 않기로 결정하면 -> id를 다시 입력받음
                        			$userId.focus(); // id 재입력 유도
                        		}
                        	}
                        },
                        error : function() {
                            console.log("ID 중복 체크용 AJAX (통신) 실패")
                        }
                    })                   

                }
            </script>

            <br><br><br>
        </form>

    </div>

</body>
</html>