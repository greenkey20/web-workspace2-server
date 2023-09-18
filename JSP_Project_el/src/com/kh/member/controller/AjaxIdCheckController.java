package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.model.service.MemberService;

// 2022.1.17(월) 15h40
/**
 * Servlet implementation class AjaxIdCheckController
 */
@WebServlet("/idCheck.me")
public class AjaxIdCheckController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxIdCheckController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 전송 방식 별도 기재 안 했음 = get 방식으로 요청 보냄 -> 단계0) encoding 필요 없음
		
		// 단계1) request 객체로부터 값 뽑기
		String checkId = request.getParameter("checkId");
		
		// 단계2) db로 전달할 값이 문자열 1개 밖에 없으므로, vo로 가공 필요 없음
		
		// 단계3) service단(MemberService 클래스)으로 toss -> SELECT문을 실행해서 중복 확인을 했지만, 굳이 정보를 모두 담아서 넘길 필요가 없기 때문에, 숫자로 반환 받음
		int count = new MemberService().idCheck(checkId);
		
		// 단계4) 결과에 따른 응답 view 지정 -> 화면이 깜빡(? 16h10 나의 질문 = 맞나? 깜빡 안 하는 것이 AJAX 방식 아닌가?)
		// 형식 및 encoding 먼저 지정
		response.setContentType("text/html; charset=UTF-8");
		
		// AJAX는 결과물만 내보냄 <- response.getWriter().print();
		// count = 1/중복 id가 있을 때, 응답 데이터로 "NNNNN" 반환 vs count = 0/중복 id가 없을 때, 응답 데이터로 "NNNNY" 반환
		if (count > 0) { // 사용자가 입력한 id가 이미 존재하는/있는 경우 -> 응답 데이터 = "NNNNN"
			response.getWriter().print("NNNNN");
		} else { // 사용자가 입력한 id가 존재하지 않는 경우 -> 응답 데이터 = "NNNNY"
			response.getWriter().print("NNNNY");
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
