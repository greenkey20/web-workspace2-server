package com.kh.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 2022.1.14(금) 15h
/**
 * Servlet implementation class ThumbnailEnrollFormController
 */
@WebServlet("/enrollForm.th")
public class ThumbnailEnrollFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThumbnailEnrollFormController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 이 Servlet의 역할 = 사진게시판 글 작성 form(views/board/thumbnailEnrollForm.jsp) 띄워줌
		// 2022.1.15(토) 14h55 나의 질문 = thumbnailListView.jsp로부터 <a href="<%= contextPath %>/enrollForm.th">와 같이 받은 요청을 이 Servlet에서 처리하고 있는데, a href 속성 값을 localhost:8001/jsp/views/board/thumbnailEnrollForm.jsp로 주어 링크 거는 것과 차이가 무엇이지?
		request.getRequestDispatcher("views/board/thumbnailEnrollForm.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
