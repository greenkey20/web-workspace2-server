package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 2022.1.5(수) 12h45

/**
 * Servlet implementation class LogoutController
 */
@WebServlet("/logout.me")
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그아웃 요청에 따라 처리해 주고자 함 = session을 만료시킴/무효화함
		// 무효화 메소드 invalidate() = session에서 제공하는 메소드 -> 나의 질문 = session.removeAttribute()과의 (기능 등)차이점은 무엇인가?
		// 방법1)
//		HttpSession session = request.getSession();
//		session.invalidate();
		// 방법2) 변수에 담을 필요 없이 1줄로 작성
		request.getSession().invalidate();
		
		// sendRedirect 방식으로 응답 페이지 (경로) 지정/요청
		// localhost:8001/jsp -> index.jsp가 보여짐
		// 방법1) hard coding -> context path/root 변경 시 유지/보수 어려움
//		response.sendRedirect("/jsp");
		// 방법2) 앞으로는 응답 페이지 요청 시 contextPath/Root를 직접 작성x -> request.getContextPath() 메소드 호출/사용 -> contextPath/Root가 나옴
		response.sendRedirect(request.getContextPath());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
