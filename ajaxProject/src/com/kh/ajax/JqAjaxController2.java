package com.kh.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

// 2022.1.18(화) 14h15
/**
 * Servlet implementation class JqAjaxController2
 */
@WebServlet("/jqAjax2.do")
public class JqAjaxController2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JqAjaxController2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// post 방식으로 요청 -> encoding
		request.setCharacterEncoding("UTF-8");
		
//		System.out.println("post 방식 AJAX 해보고 있어요~"); // 요청 페이지-서블릿 잘 연결되는지 확인
		
		// 값 뽑기
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		
		// vo 가공 -> service 호출
		
		// 결과에 따른 응답: 우선, 한글이 있을 경우를 대비해서 encoding 설정 필수
//		response.setContentType("text/html; charset=UTF-8");
		
		// 응답 페이지로 넘기기 -> AJAX는 결과를 오로지 1개만 응답할 수 있음 -> 아래와 같은 구문은 사용할 수 없음
//		response.getWriter().print(name, age);
		
		// 1개만 응답하는 방법1) 1개의 문자열(e.g. 이름 : xx, 나이 : x)로 이어서 보냄 -> 각각의 값 꺼내어 쓰기 어려움; 이건 야매 방식
//		String responseData = "이름 : " + name + ", 나이 : " + age;
//		response.getWriter().print(responseData);
		
		// 1개만 응답하는 방법2) AJAX로 실제로 값을 여러 개 보내고 싶을 때 정석 방법 = JSON(JavaScript Object(객체) Notation(표기법), AJAX 통신 시 데이터 전송에 이용되는 format 형식 중 하나) 사용 -> 나의 질문 = format 형식이란 무엇인가?
		// [value, value, value, ..] = JavaScript에서의 배열 객체 <- JSONArray(14h25 내용의 논리 흐름 제대로 못 듣고 필기만 씀)
		// {key:value, key:value, ..} = JavaScript에서의 일반 객체 <- JSONObject
		// 단, Java와 JavaScript(JavaScript의 표기법인 JSON 포함)는 상관 관계가 없는 바, Java에서 기본적으로 제공x + library(.jar)가 필요함 <- "https://code.google.com/archive/p/json-simple/downloads 접속 -> json-simple-1.1.1.jar download -> WEB-INF > lib에 붙여넣기"
		// JSON 처리 시 사용되는 클래스 종류
		// 1. JSONArray[값1, 값2, ..] -> 배열 형태로 넘기기 가능
		// 2. JSONObject{key1:value1, key2:value2, ..} -> 객체 형태로 넘기기 가능
		
		/*
		JSONArray jArr = new JSONArray(); // 라이브러리를 통해 제공된 JSONArray 클래스를 이용해서 빈 JavaScript 배열 []이 만들어짐
		// add() -> 배열에 값 담기
		jArr.add(name); // jArr = ["강해피"]
		jArr.add(age); // jArr = ["강해피", 4]
		
		// 단계0 = 전달하고자 하는 자료의 용도가 달라질 수 있는 바, encoding은 항상 해준다고 생각하기
//		response.setContentType("text/html; charset=UTF-8"); // html 형식의 문자열(text)로 넘기겠다고 encoding 지정했기 때문에, JavaScript 객체를 만들었지만 문자열로 넘어감 -> 문자열(브라우저 console에서 검은색 글씨로 표시됨)로써 ["강해피",4]가 넘어감
		response.setContentType("application/json; charset=UTF-8"); // vs application = JSON 타입의 값 <- 응답할 데이터의 content type을 제대로 지정해야 문자열 형식으로 넘어가지 않음 
		
		// 배열을 응답 페이지로 보내기
		response.getWriter().print(jArr);
		*/
		// 강사님 말씀+필기 속도 너무 빨라서 이 윗 부분 필기(+설명 듣기) 제대로 못 함 ㅠ.ㅠ
		
		// 2022.1.18(화) 15h
		// JSONObject로도 넘기기 가능
		JSONObject jObj = new JSONObject(); // 빈 JavaScript 객체 {}가 만들어짐
		// put() -> 객체에 값 담기
		jObj.put("name", name); // {name:"강해피"}
		jObj.put("age", age); // {name:"강해피", age:4}
		
		// JSON 타입의 값이라고 content type 및 encoding 지정
		response.setContentType("application/json; charset=UTF-8");
		
		// 객체를 응답 페이지로 보내기
		response.getWriter().print(jObj);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
