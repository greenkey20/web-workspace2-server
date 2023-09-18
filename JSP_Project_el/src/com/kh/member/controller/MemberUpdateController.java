package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberUpdateController
 */
@WebServlet("/update.me")
public class MemberUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 사용자가 요청하며 전달한 정보는 모두 request 객체 안에 들어있음
		// 단계0) 사용자가 post 방식으로 요청 보냄 -> encoding 설정
		request.setCharacterEncoding("UTF-8");
		
		// 단계1) 사용자가 요청 시 전달한 값을 request(객체의 parameter 영역 안)로부터 뽑기
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String[] interestArr = request.getParameterValues("interest");
		
		String interest = "";
		if (interestArr != null) {
			interest = String.join(",", interestArr);
		}
		
		// 단계2) VO 객체에 담기
		Member m = new Member(userId, userName, phone, email, address, interest);
		
		// 단계3) service단으로 toss/요청 처리
		Member updateMem = new MemberService().updateMember(m);
		// 2022.1.7(금) 12h30 service로부터 dml 처리된 행 수 int 자료형을 반환 받았을 때 문제점 = 회원 정보 변경 후 commit까지 해서 db에도 반영이 되었는데, session 정보가 업데이트 안 되었기 때문에 my page에는 변경 후 정보가 표시되지 않음
		// 2022.1.7(금) 14h service로부터 updateMem 객체를 받아옴 -> session 객체에 담아줌
		
		// 단계4) 처리 결과를 가지고/에 따라 사용자가 보게 될 응답 화면(jsp) 지정
		if (updateMem == null) { // 회원 정보 변경 실패한 경우 = service로부터 반환받은 updateMem이 null -> error page로 응답해줌
			request.setAttribute("errorMsg", "회원 정보 수정에 실패했습니다");
			
			// forward
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			
			// 나의 질문 = 현재 저희 어플리케이션에서 코드에 문제가 없다면, 회원 정보 수정에 실패하는 경우는 어떤 것이 있을까요? -> 나의 생각 = 너무 긴 이름으로 변경 시도 등?
		} else { // 회원 정보 변경 성공 -> 변경된 정보를 반영해서 my page 화면을 (그대로)보여줌
			HttpSession session = request.getSession(); // session 객체 빌려(?)옴
			
			session.setAttribute("loginUser", updateMem); // key 값이 중복되는 경우, 기존 value 값에 덮어쓰기됨
			session.setAttribute("alertMsg", "성공적으로 회원 정보를 수정했습니다");
			
			// my page에서 정보 변경 후 sendRedirect로 넘겨주기 -> localhost:8001/jsp/myPage.me로 다시 전송
			response.sendRedirect(request.getContextPath() + "/myPage.me");
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
