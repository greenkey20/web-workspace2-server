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
import com.kh.common.model.vo.PageInfo;

// 2022.1.11(화) 16h30
/**
 * Servlet implementation class BoardListController
 */
@WebServlet("/list.bo")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// query string(/jsp/list.bo?currentPage=1)/get 방식으로 요청 받음 -> 단계0) encoding 필요 없음
		
		// 단계1) request 객체로부터 필요한 값 뽑기
		// paging 처리 = 사용자에게 보여줄 page를 정함/만듦 -> 1페이지에 보여줄 글 수만큼 보여주기 + 의도한만큼의 버튼 생성
		// 게시판에 글 나열 시 가장 먼저 필요한 정보 = 사용자에게 보여줄, 현재 이 게시판의 글의 총 개수 + 사용자가 요청한 페이지가 어디인가 + 한 페이지에 몇 개의 글을 보여줄 것인가 + 시작/끝(이전/다음 버튼) -> 마지막 페이지 결정 가능 + paging 버튼이 동적으로 만들어짐
		// 필요한 변수들 -> // 2022.1.12(수) 10h15 아래 변수 list 복사해서 PageInfo vo 생성
		int listCount; // 현재 일반게시판의 게시글 총 개수 <- 그룹 함수 중 하나인 COUNT(*) 활용해서 BOARD 테이블로부터 조회
		int currentPage; // 사용자가 요청한/현재 페이지 -> 아주 특별한 경우가 아니라면/사용자가 즐겨찾기를 해둔 경우가 아니라면, 보통 가장 최신(글이 보여지는)/1번 페이지 요청
		int pageLimit; // 페이지 하단에 보여질/표시되는 paging bar/buttons의 최대 개수 -> 10개(계산 편리)로 임의 지정
		int boardLimit; // 한 페이지에 보여질 게시글의 최대 개수 -> 10개로 임의 지정
		
		// paging bar/buttons 관련 
		int maxPage; // 가장 마지막 페이지가 몇 번 페이지인지 = 페이지의 총 개수
		int startPage; // 페이지 하단에 보여질 첫번째 paging bar
		int endPage; // 페이지 하단에 표시되는 마지막 paging bar
		
		// DB에 가서 listCount(총 게시글 개수) 알아오기 + boardLimit -> 마지막 페이지 결정
		listCount = new BoardService().selectListCount();
		
		// currentPage(사용자가 query string으로 요청한/현재 페이지)
		currentPage = Integer.parseInt(request.getParameter("currentPage")); // getParameter() 메소드의 반환형 = String -> 나는 이 자료를 숫자로 사용하고 싶기 때문에 int형으로 parsing
		
		// pageLimit(paging bar의 최대 개수)
		pageLimit = 10;
		
		// boardLimit(한 페이지에 보여질 게시글의 최대 개수)
		boardLimit = 10;
		
		// console에 출력해서 중간 확인
//		System.out.println(listCount); // 107
//		System.out.println(currentPage); // 1
		
		// maxPage = 가장 마지막 페이지가 몇 번 페이지인지 = 페이지의 총 개수 <- listCount와 boardLimit의 영향을 받음
		/* e.g. boardLimit = 10이라고 가정하고, 규칙 구해보기
		 * 게시글의 총 개수(listCount)	boardLimit(10개라고 가정)	listCount/boardLimit(나눗셈 결과)	maxPage(마지막 페이지)	
		 * 100개						10						10.0							10
		 * 101개						10						10.1							11
		 * 105개						10						10.5							11
		 * 109개						10						10.9							11
		 * 110개						10						11.0							11
		 * 111개						10						11.1							12
		 * ..			
		 * -> 규칙: maxPage = 나눗셈 결과(listCount/boardLimit)를 올림 처리
		 * 
		 * 처리 단계
		 * 1) listCount를 double형으로 형 변환 -> 피연산자 중 하나만 double이면 연산 결과는 double로 나옴
		 * 2) listCount/boardLimit 계산
		 * 3) Math.ceil() 메소드를 활용해서 나눗셈 결과를 올림 처리
		 * 4) 결국 정수형 자료가 필요하므로, 결과 값을 int로 형 변환
		 */
		maxPage = (int)Math.ceil((double)listCount / boardLimit);
