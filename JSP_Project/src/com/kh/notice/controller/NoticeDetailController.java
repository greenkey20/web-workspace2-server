package com.kh.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

// 2022.1.10(월) 17h5
/**
 * Servlet implementation class NoticeDetailController
 */
@WebServlet("/detail.no")
public class NoticeDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get 방식 -> 단계0) encoding 필요 없음
		
		// 'localhost:8001/jsp/detail.no?nno=글번호'로 요청 옴
		// 단계1) request 객체로부터 값 뽑기
		int noticeNo = Integer.parseInt(request.getParameter("nno")); // getParameter()의 반환형 = String -> NOTICE 테이블에서 글 번호 컬럼 값 number로 정의되어 있으므로, 여기서(vs 아까 __에서는 Dao에서) int 자료형로 변환
		
		// 단계2) vo로 가공 -> 넘길 자료 1개 밖에 없으니까 필요 없음
		
		// 단계3) service단으로 전달1 = 클릭했을 때 공지사항 글 조회 수를 +1 UPDATE
		int result = new NoticeService().increaseCount(noticeNo);
		
		// 단계3) service단으로 전달2 = UPDATE가 성공했다면 상세 조회 요청
		if (result > 0) {
			// 2022.1.11(화) 9h20
			Notice n = new NoticeService().selectNotice(noticeNo);
			// 공지사항 상세 조회 페이지에서만 보여주면 되므로, request(o) session(x) 객체(의 attribute 영역)에 담음
			request.setAttribute("n", n);
			
			// forwarding 시 위와 같이 attribute 영역 "n" key 값 세팅한 request 객체도 함께 넘김 -> db로부터 받아온 데이터를 화면에 표시할 수 있도록
			request.getRequestDispatcher("views/notice/noticeDetailView.jsp").forward(request, response);
			
		} else { // count를 1 증가시키는 데에 실패(e.g. 너무 많은 사용자가 한꺼번에 요청해서 서버가 다 처리하지 못하고 몇몇 사용자들의 요청 session은 time out된 경우) -> 상세 조회할 필요 없음; error page 응답
			request.setAttribute("errorMsg", "공지사항 상세 조회 실패");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
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
