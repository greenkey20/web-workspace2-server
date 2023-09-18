package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

// 2022.1.5(수) 15h35
/**
 * Servlet implementation class MemberInsertController
 */
@WebServlet("/insert.me")
public class MemberInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// post 방식으로 요청 받음 -> 단계0) encoding 설정 먼저 변경
		request.setCharacterEncoding("UTF-8");
		
		// 단계1) 요청 시 전달받은 값들을 request 객체로부터 get
		String userId = request.getParameter("userId"); // 필수 입력 사항
		String userPwd = request.getParameter("userPwd"); // 필수 입력 사항
		String userName = request.getParameter("userName"); // 필수 입력 사항
		String phone = request.getParameter("phone"); // 빈 문자열이 들어갈 수 있음
		String email = request.getParameter("email"); // 빈 문자열이 들어갈 수 있음
		String address = request.getParameter("address"); // 빈 문자열이 들어갈 수 있음
		String[] interestArr = request.getParameterValues("interest"); // ["운동", "등산", ..] 또는  null
		
		String interest = "";
		if (interestArr != null) { // interestArr의 원소가 1개 이상일 때만 아래와 같이 표시 형식 수정하면 됨
			interest = String.join(",", interestArr); // 운동, 등산, .. 이런 형식의 하나의 문자열로 표시
		}
		
		// 단계2) 매개변수 생성자를 이용해서 Member 객체에 담기
		Member m = new Member(userId, userPwd, userName, phone, email, address, interest);
		
		// 단계3) service단으로 요청 처리 toss -> 처리 결과 받음
		int result = new MemberService().insertMember(m);
		
		// 단계4) 처리 결과를 가지고/에 따라 사용자가 보게 될 응답 화면(jsp) 지정
		if (result > 0) { // 회원 가입/db에 회원 추가 성공 -> url 재요청/sendRedirect 방식으로 jsp 요청
			request.getSession().setAttribute("alertMsg", "회원 가입에 성공했습니다"); // 나의 질문 = '회원 가입 성공' alert 창 뜨는 원리 이해 못 함; redirect에서 어디로 가고 어디로 가는지 명확히 이해 못했는데, 강사님 설명 다시 듣고 이해함 17h30
			response.sendRedirect(request.getContextPath()); // response 객체에는 아무 것도 안 담겨 있음; response 객체의 sendRedirect() 메소드 = 매개변수에 표시된 경로로 응답 페이지를 다시 지정해줌; getContextPath() = server의 context path를 반환해줌
			// index.jsp로 감
		} else { // 회원 가입/db에 회원 추가 실패 -> error page 보여줌
			request.setAttribute("errorMsg", "회원 가입에 실패했습니다");
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			view.forward(request, response);
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
