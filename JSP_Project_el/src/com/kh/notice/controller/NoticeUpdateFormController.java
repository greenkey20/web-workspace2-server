package com.kh.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

// 2022.1.11(화) 11h15
/**
 * Servlet implementation class NoticeUpdateFormController
 */
@WebServlet("/updateForm.no")
public class NoticeUpdateFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateFormController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// a 태그를 통해, 'default = get 방식'으로 요청 받음 -> 단계0) 필요 없음
		
		// 단계1) 요청 시 전달받은 값을 request 객체로부터 뽑기 -> getParameter() 메소드의 반환형 = String -> NOTICE 테이블의 NOTICE_NO 컬럼 값이 number형으로 저장되어 있는 바, 추후 sql문에서 숫자끼리 비교하기 위해 형 변환해둠
		int noticeNo = Integer.parseInt(request.getParameter("nno"));
		
		// 뽑은 값 1개 밖에 없으니까 단계2) VO 가공 필요 없음
		
		// 단계3) service단으로 toss; 공지사항 상세 조회 시 필요(해서 생성)했던 selectNotice() 메소드 재활용 가능 -> selectNotice() 메소드 호출만 하면 됨
		Notice n = new NoticeService().selectNotice(noticeNo);
		// n에 글 번호, 글 제목, 글 내용, 작성자 ID, 작성일 담겨있음
		
		// 단계4) forwarding -> 응답 view 지정
		request.setAttribute("n", n);
		request.getRequestDispatcher("views/notice/noticeUpdateForm.jsp").forward(request, response);
		
		// cf. forwarding: 구체적으로 directory 경로를 제시해서 응답해야 할 때(-> directory 구조 노출 방지 가능(나의 질문 = 내용이 맞나? 구조가 노출되는 것 아닌가?)) vs sendRedirect: contextPath(request.getContextPath())로 시작하는 url을 응답해야 할 때
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
