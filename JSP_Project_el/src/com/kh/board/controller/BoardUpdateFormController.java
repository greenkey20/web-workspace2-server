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
import com.kh.board.model.vo.Category;

// 2022.1.13(목) 16h10
/**
 * Servlet implementation class BoardUpdateFormController
 */
@WebServlet("/updateForm.bo")
public class BoardUpdateFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardUpdateFormController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Category 테이블로부터 카테고리 전체 조회(-> 응답 페이지(일반게시판 게시글 수정 화면)에 뿌려줘야 함)
		ArrayList<Category> list = new BoardService().selectCategory();
		
		// 해당 글 번호(=요청 시 key 값 "bno"로 넘긴 값) 뽑기
		int boardNo = Integer.parseInt(request.getParameter("bno"));
		
		// Board 객체 b -> 게시글 번호, 카테고리명, 글 제목, 글 내용, 작성자 id, 작성일이 들어있음
		Board b = new BoardService().selectBoard(boardNo);
		
		// Attachment 객체 at -> 파일 번호, 원본 파일명, 수정 파일명, 파일 경로가 들어있음
		Attachment at = new BoardService().selectAttachment(boardNo);
		
		// cf. 클래스 다이어그램 작성 -> 다른 클래스에서 만들어둔 메소드 등을 중복해서 만드는 실수 방지 가능 -> 내가 어디서 무엇을 만들어두었었는지 파악하고 있어야 불필요한 작업 방지 가능
		
		// 값 넘기기
		request.setAttribute("list", list);
		request.setAttribute("b", b);
		request.setAttribute("at", at);
		
		// RequestDispatcher 객체를 이용(+응답 페이지 지정)해서 forwarding
		request.getRequestDispatcher("views/board/boardUpdateForm.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
