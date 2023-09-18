package com.kh.board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;

// 2022.1.13(목) 14h15
/**
 * Servlet implementation class BoardDetailController
 */
@WebServlet("/detail.bo")
public class BoardDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 2022.1.13(목) 14h35 자동 생성된 아래 코드 1줄을 안 지우고 실행했을 때, 응답 페이지/게시글 상세 조회 페이지의 모든 한글이 ?로 출력되었음 -> 해결 방법 = 아래 자동 생성된 코드 삭제하고 실행
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// 일반게시판의 게시글 상세 조회(+조회 수 증가) <- db로부터 조회
		// get 방식으로 요청 받음 -> 단계0) encoding 필요 없음
		
		// 단계1) 값 뽑기
		int boardNo = Integer.parseInt(request.getParameter("bno")); // request.getParameter()의 반환형 = String -> parsing해서 int로 자료형 바꿈/사용
		
		// 단계2) 값 1개 밖에 없으므로 가공 필요 없음
		
		// 단계3) service단으로  toss
		BoardService bService = new BoardService(); // 이 controller에서는 쿼리문 3개나 날리는 등 service 클래스 사용할 일이 많음 -> 객체 하나 만들어서 사용
		
		int result = bService.increaseCount(boardNo);
		if (result > 0) { // 조회 수가 성공적으로 증가되었다면 -> BOARD 및 ATTACHMENT 조회 -> 조회되지 않은 경우 b 및 at에는 각각 null이 들어있음
			Board b = bService.selectBoard(boardNo); // BOARD 조회
			Attachment at = bService.selectAttachment(boardNo); // ATTACHMENT 조회
			
			// 조회한 b와 at를 응답 페이지로 넘기기
			request.setAttribute("b", b);
			request.setAttribute("at", at);
			
			// 응답 page "views/board/boardDetailView.jsp"로 forwarding
			request.getRequestDispatcher("views/board/boardDetailView.jsp").forward(request, response);
			// 나의 질문 = 여전히 어떨 때 forward ou sendRedirect하는지 정확히 모르겠음 ㅠ.ㅠ -> 민성님께서 보내주신 links(dispatcher/forwarding 방식 vs redirect 방식 차이) 읽어보자 
			
		} else {  // 조회 수 증가에 실패한 경우 -> error page로 넘김
			request.setAttribute("errorMsg", "일반게시판 게시글의 상세 조회에 실패했습니다");
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp"); // request.getRequestDispatcher() 결과(=RequestDispatcher라는 객체)를 변수에 할당 -> if절에서 1줄로 쓴 것과 다른 점은, 여기서는 2줄이라는 점 뿐; 기능 차이 없음
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
