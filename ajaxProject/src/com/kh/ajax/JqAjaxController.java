package com.kh.ajax;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 2022.1.17(월) 15h
/**
 * Servlet implementation class JqAjaxController
 */
@WebServlet("/jqAjax1.do")
public class JqAjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JqAjaxController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get 방식으로 요청 보냄 -> 단계0) encoding 필요 없음
		
		// 단계1) 값 뽑기 <- request.getParameter()
		String str = request.getParameter("input"); // getParameter()의 반환형 = String
		System.out.println("안녕하세요!"); // System.out.println() -> 표준 출력 도구 -> console창에 출력
		System.out.println("요청 시 전달 값 : " + str); // e.g. input text에 '해피는 해피해요' 입력하고 '전송' 버튼 클릭 시 vs 아무 것도 입력 안 하고 '전송' 시
		System.out.println("입력된 값 : " + str + ", 입력된 값의 길이 : " + str.length()); // '입력된 값 : 해피는 해피해요, 입력된 값의 길이 : 8' vs '입력된 값 : , 입력된 값의 길이 : 0'; length() = 문자열(String) 자료형에 쓸 수 있는 메소드
		// 위 출력 내용이 console에 잘 출력됨 = 나의 요청이 서버로 잘 전달되었음 + 브라우저 개발자 도구 > network: 200 = 잘 전송됨
		
		// 응답
		// step1) 응답 데이터에 한글이 있을 경우를 대비해서, 항상 응답 데이터에 대해 encoding을 설정
		response.setContentType("text/html; charset=UTF-8"); // 'text/html' 부분은 변동 가능하나, 'charset=UTF-8' 부분은 항상 이렇게 쓰기
		// step2) 응답 <- response.getWriter() = jsp와의 통로를 열어줌/stream 생성 = Java 코드 안에 html 코드 넣을 때 사용
		response.getWriter().print("입력된 값 : " + str + ", 입력된 값의 길이 : " + str.length()); // success 속성 값 function의 인자 result로 들어감; 응답 = 나의 요청이 서버에 잘 전달되었음 + 서버가 잘 응답해줌
		// AJAX는 page(x) 데이터(o)로 응답함 -> 이렇게 응답해주는 데이터를 잘 활용해야 함
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
