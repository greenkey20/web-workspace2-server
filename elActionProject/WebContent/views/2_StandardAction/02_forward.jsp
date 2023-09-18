<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>표준 액션 태그 - jsp:forward</title>
</head>
<body>
	<!--2022.2.8(화) 11h15-->
	<!--'아래 코드에 의해 출력되는 내용 = footer.jsp 파일' vs '주소창에 찍히는 url = http://localhost:8003/el/views/2_StandardAction/02_forward.jsp'-->
	<h1>여기는 forwarding page~</h1>
	
	<!--jsp:forward = 화면을 전환시켜주는 태그
		특징 = url은 그대로이고, 화면만 바뀜-->
	<jsp:forward page="footer.jsp" />

</body>
</html>