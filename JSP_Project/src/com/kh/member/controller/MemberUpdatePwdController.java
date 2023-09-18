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

// 2022.1.7(금) 16h
/**
 * Servlet implementation class MemberUpdatePwdController
 */
@WebServlet("/updatePwd.me")
public class MemberUpdatePwdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdatePwdController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 단계0) post 방식으로 요청 받았으므로, encoding 방식 정리/변경
		request.setCharacterEncoding("UTF-8");
		
		// 단계1) request 객체로부터 (필요한) 값 뽑기
		String userId = request.getParameter("userId"); // 누구의 비밀번호를 변경하고자 하는지 알기 위해 userId 값도 받음
		String userPwd = request.getParameter("userPwd");
		String updatePwd = request.getParameter("updatePwd");
		
		// 단계2) VO 객체에 담아서 가공 vs 여기서는 매개변수 3개 뿐이니까(+관련 생성자도 만들어야 하니까) 굳이 필요없음
		
		// 단계3) service단으로 toss
		Member updateMem = new MemberService().updatePwdMember(userId, userPwd, updatePwd);
		// 2022.1.11(화) 9h 보충 설명: 비밀번호 변경 -> web application/site를 사용하고 있는 나의 상태가 db 뿐만 아니라 site에도 실시간으로 update되어 반영될 수 있도록 Member 객체에 updated된 회원 정보 받아옴 -> Member 객체를 controller로 반환하지 않더라도, commit/rollback 잘 했다면 데이터 수정은 잘 됨 
		// session = 웹 통신이 1번 연결되었을 때 연결 상태(비밀번호 등 접속 정보 포함): session은 통신 시작할 때 받아옴 -> 브라우저를 종료하거나 session에 있는 정보를 지워주는(remove, invalidate 등) 등 session 닫을(?) 때까지
		// 과거에는 session id 공격 등 vs 요즘에는 보안 장비가 잘 되어있음 + Ajax 배운 뒤에 secure coding(나의 web app에서 공격받을 수 있는 점에 대해 대비?) 해 볼 것임
		
		// 단계4) 결과 값을 통해 성공/실패 여부에 따른 응답 화면 지정
		HttpSession session = request.getSession(); // session 객체 자주 사용할 것 같으니까, 미리 접근해둠
		
		if (updateMem == null) { // 비밀번호 변경 실패 -> my page alert
			session.setAttribute("alertMsg", "비밀번호 변경에 실패하였습니다");
		} else { // 비밀번호 변경 성공
			session.setAttribute("alertMsg", "비밀번호 변경에 성공하였습니다");
			session.setAttribute("loginUser", updateMem);
		}
		
		// 성공이든 실패든 myPage.me를 요청 -> localhost:8001/jsp/myPage.me
		response.sendRedirect(request.getContextPath() + "/myPage.me");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