//		System.out.println(maxPage); // 11
		
		// startPage(페이지 하단에 보여질 paging bar의 시작 수) <- pageLimit과 currentPage의 영향을 받음
		// 2022.1.12(수) 9h35
		/* e.g. pageLimit = 10이라고 가정하고, startPage 1, 11, 21, 31, 41..(등차수열)의 규칙 구해보기 = n * 10 + 1
		 * 		pageLimit = 5라면, startPage 1, 6, 11, 16, 21..(등차수열)의 규칙 = n * 5 + 1
		 * 		..
		 * 즉, startPage = n * pageLimit + 1
		 * 나의 idea for startPage = [(currentPage / page limit)의 ceiling 정수  - 1] * pageLimit + 1 -> Java에서 정수끼리의 나눗셈의 결과는 정수(o) 실수(x)인 점에 착안하면, 더 간단하게 표현 가능함
		 * 
		 * 강사님의 알고리즘
		 * currentPage	startPage
		 * 1~10			1
		 * 11~20		11
		 * ..
		 * -> currentPage가 1~10일 때, n * 10 + 1 = 1 -> n = 0 -> 1~10 / 10 = 0 또는 1
		 * 	  currentPage가 11~20일 때, n * 10 + 1 = 11 -> n = 1 -> 11~20 / 10 = 1 또는 2
		 * 	  currentPage가 21~30일 때, n * 10 + 1 = 21 -> n = 2 -> 21~30 / 10 = 2 또는 3
		 * 	  ..
		 * -> n을 구해보면 n = (currentPage - 1) / pageLimit
		 * 	  (currentPage 1~10 - 1) / 10 = 0
		 * 	  (currentPage 11~20 - 1) / 10 = 1
		 * 	  (currentPage 21~30 - 1) / 10 = 2
		 * 	  ..
		 * -> startPage = (currentPage - 1) / pageLimit * pageLimit + 1
		 */
		startPage = (currentPage - 1) / pageLimit * pageLimit + 1; // 나의 질문 = (currentPage - 1) / pageLimit을 floor 정수로 바꾸는 과정 필요 없을까요? e.g. currentPage = 3일 때 startPage = 3? -> 강사님의 답변 = 정수끼리의 연산으로 생각하면, 강사님께서 말씀해주신 알고리즘 맞음
		
		// endPage(페이지 하단에 보여질 paging bar의 끝 수) <- startPage와 pageLimit의 영향을 받음
		/* e.g. pageLimit = 10이라고 가정하고, endPage 10, 20, 30..(등차수열)의 규칙 구해보기
		 * startPage	endPage
		 * 1			10
		 * 11			20
		 * 21			30
		 * ..
		 * -> 규칙: endPage = startPage + pageLimit - 1
		 * + 선택적으로(if문/조건문) 마지막 paging bar에 대해서는 maxPage까지만 보이게끔 하기
		 */
		endPage = startPage + pageLimit - 1;
		// startPage가 11이라서 endPage가 20이 되어야 하는데, maxPage가 11이라면/총 11페이지까지 밖에 없다면, endPage를 maxPage로 변경
		if (endPage > maxPage) {
			endPage = maxPage;
		}
		
		// 여기까지 총 7개의 paging 처리 관련 변수 할당 -> 7개의 변수를 가지고, (currentPage별)해당되는 범위(startRow~endRow)에 따른 게시글 10개(boardLimit)씩만 SELECT하고자 함 -> service단으로 toss/처리 요청
		// paging 처리 관련 7개의 변수들을 일일이 넘기기는 번거로움 vs 잘 정리해서 service단으로 toss하기/넘기기 위해 com.kh.common.model.vo.PageInfo 클래스/객체 생성 -> 단계2) vo로 가공
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
		
		// 단계3) service단으로 toss/처리 요청
		ArrayList<Board> list = new BoardService().selectList(pi);
		
		// 단계4) list와 pi 넘기며, 응답 view 지정
		request.setAttribute("list", list);
		request.setAttribute("pi", pi);
		
		// views/board/boardListView.jsp로 forwarding -> 일반게시판의 게시글 목록이 보이는 페이지로 응답해줌
		request.getRequestDispatcher("views/board/boardListView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
