package com.kh.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

// 2022.1.11(화) 12h5
/**
 * Servlet implementation class NoticeUpdateController
 */
@WebServlet("/update.no")
public class NoticeUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// post 방식의 요청 받음 -> 단계0) encoding
		request.setCharacterEncoding("UTF-8");
		
		// 단계1) 값 뽑기; getParameter()의 반환형 = String
		int noticeNo = Integer.parseInt(request.getParameter("nno"));
		String noticeTitle = request.getParameter("title");
		String noticeContent = request.getParameter("content");
		
		// 단계2) VO로 가공
		Notice n = new Notice();
		n.setNoticeNo(noticeNo);
		n.setNoticeTitle(noticeTitle);
		n.setNoticeContent(noticeContent);
		
		// 단계3) service단으로 처리 요청
		int result = new NoticeService().updateNotice(n);
		
		// 단계4) 결과 값에 따라 응답 지정
		if (result > 0) { // 공지 글 수정 성공 -> alert 띄우기 -> 해당 글 상세 보기 페이지(http://localhost:8001/jsp/detail.no?nno=해당 공지 글 번호)로 응답 view 지정
			request.getSession().setAttribute("alertMsg", "성공적으로 공지 글 수정이 되었습니다");
			response.sendRedirect(request.getContextPath() + "/detail.no?nno=" + noticeNo);
			
			// cf. sendRedirect: contextPath(request.getContextPath())로 시작하는 url을 응답해야 할 때 vs forwarding: 구체적으로 directory 경로를 제시해서 응답해야 할 때(-> directory 구조 노출 방지 가능)
		} else { // 공지 글 수정 실패 -> error page 띄우기; 단, 실패할 일 없을 것 같으므로, 생략
			
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
