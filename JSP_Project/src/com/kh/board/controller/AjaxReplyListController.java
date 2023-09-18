package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Reply;

// 2022.1.18(화) 17h
// AJAX로 받은 요청 Servlet의 이름 맨 앞에는 Ajax 붙여주기 
/**
 * Servlet implementation class AjaxReplyListController
 */
@WebServlet("/rlist.do")
public class AjaxReplyListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxReplyListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get 방식으로 요청 받음 -> encoding 필요 없음
		
		// 값 뽑기
		int boardNo = Integer.parseInt(request.getParameter("bno")); // getParameter()의 반환형 = String -> parsing 필요
		
		// 전달할 값이 정수 1개 뿐이므로 vo 가공 필요 없음
		
		// 게시판 관련 기능을 처리하는 service단으로 toss
		ArrayList<Reply> list = new BoardService().selectReplyList(boardNo);
		
		// GSON 이용 -> db에서 받아온 ArrayList(Java 객체)를 JavaScript 배열 형태로 변환해서 응답해줌
		// 단계0a = WEB-INF > lib에 JSON, GSON libraries를 drag해서 넣기
		// 단계0b = 형식 및 encoding 지정
		response.setContentType("application/json; charset=UTF-8");
		new Gson().toJson(list, response.getWriter());
		
		// 17h45 나의 질문 = 강사님, 서버 멈춤하고(빨간 네모 눌렀다가) 시작하는 것과, 그냥 초록 세모만 눌러서 restart 하는 것과의 차이가 있나요? -> 강사님 답변 = 차이 없음
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
