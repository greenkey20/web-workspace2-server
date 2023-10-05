<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사진게시판 작성</title>
<style>
	/* boardEnrollForm.jsp의 style 복사+붙여넣기*/
	.outer {
        background-color: seagreen;
        color: mediumspringgreen;
        width: 1000px;
		height: 800px;
        margin: auto;
        margin-top: 50px;
    }

    #enroll-form>table {border: 1px solid mediumspringgreen;}
    
    #enroll-form input, #enroll-form textarea {
        width: 100%;
        box-sizing: border-box;
    }
</style>
</head>
<body>
	<!--2022.1.14(금) 12h30-->
	<%@ include file = "../common/menubar.jsp" %>
	<!--http://localhost:8001/jsp/views/board/thumbnailEnrollForm.jsp 띄워놓고 VS Code 가서 작업-->

	<div class="outer">
		<br>
		<h2 align="center">사진게시판 작성</h2>
		<br>

		<!--15h 파일 첨부하는 요청을 하는 경우에는 form 태그 내에 꼭/반드시 enctype(속성)="multipart/form-data" 포함시켜야 함 -> request 객체에 자료 넘길 때 multipart라는 형식으로 변환해서 보냄; 부호화 방식을 지정하는 것임
			+ 사용자들이 첨부하는 파일들을 저장할 (내 hard disc 내)경로 미리 지정 -> WebContent > resources > thumbnail_upfiles-->
		<form action="<%= contextPath %>/insert.th" id="enroll-form" method="post" enctype="multipart/form-data">
			
			<input type="hidden" name="userNo" value="<%= loginUser.getUserNo() %>">
			 
			<table align="center" border="1">
				<!--(tr>th+td*3)*4-->
				<tr>
					<th width="100">제목</th>
					<td colspan="3"><input type="text" name="title" required></td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan="3">
						<textarea name="content" style="resize: none;" rows="5"></textarea>
					</td>
				</tr>
				<tr>
					<th>대표 이미지</th> <!--반드시 첨부해야 함-->
					<td colspan="3" align="center">
						<img id="titleImg" width="450" height="170"> <!--강사님께서는  src="" alt="" 속성(VS code에서 img 태그 자동생성 시 뜸)들 삭제하셨음-->
					</td>
				</tr>
				<tr>
					<th>상세 이미지</th> <!--선택적으로 첨부 가능-->
					<td><img id="contentImg1" width="150" height="120"></td>
					<td><img id="contentImg2" width="150" height="120"></td>
					<td><img id="contentImg3" width="150" height="120"></td>
				</tr>
			</table>

			<div id="file-area">
				<input type="file" id="file1" onchange="loadImg(this, 1);" name="file1" required>
				<input type="file" id="file2" onchange="loadImg(this, 2);" name="file2">
				<input type="file" id="file3" onchange="loadImg(this, 3);" name="file3">
				<input type="file" id="file4" onchange="loadImg(this, 4);" name="file4">
				<!--this = 요소객체 자기 자신
					onchange 속성 = input 태그의 내용물이 변경되었을 때 발생하는 이벤트 = loadImg() 함수(JavaScript 영역/script 태그 내에 내가 만들 함수) 호출-->
			</div>

			<script>
				$(function() { // 문서가 다 읽어지면
					$('#file-area').hide(); // 해당 요소가 보이지 않게 함

					$('#titleImg').click(function() {
						$('#file1').click();
					})

					$('#contentImg1').click(function() {
						$('#file2').click();
					})

					$('#contentImg2').click(function() {
						$('#file3').click();
					})

					$('#contentImg3').click(function() {
						$('#file4').click();
					})						
				})

				function loadImg(inputFile, num) {
					// inputFile = 현재 변화가 생긴 <input type="file"> 요소객체(자기 자신)
					// num = 몇번째 input 요소인지 확인 후 해당 영역에 미리보기 하기 위한 (매개)변수
					// console.log(inputFile.files.length); // files 속성 = 업로드된 파일의 정보들을 배열 형식으로 여러 개 묶어서 반환; length = 배열의 크기; 파일 선택 시 1 vs 파일 선택 취소 시 0이 찍힘 -> 파일의 선택/존재 여부를 알 수 있음 + inputFile.files[0]에 선택된 파일이 담겨있음
					
					// files 업로드 관련해서 강사님께서는 JavaScript 공식 문서에서 배우심 -> 공식 문서 중요 e.g. 몇 년 전 JavaScript 라이브러리(?) "react" 공식 문서(영어 원문)를 한글로 잘 정리하고 예제 잘 만들어서 + 막 수요가 생길 때 강의한 사람, 큰 돈을 벌었음

					if (inputFile.files.length == 1) { // 파일이 있는 경우 -> 선택된 파일을 읽어들여서 그 영역에 맞는 곳에 미리보기
						// 단계1) 파일을 읽어들일 FileReader 객체 생성
						var reader = new FileReader();

						// 단계2) FileReader 객체로부터 파일을 읽어들이는 메소드 호출 + 어느 파일을 읽어들일 것인지 인자로 넣어줌 -> 해당 파일을 읽어들이는 순간, 읽어들인 파일만의 고유한 url이 부여됨
						reader.readAsDataURL(inputFile.files[0]);

						// 단계3) 해당 url을 src 속성으로 부여(attr)
						reader.onload = function(e) { // 파일 읽기가 완료되었을 때 실행할 함수 정의
							// 매개변수 e의 target = e.target = 이벤트(나의 질문 = 어떤 이벤트? onchange? -> 2022.1.16(일) 20h50 나의 생각 = onload 이벤트? 그래도 잘 모르겠음 ㅠ.ㅠ)를 당한 객체 = this
							// 단계2에서 생성된 각 파일의 url이 e의 target.result(e.target.result)에 담김
							// console.log(e); // 2022.1.17(월) 오전에 이렇게 찍어보려고  했는데, console에 찍히는 게 없었다 - 왜일까? >.< -> 2022.1.17(월) 17h30 강사님께 slack으로 'e가 무엇을 지칭하는지?' 질문 드림 -> 강사님 답변 = input type file(inputFile.files[0]에 파일이 있는지 확인해야 하는 바..)
							// 각 영역에 맞춰서 이미지 미리보기
							switch (num) {
								case 1 : $("#titleImg").attr("src", e.target.result); break;
								case 2 : $("#contentImg1").attr("src", e.target.result); break;
								case 3 : $("#contentImg2").attr("src", e.target.result); break;
								case 4 : $("#contentImg3").attr("src", e.target.result); break;
							}
						}
					} // '첨부 파일이 있는 경우' if문 영역 끝
					else { // 파일이 없는 경우 -> 미리보기 사라지게 하기 = src 속성의 값으로 null을 담아 사라지게 함
						switch (num) {
							case 1 : $("#titleImg").attr("src", null); break;
							case 2 : $("#contentImg1").attr("src", null); break;
							case 3 : $("#contentImg2").attr("src", null); break;
							case 4 : $("#contentImg3").attr("src", null); break;
						}
					} // '첨부 파일이 없는 경우' else문 영역 끝
				} // loadImg() 영역 끝
			</script>

			<div align="center">
				<button type="submit" class="btn btn-sm tbn-secondary">등록하기</button> <!--강사님께서 스타일 같은 것? 수정하신 것 같은데 제대로 못 봄 15h -> 확인 완료-->
			</div>

		</form>
	</div>
</body>
</html>