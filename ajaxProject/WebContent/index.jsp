<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AJAX 개요</title>
<!-- <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script> --> <!--Bootstrap에 있던 jQuery 복사해 온 것 -> 이걸로는 AJAX 실행 안 되는 듯-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> <!--강사님께서 14h40 slack에 업로드해주신 링크-->
</head>
<body>
    <!--2022.1.17(월) 12h40-->
    <!--이 welcome page 접근 경로 = localhost:8001/ajax/-->
    <h1>AJAX 개요</h1>
    <p>
        AJAX = Asynchronous(비동기의, 동시에 일어나지 않는, 비동시적인) JavaScript And XML의 약자 -> 서버로부터 데이터를 가져와 전체 페이지를 새로고침하지 않고 일부만 로드해 내용물만 바꿀 수 있는 기법 -> 비동기식 요청을 보내기 위해서는 AJAX라는, (JavaScript로 만든) 기술이 필요함<br><br>
        참고로, 그동안/기존에 a 태그를 이용한 요청 및 form을 통해 요청한 방식 = 동기식 요청 -> 응답 페이지가 돌아와야(=페이지 화면이 1번 깜빡임) 그 결과를 볼 수 있음<br><br>

        동기식/비동기식 요청 차이<br>
        동기식: 요청 처리 후, 그에 해당하는 응답 페이지가 돌아와야만 그 다음 작업이 가능함 -> 만약 서버에서 응답 페이지를 돌려주는 시간이 지연(delay time 발생)되면(+또는 time session out이 발생) 무작정 기다려야 함/흰 페이지를 보게 됨<br>
        <!--2022.1.17(월) 14h5-->
               전체 페이지가 reload됨/새로고침/페이지가 1번 깜빡거리면서 넘어감<br><br>        
        비동기식: 현재 페이지는 그대로 유지하면서 중간중간마다 추가적인 요청을 보내줄 수 있고, 요청을 한다고 해서 다른 페이지로 넘어가지는 않음/현재 페이지를 유지 + 요청을 보내놓고 그에 해당되는 응답이 돌아올 때까지 현재 페이지에서 다른 작업을 할 수 있음/페이지가 깜빡거리지 않음<br>
        비동기식의 단점:
        1. 현재 페이지에 지속적으로 리소스(나의 질문 = resources가 무엇일까?)가 쌓임 -> 페이지 속도가 현저히 느려질 수 있음<br>
        2. 페이지 내 (요소가 계속 추가되는 등) 복잡도가 기하급수적으로 증가 -> error 발생 시 debugging/오류 수정이 어려움<br>
        3. 요청 후 돌아온 응답 데이터를 가지고 새로운 요소를 현재 페이지 내에서 동적으로 만들어서 뿌려줘야 함 -> DOM 요소를 새로이 만들어내는 구문을 잘 익혀두어야 함<br><br>

        AJAX 구현 방식 = JavaScript 방식 + jQuery 방식 -> jQuery 방식의 코드가 간결하고 사용하기 쉬움<br><br>    
    </p>

    <!--e.g. Naver 회원 가입 화면, main page에서 검색어 입력/신문기사나 날씨 실시간 update, 웹툰 감상 후 댓글 작성 화면 등 -> 브라우저 개발자 도구 > network: 200 ok = 잘 감-->

    <pre>
        jQuery에서의 AJAX 통신
        표현법:
        $.ajax({
            속성명 : 속성값,
            속성명 : 속성값,
            속성명 : 속성값,
            ..
        })        
        -> AJAX 기술이 가능하게 하는 객체를 매개변수로 넘긴다고 생각하기 <!--모양이 객체의 생김새와 같음-->

        주요 속성:
        1. url: 요청할 url(필수로 작성) = form 태그의 action 속성
        2. type/method: 요청 전송 방식; get 또는 post; 생략 시 기본 값은 get = form 태그의 method 속성
        3. data: 요청 시 전달 값({key:value, key:value, ..}) = form 태그 안에 input 태그로 입력한 값
        4. success: AJAX 통신 성공 시 실행할 함수를 정의
        5. error: AJAX 통신 실패 시 실행할 함수를 정의
        6. complete: AJAX 통신 성공이든 실패든 무조건 끝나면 실행할 함수를 정의

        부수적인 속성:
        - async : 서버와의 비동기 처리 방식 설정 여부 (기본값 true)
        - contentType : request 의 데이터 인코딩 방식 정의 (보내는 측의 데이터 인코딩)
        - dataType : 서버에서 response 로 오는 데이터의 데이터 형 설정, 값이 없다면 스마트하게 판단함
            xml : 트리 형태의 구조
            json : 맵 형태의 데이터 구조 (일반적인 데이터 구조)
            script : javascript 및 일반 String 형태의 데이터
            html : html 태그 자체를 return 하는 방식
            text : String 데이터
        - accept : 파라미터의 타입을 설정 (사용자 특화 된 파라미터 타입 설정 가능)
        - beforeSend : ajax 요청을 하기 전 실행되는 이벤트 callback 함수(데이터 가공 및 header 관련 설정)
        - cache : 요청 및 결과값을 scope 에서 갖고 있지 않도록 하는 것 (기본값 true)
        - contents : jQuery 에서 response 의 데이터를 파싱하는 방식 정의
        - context : ajax 메소드 내 모든 영역에서 파싱 방식 정의
        - crossDomain : 타 도메인 호출 가능 여부 설정 (기본값 false)
        - dataFilter : response 를 받았을 때 정상적인 값을 return 할 수 있도록 데이터와 데이터 타입 설정
        - global : 기본 이벤트 사용 여부 (ajaxStart, ajaxStop) (버퍼링 같이 시작과 끝을 나타낼 때, 선처리 작업)
        - password : 서버에 접속 권한 (비밀번호) 가 필요한 경우
        - processData : 서버로 보내는 값에 대한 형태 설정 여부 (기본 데이터를 원하는 경우 false 설정)
        - timeout : 서버 요청 시 응답 대기 시간 (milisecond)
    </pre>

    <hr>
    <h1>jQuery 방식을 이용한 AJAX 테스트</h1>
    
    <h3>1. 버튼 클릭 시 get 방식으로 서버에 데이터 전송 및 응답</h3>
    입력: <input type="text" id="input1">
    <button id="btn1">전송</button>
    <br>
    응답: <label id="output1">현재 응답 없음</label>

    <script>
        $(function() {
            $('#btn1').click(function() {
                // locaion.href = "/ajax/jqAjax1.do?text=hi" -> 동기식 통신 vs 비동기식 통신 <- AJAX 사용
                $.ajax({
                    url : "jqAjax1.do", // AJAX에서는 url 맨 앞에 명시적으로 / 안 써도 되고 자동 입력됨; 속성 여러 개 나열하고 싶을 때는 ,(comma) 뒤에 기재
                    data : {input : $('#input1').val()}, // key 값/속성(명) : value 값/속성 값; 데이터가 여러 개일 때에도 ,(comma) 기재하고 나열
                    type : "get", // 기술 안 하면 default = get 방식 전송
                    success : function(result) { // 서버로 요청이 잘 전달되었을 때(status 200) + 서버가 요청을 잘 처리해주었을 때(나의 질문 = 이것까지 포함해야 status 200인가?) -> result = 요청에 대한 응답 데이터가 이 매개변수에 전달됨
                        $('#output1').text(result);
                    },
                    error : function() {
                        alert("AJAX 통신 실패~");
                        // 브라우저 개발자 도구 > network: status 404 = 요청은 성공적으로 됨 + 아직 응답이 없어서 그럼
                    },
                    complete : function() {
                        console.log("성공이든 실패이든 실행");
                    }
                })
            })
        })
    </script>

    <hr>
    <h3>2. 버튼 클릭 시 post 방식으로 서버에 데이터 전송 및 응답</h3>
    이름: <input type="text" id="input2_1"><br>
    나이: <input type="number" id="input2_2"><br>
    <button onclick="test2();">전송</button>
    <br>
    응답: <label id="output2">현재 응답 없음</label>

	<!--2022.1.18(화) 14h-->
    <script>
        function test2() {
            $.ajax({
                url : "jqAjax2.do",
                data : {
                    name : $("#input2_1").val(),
                    age : $("#input2_2").val()
                },
                type : "post",
                success : function(result) {
                    // $("#output2").text(result);
                    
                 	// JSONArray/배열 형태로 넘겼을 때/경우, 가공된 것을 눈에 보이게 하는 것 = view단의 역할 = unboxing(14h50 강사님께서 이게 뭔지 설명해 주시는 것 제대로 못 들음)
                 	// $("#output2").text("이름 : " + result[0] + ", 나이 : " + result[1]);
                 	
                 	// JSONObject/객체 형태로 넘겼을 경우 -> JavaScript에서 객체의 속성 값에 접근하는 2가지 방법 = '객체명.속성명' 또는 '객체명["속성명"]'
                 	$("#output2").text("이름 : " + result.name + ", 나이 : " + result.age);
                    
                    console.log(result, typeof(result)); // ["강해피",4] string vs (2) ['강해피', 4] 'object'(배열) vs {name: '강해피', age: 4} 'object'(객체)
                },
                error : function() {
                    alert("AJAX 통신 실패~");
                }
            })
        }
    </script>

    <!--2022.1.18(화) 15h10 -> 강사님의 질문 = 지금까지 AJAX 통신 시 귀찮은 작업은 무엇인가? -> JSON으로 Java 객체를 JavaScript 객체로 (번거로운 방법으로) 바꾼 뒤, GSON 알려주심-->
    <hr>
    <h3>3. 서버로 데이터 전송 후 조회된 객체를 응답 데이터로 받기</h3>
    회원 번호 입력: <input type="number" id="input3">
    <button onclick="test3();">조회</button>

    <div id="output3">

    </div>

    <!--2022.1.18(화) 16h-->
    <div>----ArrayList로 받기----</div>
    <table id="output4">
        <thead>
            <tr>
                <th>회원 번호</th>
                <th>이름</th>
                <th>나이</th>
                <th>성별</th>
            </tr>            
        </thead>
        <tbody>

        </tbody>
    </table>

    <script>
        function test3() {
            $.ajax({
                url : "jqAjax3.do",
                data : { no : $("#input3").val()},
                success : function(result) {
                    // vo 객체 1개만 보낸 case1
                    /*
                    var resultStr = "회원 번호 : " + result.memberNo + "<br>"
                                  + "이름 : " + result.memberName + "<br>"
                                  + "나이 : " + result.age + "<br>"
                                  + "성별 : " + result.gender;
                    $("#output3").html(resultStr);
                    */

                    // ArrayList로 vo 여러 개 묶어서 보낸 case2 -> 반복문으로 문자열을 연이어서 만들기/누적
                    var resultStr2 = "";

                    for (var i = 0; i < result.length; i++) {
                        resultStr2 += "<tr>"
                                   + "<td>" + result[i].memberNo + "</td>"
                                   + "<td>" + result[i].memberName + "</td>"
                                   + "<td>" + result[i].age + "</td>"
                                   + "<td>" + result[i].gender + "</td>"
                                   + "</tr>";
                    }

                    $("#output4 tbody").html(resultStr2);

                	console.log(result, typeof(result));
                	// Java 객체로 넘긴 경우, Member [memberNo=20, memberName=강토미, age=5, gender=여성] string
                	// JSONObject로 넘긴 경우, {memberNo: 20, gender: '여성', memberName: '강토미', age: 5} 'object'
                	// GSON 객체(-> JSONObject{})로 넘긴 경우, {memberNo: 20, memberName: '강토미', age: 5, gender: '여성'} 'object'
                	// GSON 객체배열(-> JSONArray[])로 넘긴 경우, (3) [{…}, {…}, {…}] 'object'
                },
                error : function() {
                    alert("AJAX 통신 실패~");
                } 
            })
        }
    </script>   
    


</body>
</html>