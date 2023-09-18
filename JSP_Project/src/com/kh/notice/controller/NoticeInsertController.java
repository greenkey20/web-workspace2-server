package com.kh.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

// 2022.1.10(월) 15h
/**
 * Servlet implementation class NoticeInsertController
 */
@WebServlet("/insert.no")
public class NoticeInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// NOTICE 테이블에 INSERT 시 추가적으로 필요한 데이터 = NOTICE_WRITER 컬럼 값 = userNo(요청 페이지에서 input hidden으로 받아옴)
		// post 방식 -> 단계0) encoding
		request.setCharacterEncoding("UTF-8");
		
		// 단계1) 요청 시 전달받은 값을 request 객체로부터 뽑기 -> getParameter() 메소드의 반환형 = String
		String userNo = request.getParameter("userNo"); // 관리자로 로그인해서 공지 글 작성 가능하므로 userNo = 1 -> String 자료형으로 뽑아오므로  "1"
		String noticeTitle = request.getParameter("title");
		String noticeContent = request.getParameter("content");
		
		// 단계2) VO 객체로 가공
		Notice n = new Notice(); // Notice 객체 하나 생성 -> setter()로 값 넣기
		n.setNoticeWriter(userNo);
		n.setNoticeTitle(noticeTitle);
		n.setNoticeContent(noticeContent);
		
		// 단계3) service단으로 toss/처리 요청
		int result = new NoticeService().insertNotice(n);
		
		// 단계4) service단으로부터 전달받은 결과에 따른 응답 페이지 지정
		if (result > 0) { // 성공 시
			request.getSession().setAttribute("alertMsg", "성공적으로 공지사항이 등록되었습니다"); // alert 창 띄움 -> 그 다음에
			response.sendRedirect(request.getContextPath() + "/list.no"); // 공지사항 list(localhost:8001/jsp/list.no)가 보이도록 응답 페이지 넘겨줌
			// 나의 질문 = 강사님, 업데이트된 공지사항 목록이 어떻게 보여지는 것인지 정확히 이해가 안 됩니다 >.<
		} else { // 실패 시 -> error 페이지로 보냄
			request.setAttribute("errorMsg", "공지사항 등록 실패");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
		}
		// 나의 질문 = 강사님, 공지사항 등록 성공 msg는 왜  Session 객체에 담고, 실패 msg는 왜 request 객체에 담나요?
		// 2022.1.10(월) 16h 강사님의 답변 = 성공 msg도 request 객체에 담아도 상관은 없는데, 수업 프로젝트 만들 때는 LoginController에서 (나의 생각 = loginUser 객체를 session에 담으면서) session 객체의 attribute 속성에 담았음 <- session.setAttribute("alertMsg", "성공적으로 로그인하셨습니다");
		// vs 실패 msg는 응답 페이지 1곳에서만 사용하면 되므로, (LoginController에서) request 객체에 담아서 넘김 <- request.setAttribute("errorMsg", "로그인에 실패했습니다");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
