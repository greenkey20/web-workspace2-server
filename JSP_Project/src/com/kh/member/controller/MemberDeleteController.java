package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

//2022.1.7(금) 17h 수업 실습 + 주말 숙제
/**
 * Servlet implementation class MemberDeleteController
 */
@WebServlet("/delete.me")
public class MemberDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 단계0) post 방식으로 전송 받으므로(-> 괴상망칙한 형태로 들어있을 수 있음) UTF-8 형식으로 뽑아쓸 수 있도록 encoding
		request.setCharacterEncoding("UTF-8");
		
		// 단계1) request 객체에 받아온, 사용자가 요청과 함께 넘긴, 값 뽑기
		String userPwd = request.getParameter("userPwd");
		
		// 현재 로그인한 회원(의 정보)을 특정하기 위해
		// 방법1) input type="hidden"으로 애초에 숨겨서 요청 시 전달을 함
//		String userId = request.getParameter("userId"); // 2022.1.7(금) 실습 시 내가 한 방법
		// 2022.1.10(월) 10h
		// 방법2) session(의 attribute) 영역에 담겨있는 회원 객체를 뽑음 -> session에 담겨있는, 기존의 로그인된 사용자의 정보를 얻어옴
		HttpSession session = request.getSession();
		String userId = ((Member)session.getAttribute("loginUser")).getUserId();
		
		// 단계2) VO 객체에 담아서 가공 vs 여기서는 매개변수 2개 뿐이니까 필요 없을 듯함
		
		// 단계3) service단으로 toss/처리 요청
		int result = new MemberService().deleteMember(userId, userPwd);
		// 나는 Member deleteMem를 반환받는 방식으로 했었음 
		
		// 단계4) service단으로부터 받아온 결과(성공/실패)에 따른 응답 화면 지정
//		HttpSession session = request.getSession();
		
		if (result > 0) { // 회원 탈퇴 성공 시 -> 로그인 상태가 유지되면 안 됨; session.invalidate() vs session에 key 값 "alertMsg"를 넘기는 코드를 활용해서 alert 창 띄워주고자 함 
			// invalidate() 메소드를 사용하면 session이 만료되어 alert가 작동하지 않을 것임 -> session.removeAttribute("key 값")를 활용(해당 key 값에 대한 value만 없어짐)해서 로그아웃 시키기
			session.removeAttribute("loginUser"); // session 내에 로그인된 회원의 객체가 없어짐
			session.setAttribute("alertMsg", "회원 탈퇴에 성공하였습니다. 안녕히 가세요, 다음에 또 만나요!"); // 내가 숙제로 만든 것; 나의 질문 = 왜 이 페이지는 안 뜰까? -> 나의 발견 = 처음 코드에서는 request 객체의 attribute 영역의 "alertMsg" key에 대한 값을 지정하려고 했는데, loginController에서 session 객체의 attribute 영역의 "alertMsg" key에 대한 값을 지정했었기 때문에, 여기서도 session 객체 사용해야 한다? -> 나의 추가 질문 = request/session 객체 사용법 잘 이해 안 됨
//			session.setAttribute("loginUser", deleteMem); // 내가 숙제로 했던 방식
			
			// 로그아웃 되었으므로 my page가 보이면 안 되는 바, main page(localhost:8001/jsp)로 보냄 = contextRoot/Path 응답
			// Apache Tomcat 서버에 localhost:8001/jsp(폴더) 요청 시, 주소창 가장 오른쪽에 /('폴더' 의미) 자동으로 생김 vs localhost:8001/jsp.html과 같이 확장자를 붙여서 요청하는 경우에는 파일 요청하는 것으로 인식됨 
			response.sendRedirect(request.getContextPath());
		} else { // 회원 탈퇴 실패 시 = 회원 탈퇴가 아직 안 됨 -> 경로를 지정해서 forwarding하는 방식으로 오류 페이지로 보냄
			request.setAttribute("errorMsg", "회원 탈퇴에 실패하였습니다");
			// 2022.1.10(월) 10h45 session.setAttribute("errorMsg", "회원 탈퇴에 실패하였습니다"); 이렇게 오타 -> 나의 발견 = session의 attribute 영역에 "errorMsg"라는 key 값을 가진 것을 만든 적 없는 바, null이라는 초록색 문자가 화면 상단 가운데에 뜸 -> 나의 질문 = 이 글자 style은 어떻게/어디서 적용된 것이지?
			
			// 내가 한 방식 = my page 응답
//			response.sendRedirect(request.getContextPath() + "/myPage.me");
			// 2022.1.10(월) 10h40 강사님 방식
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			// 경로 지정 시(+Dao에서 getResources() 할 때 제외한 모든 경우) 기준 = WebContent(-> 절대 경로 지정) vs Dao에서 getResources() 할 때 기준 = MemberDao.class 파일의 최상위 = "WebContent > WEB-INF > classes"(-> 이 classes 폴더를 기준으로 상대 경로 지정(=절대 상대 경로)) 
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
