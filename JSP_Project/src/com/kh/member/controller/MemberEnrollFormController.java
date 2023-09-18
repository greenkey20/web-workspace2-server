package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 2022.1.5(수) 15h5
/**
 * Servlet implementation class MemberEnrollFormController
 */
@WebServlet("/enrollForm.me")
public class MemberEnrollFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberEnrollFormController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 이 Servlet은 회원 가입 form만 띄워줄 것임
		
		// forwarding 하는 방법 = RequestDispatcher 객체 이용
		// 방법1)
//		RequestDispatcher view = request.getRequestDispatcher("views/member/memberEnrollForm.jsp");
//		view.forward(request, response);
		// 방법2) 1줄로 쓰기
		request.getRequestDispatcher("views/member/memberEnrollForm.jsp").forward(request, response);
		
		// Servlet이 응답 페이지를 보내주는 바, 요청을 보냈을 때 404 error page가 뜨면, files명 오타 없는지 확인 + 제대로 된 경로를 내가 forwarding 했는지 확인 + Servlet url mapping 값(요청 보내는 곳 및 Servlet에서 받는 곳) 확인
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
