package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 2022.1.5(수) 17h45
/**
 * Servlet implementation class MyPageController
 */
@WebServlet("/myPage.me")
public class MyPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyPageController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 2022.1.7(금) 11h
		// 접속자의 정보가 session에 담기는 바,
		HttpSession session = request.getSession();
		
		if (session.getAttribute("loginUser") == null) { // 로그인 전에는 "loginUser" key 값에 해당하는 value가 null -> 이 경우에는 alert로 경고 
			session.setAttribute("alertMsg", "로그인 후 이용 가능한 서비스입니다");
			// sendRedirect 형식으로 main page("/jsp")로 소환(내가 이해한 것이 맞나? 강사님 설명 제대로 못 들음) 
			response.sendRedirect(request.getContextPath());
		} else { // 로그인 후 "loginUser" key 값에 해당하는 value가 들어있음 -> 이 경우에는 forwarding
			request.getRequestDispatcher("views/member/myPage.jsp").forward(request, response); // 나의 질문 = 강사님, 서블릿에서 포워딩하는 것이 정확히 무엇인지 잘 이해가 안 됩니다 >ㅁ<
		}
		
		// 2022.1.10(월) 9h
		// 웹 통신 = client가 서버에 요청(request) 보내면 응답(response) 보내줌
		// 사용자는 myPage.jsp > 보통 form 태그 안에서, 또는 a 태그를 클릭함으로써 (다른 html 문서를 보여달라는), 요청을 보냄
		// forward = 응답 페이지를 지정해서 보여줌 -> getRequestDispatcher(사용자의 요청에 대해 응답해주는 페이지 지정).forward(사용자가 요청한 내용을 담은 request, 응답하는 내용을 담은 response 객체들을 함께 보냄)
		// sendRedirect = 다시 보내기 -> '응답 페이지는 여기로 가~'라고 client에게 알려줌 = 그게 응답
		// 나의 질문 = 위 if/else문에서 각각 sendRedirect, getRequestDispatcher 방법 바꿔서 써도 되는지? -> 어떨 때에 각각의 방법을 사용하는 것인지?
		
//		request.getRequestDispatcher("views/member/myPage.jsp").forward(request, response); // 나의 질문 = 위 조건문 처리하기 전, 로그아웃 상태에서 localhost:8001/jsp/myPage.me를 요청하면 500 error(java.lang.NullPointerException) 발생하는데, 왜 발생하는지 모르겠습니다 >.<  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
