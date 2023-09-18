<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>명언 보여주기</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
	
	<button type="button" id="wiseword">명언을 보여주세요~</button>
    <div id="list">
        <ul>
            <!-- <li></li>
            <li></li>
            <li></li> -->
        </ul>
    </div>

    <script>
        $(function() {
            $("#wiseword").click(function() {
                
            	$.ajax({
            		url : "wiseword.do",
            		success : function(result) {
            			var wiseword = "";
            			wiseword += "<li>" + result.seq + "번 명언 : " + result.sentence + " by " + result.writer + "</li>";
            			
            			$("#list ul").append(wiseword);
            			
            		},
            		error : function() {
            			console.log("AJAX 통신에 에러가 발생했습니다.")
            		}
            	})
            	
            })
        })

    </script>

</body>
</html>