<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>notice.jsp</title>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	font-family: Arial, "MS Trebuchet", sans-serif;
}

div.container {
	width: 90%;
	margin: 0 auto;
	padding: 2% 2%;
	text-align: center;
}

div.noticeInfo {
	margin: 2% 0;
}

div#title {
	width: 60%;
	margin: 5% 0 0 16%;
	border-top: 1px solid gray;
	padding-top: 1%;
	text-align: left;
	font-weight: bold;
}

div.register {
	display: inline-block;
	height: 100px;
	margin-top: 2%;
}

form .customHeight {
	height: 100px;
}

button.btnOK {
	position: relative;
	top: -50px;
	width: 100px;
	background-color: #4d4dff;
	color: #fff;
	border-style: none;
	cursor: pointer;
}

textarea#commentContents {
	font-size: 12pt;
}

div#viewComments {
	width: 60%;
	margin: 1% 0 0 16%;
	text-align: left;
	max-height: 300px;
	overflow: auto;
}

span.markColor {
	color: #ff0000;
}

div.customDisplay {
	display: inline-block;
	margin: 1% 3% 0 0;
}

div.commentDel {
	margin-bottom: 2%;
	font-size: 8pt;
	font-style: italic;
	cursor: pointer;
}
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {

		func_init();

		$("button.btnOK").click(
			function() {
				// *** (요구사항2) *** //
				// form 태그의 name이 commentFrm 인 모든 입력값들을 받아 변수명 queryString 에 저장시킨다.
				var queryString = $("form[name=commentFrm] textarea").val();
				// console.log(queryString);

				$.ajax({
					// *** (요구사항3) *** //
					// form 태그의 name이 commentFrm 인 모든 입력값들을 POST 방식으로 "commentRegister.do" 로 넘겨 입력처리하도록 한다.
					url : "commentRegister.do",
					data : {
						commentContents : queryString
					},
					type : "post",
					success : function(result) {
						console.log("댓글 입력 AJAX 처리 결과 = " + result);
						
						func_init();
						$("#commentContents").val("").focus();
					},
					error : function(request, status, error) {
						alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
					}
				});
			});

	});

	function func_init() {
		$.ajax({
			// *** (요구사항4) *** //
			// func_init() 의 기능은 사용자가 입력하였던 기존의 모든 댓글내용을 보여주는 것으로서 
			// jQuery를 사용한 Ajax로 처리하도록 하고 결과는 GSON을 이용해서 받는다.
			// URL 주소는 commentList.do 이며 GET 방식이다. 
			// 웹페이지에 보여지는 위치는 div 태그의 id 값이 viewComments 인 곳이다.
			// 만약에 등록된 댓글내용이 없을 경우에는 "데이터가 없습니다." 라고 나타내도록 한다.
			url : "commentList.do",
			type : "post",
			success : function(list) {
				console.log(list);
				
				var result = "";
				
				if (list.length == 0) { // 시험 볼 때 'list.empty()'로 조건 체크하려고 했더니, 브라우저 개발자 도구 console에 이런 함수 없다고 오류 알려줘서, 2022.2.16(수) 20h20 이렇게 수정함
					result = "데이터가 없습니다.";
				} else {
					for (var i in list) {
						result += list[i].commentContents + "<br>"
								+ list[i].writeDate + "<br>";
					}
				}
				
				$("#viewComments").html(result);
			},
			error : function(request, status, error) {
				alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
			}
		});
	}
</script>
</head>
<body>
	<!--2022.2.16(수) 15h5 평가자 checklist-->
	<div class="container">
		<h2>공지사항</h2>
		<div class="noticeInfo">
			맛집투어 워크샵을 하려고 합니다. 아래의 입력란에 맛집내용을 기재하신후 등록버튼을 클릭하여 맛집정보를 보내주시길 바랍니다.
			<div id="title">댓글확인</div>
			<div id="viewComments"></div>
			<form name="commentFrm">
				<div class="register">
					<textarea cols="100" class="customHeight" name="commentContents" id="commentContents"></textarea>
				</div>
				<div class="register">
					<button type="button" class="customHeight btnOK">댓글등록</button>
				</div>
				<input type="text" style="display: none;">
			</form>
		</div>
	</div>
</body>
</html>