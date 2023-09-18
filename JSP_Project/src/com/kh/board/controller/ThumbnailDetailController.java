package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;

// 2022.1.17(월) 11h20
/**
 * Servlet implementation class ThumbnailDetailController
 */
@WebServlet("/detail.th")
public class ThumbnailDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThumbnailDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get 방식으로 요청 -> 단계0) encoding 필요 없음
		
		// 단계1) 글 번호 뽑아오기
		int boardNo = Integer.parseInt(request.getParameter("bno")); // getParameter()의 반환형 = String
		
		// 단계2) 게시글 상세 조회
		// 단계2a) 조회 수 증가하는 쿼리문 요청
		int result = new BoardService().increaseCount(boardNo);
		
		// 단계2b) 단계2a/조회 수 증가가 성공했을 경우 -> BOARD 테이블 및 ATTACHMENT 테이블로부터 조회 요청
		if (result > 0) {
			// BOARD 테이블에서 조회 요청 시, 기존에 만들어둔 selectBoard() 메소드의 문제점 = 일반게시판 게시글은 카테고리가 있어서 (inner)join을 했을 때 카테고리가 null인 게시글이 존재하지 않음 vs 사진게시판 게시글의 카테고리는 모두 null이기 때문에 inner join을 했을 경우, 기존 SELECT문으로 하나도 조회되지 않음
			// 나의 의견 = Left join(왼쪽 테이블 기준 outer join) 해야 해요, category_name이 없는 행을 조회하려고 해서..
			// 해결 방법 = CATEGORY 컬럼 기준으로 일치하는+일치하지 않는 행을 모두 가져오려면, 기존 쿼리문의 inner를 outer(+왼쪽 테이블 기준으로) join으로 수정해야 함 -> 기존에 만들어둔 selectBoard() 메소드를 호출/재활용 가능
			Board b = new BoardService().selectBoard(boardNo);
			
			//  ATTACHMENT 테이블로부터 조회 요청 -> ArrayList<Attachment>에 조회 결과를 담음
			ArrayList<Attachment> list = new BoardService().selectAttachmentList(boardNo);

			// 요청 결과를 request 객체에 담기
			// 주말에 학우님의 질문 = sendRedirect 방식으로 응답 시 왜 request 객체를 사용할 수 없는가? -> 11h45 강사님 설명(들으면서 제대로 필기 못함) = request 객체는 응답/요청 페이지 1세트에서만 사용 가능 + request 객체를 함께 보내어 forwarding한 페이지를 응답해줌 vs sendRedirect는 응답을 받으려면 어딘가로 가라고 지시(? -> redirect된 페이지에서 요청 페이지?(정확히 이해가 안 됨 ㅠ.ㅠ); 응답을 받기 위해서 사용자는 1번 더 요청해야 함) 
			request.setAttribute("b", b);
			request.setAttribute("list", list);
			
			// 응답 view(views/board/thumbnailDetailView.jsp) 지정
			request.getRequestDispatcher("views/board/thumbnailDetailView.jsp").forward(request, response);
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
