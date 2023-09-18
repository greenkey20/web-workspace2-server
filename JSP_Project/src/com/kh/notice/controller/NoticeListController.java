package com.kh.notice.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

// 2022.1.10(월) 11h45
/**
 * Servlet implementation class NoticeListController
 */
@WebServlet("/list.no")
public class NoticeListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 화면을 띄우기 전에 notice 테이블로부터 들어있는 값을 뽑아서 응답 페이지에 전달
		
		// get 방식으로 요청 받음 -> 단계0) 필요 없음
		// 요청 받을 때 전달받은 데이터 없으므로, 단계1) request 객체로부터 (필요한) 값 뽑기 필요 없음 -> 단계2) VO 객체에 담아서 가공도 필요 없음
		// 단계3) service단에 toss/SELECT 요청 -> NOTICE(공지사항) 테이블에 있는 전체 행 반환; 반환받을 행의 개수 = 0~여러 개 -> generic 사용해서 Notice 객체만 담을 수 있는 ArrayList에 담음 
		ArrayList<Notice> list = new NoticeService().selectNoticeList();
		
		// 단계4) 뽑아온 list를 어딘가에/어떤 객체에 담아서 응답 페이지로 보내기 -> 응답 페이지에서만 사용할 예정이므로 request의 attribute 영역에 담음
		request.setAttribute("list", list);
		
		// 응답 화면 띄우기 <- request 객체의 dispatcher로 경로 위임 = 이 서블릿의 결론 <- request.getRequestDispatcher("path");
		request.getRequestDispatcher("views/notice/noticeListView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
