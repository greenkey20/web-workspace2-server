package com.kh.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;

// 2022.1.11(화) 14h40 실습
// url 매핑값 중복은 Eclipse에서 잡아주지 못함 vs Spring에서는 해결 가능함
/**
 * Servlet implementation class NoticeDeleteController
 */
@WebServlet("/delete.no")
public class NoticeDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get 방식으로 요청 받음 + query string으로부터 값 뽑을 key는 "nno" -> 단계0) encoding 필요 없음
		
		// 단계1)
		int noticeNo = Integer.parseInt(request.getParameter("nno")); // Wrapper 클래스인 Integer 사용
//		System.out.println(noticeNo); // 요청 받은 값이 변수에 잘 담겼는지 console에 출력해서 확인
		
		// 단계2) vo 가공 필요 없음
		
		// 단계3) service단으로 toss
		int result = new NoticeService().deleteNotice(noticeNo);
		
		// 단계4) 결과에 따른 응답 view 지정
		if (result > 0) { // 공지 글 삭제 성공 시 -> alert 띄움 -> 공지사항  list 화면(localhost:8001/jsp/list.no)으로 응답
			request.getSession().setAttribute("alertMsg", "성공적으로 공지사항이 삭제되었습니다");
			
			// 2022.1.12(수) 9h 보충 설명
			// 방법1) forwarding 방식: 응답 페이지를 views/notice/noticeListView.jsp로 위임 + request 객체로부터 뭔가 꺼내서 응답 페이지에서 쓰는데, request 객체에 담아서 보내준 것 없음 -> 응답 페이지 넘겨줄 때 해당 jsp 파일 상단에 선언한 변수 list에 null이 들어있을 것임
//			request.getRequestDispatcher("views/notice/noticeListView.jsp").forward(request, response);
			// list가 비어있음/빈 list != null list -> null인 list를 참조하려고 해서 500 error 발생
			// cf. key 값이 존재하지 않으면 null 값 들어가 있음(제대로 들었나? >.<) 
			
			// 방법2) sendRedirect 방식 = mapping 값으로 호출 -> list를 넘겨줌 -> 문제 안 생김
			response.sendRedirect(request.getContextPath() + "/list.no");
		} else { // 공지 글 삭제 실패 시 -> 처리 생략
			
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
