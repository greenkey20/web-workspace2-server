package com.kh.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Reply;
import com.kh.member.model.vo.Member;

// 2022.1.19(수) 9h20
/**
 * Servlet implementation class AjaxReplyInsertController
 */
@WebServlet("/rinsert.bo")
public class AjaxReplyInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxReplyInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// post 방식으로 요청 -> encoding
		request.setCharacterEncoding("UTF-8"); // 강사님 말씀 = "UTF-8" 소문자로 써도 됨(+대/소문자 섞어서 써도 동작은 할 듯 하지만, 적절한 표기법은 아님)
		
		// request 객체로부터 값 뽑기
		String replyContent = request.getParameter("content");
		int boardNo = Integer.parseInt(request.getParameter("bno"));
		
		// session 객체에 들어있는 로그인한 회원 정보 뽑기
		int userNo = ((Member)request.getSession().getAttribute("loginUser")).getUserNo(); // getAttribute()의 반환형 = Object -> "loginUser"는 Member 형태로 들어가 있는 바, Member 타입으로 강제 형 변환
		
		// 넘길 자료가 3개나 되므로, vo(Reply 객체)로 가공
		Reply r = new Reply();
		r.setReplyContent(replyContent);
		r.setRefBno(boardNo);
		r.setReplyWriter(String.valueOf(userNo)); // REPLY 테이블에는 댓글 작성자로 userNo이 저장되어 있으나, Reply 객체의 댓글 작성자 필드의 자료형은 String -> 방법1) String.valueOf() = API 활용 ou 방법2) userNo에 빈 문자열 "" 더해서 String으로 변환
		
		// service단 호출
		int result = new BoardService().insertReply(r);
		
		// G/JSON은 넘겨야 할 값이 여러 개일 경우, 그 여러 개를 1개로 묶을 때 사용 vs 여기에서는 result 1개만 넘기면 되므로 필요 없음/그냥 넘김
		// cf. 스파게티 코드 = 신기한 것이나 내가 할 수 있는 것 등 이것저것 다 남발한/포함시킨 코드 vs 효율적인, 생산성을 높이는 도구들을 경우에 맞게 적절히 사용하기
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
