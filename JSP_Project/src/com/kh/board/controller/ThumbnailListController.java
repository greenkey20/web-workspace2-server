package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Board;

// 2022.1.14(금) 12h20
/**
 * Servlet implementation class ThumbnailListController
 */
@WebServlet("/list.th")
public class ThumbnailListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThumbnailListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**	
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 2022.1.17(월) 9h15
		// 화면 띄우기 전에 테이블로부터 조회해야 함 -> 여러 개의 조회 결과 받음 -> ArrayList에 담음
		ArrayList<Board> list = new BoardService().selectThumbnailList(); // 페이징 처리하려면 현재 페이지 정보를 요청 페이지로부터 받아야 함 vs 여기서는 dummy data가 많지 않고, 내가 업로드한 사진들만 보여줄 것이므, 페이징 처리 생략 -> db에 전달할 값 없음 
		
		// db로부터 조회해온 결과를 request에 담기
		request.setAttribute("list", list);
		
		// views/board/thumbnailListView.jsp 파일 요청 + forwarding -> 응답 view 지정
		request.getRequestDispatcher("views/board/thumbnailListView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
