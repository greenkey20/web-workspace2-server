package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

// 2022.1.4(화) 14h40

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login.me")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// HttpServletRequest: 서버로 요청할 때의 정보들(요청 시 전달 값, 요청 전송 방식 등)이 담겨있는 객체
		// HttpServletResponse: 요청에 대해 응답하고자 할 때 사용하는 객체
		
		// 단계0) post 방식으로 요청했을 경우 반드시 encoding 설정 -> 나의 웹 개발 서비스가 전세계  네트워크/웹 상에서 동작할 수 있도록
		request.setCharacterEncoding("UTF-8");
		
		// 단계1) 요청 시 request 객체의 parameter라는 영역(마술 주머니/바구니; 차곡차곡 쌓이지x, 여러 세트가 많이 들어있음; map 같은 것)에 key+value 세트로 담겨 전달된 값을 꺼내서 변수에 기록
		// e.g. form 태그 > input 요소의 경우 'name 속성의 값 = key' + 'value 속성의 값 = 사용자가 input 상자에 기입한 값 = value'
		// 메소드()는 명령어로 해석 -> get() = 값을 내놔/빼와라, set() = 설정해
		// request.getParameter("key 값") -> 해당 key와 세트인 value가 String 자료형으로 반환됨
		// request.getParameterValues("key 값") -> 해당 key와 세트인 value(s)가 String[] 배열로 반환됨 e.g. checkbox		
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
//		System.out.println(userId);
//		System.out.println(userPwd);
		// 주의할 점1. server에 어떤 내용의 변경 수정이 일어난 뒤 'restart'가 뜬 경우, 서버 다시 시작하고 요청 보내야 함
		// 주의할 점2. Eclipse 폰트 때문에 가독성이 떨어지는데 servlet url mapping 소문자 l 확인; 대문자 L 아님 
		// 주의할 점3. server context root/path 확인 e.g. 대/소문자 구분함 cf. server 더블클릭해서 modules에서 edit/수정 가능
		
		// 단계2) 해당 요청을 처리하는 service 클래스의 메소드를 호출 + 사용자로부터 전달받은 값을 전달
		Member loginUser = new MemberService().loginMember(userId, userPwd);		
		// sql문 = SELECT * FROM MEMBER USER_ID = '사용자가 입력한 ID/userId' AND USER_PWD = '사용자가 입력한 비밀번호/userPwd' AND STATUS = 'Y';
		// 일치하는 회원이 있다면 일치하는 회원의 모든 컬럼 값이 필드에 담긴 Member 객체로 반환 받음 vs 일치하는 회원이 없다면 null이 담김
		
//		System.out.println(loginUser);
		
		// 단계3) 처리된 결과를 가지고 사용자가 보게 될 응답화면을 지정
		// 3a) 응답 화면에 보여질 값을 request 객체의 attribute 영역에 담음
		// 3b) RequestDispatcher 객체 생성 -> 응답할 view 화면(jsp 파일 경로) 지정
		// 3c) case1. RequestDispatcher 객체 생성 = 응답할 view 화면 지정 -> forward
		// 	   case2. response.sendRedirect = 응답할 view 화면 또는 url 지정
		
		/* 응답 페이지에 전달할 값이 있을 경우, 값을 어딘가에 담아야 함 -> 어딘/누군가의 attribute 영역에 담아서 보냄 -> 담아줄 수 있는 객체 4종류(Servlet Scope 내장 객체; 위로 올라올수록 범위가 큼)
		 * 크다
		 * 1. application: web application 전역(jsp, Servlet + Java 클래스) + 언제나 꺼내쓸 수 있음
		 * 2. session: 모든 jsp와 Servlet에서 꺼내 쓸 수 있음 + session이 끊기는 경우(e.g. 브라우저 종료, 서버 멈춤, 내가 직접적으로 session 객체에 담은 값을 지웠을 때/사용자가 로그아웃 버튼을 눌렀을 때 등)까지만 쓸 수 있음
		 * 3. request: 해당 request를 forwarding한 응답 jsp 페이지에서만(Servlet과 jsp 1세트) 사용 가능함 -> '요청 페이지에서부터 응답 페이지까지'에서만 쓸 수 있음; 1회성 느낌  -> 로그인 페이지로부터 다른 페이지로 넘어가면 로그인 상태 유지 안 되어 다시 로그인해야 함
		 * 4. page: 담은 값을 해당 jsp 페이지에서만 쓸 수 있음 -> 화면이 넘어가면 담은 값이 소멸됨
		 * 작다
		 * 
		 * 2022.1.5(수) 11h5
		 * session 및 request가 가장 많이 쓰임
		 * 4가지 객체 모두 attribute 속성을 가지고 있는 바, 공통적으로 데이터를 담고자 한다면 xx.setAttribute("key 값", value);
		 * 데이터를 뽑고자 한다면 xx.getAttribute("key 값");
		 * 데이터를 지우고자 한다면 xx.removeAttribute("key 값");
		 * 
		 * e.g. 로그인 시 session.setAttribute("userInfo", loginUser);
		 * 로그아웃 시 session.removeAttribute("userInfo"); 또는 무효화시키는 메소드
		 */
		
		if (loginUser == null) { // 로그인 실패 -> error page 응답
			// error 메시지 넘기기
			// 단계3a) request의 Attribute 영역에 메시지 담기
			request.setAttribute("errorMsg", "로그인에 실패했습니다");
			
			// 단계3b) RequestDispatcher 객체 생성
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			
			// 단계3c) forward(메소드 호출)
			view.forward(request, response);
			
		} else { // 로그인 성공 시 사용자의 정보를 넘기고자 함
			// 단계3a) session의 Attribute 영역에 사용자 정보 담기 <- 로그아웃하기 전까지 로그인한 회원의 정보를 계속 가져다가 사용할 것이기 때문에 session 객체에 담음
			// Servlet에서 JSP 내장 객체인 session에  접근해서 session 객체를 사용하려면, 우선 session 객체를 얻어와야 함 -> session 객체의 type = HttpSession, session 객체 생성 방법 = request.getSession();
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser);
			session.setAttribute("alertMsg", "성공적으로 로그인하셨습니다"); // 나의 질문 = 강사님, 있다가 ‘회원 가입 성공’ alert 창 뜨는 원리 설명 1번 다시 들을 수 있을까요? >.<
			
			/*
			// 방식1 = forwarding 응답 방식으로 넘길 경우
			// 단계3b) RequestDispatcher 객체 생성
			RequestDispatcher view = request.getRequestDispatcher("index.jsp");
			
			// 단계3c) forward(메소드 호출) -> forwarding 방식의 가장 큰 특징 = url(브라우저 주소 창)에는 여전히 현재 이 Servlet url mapping 값(localhost:8001/jsp/login.me)이 남아있음 vs 응답 페이지는 index.jsp이기 때문에 localhost:8001/jsp(context path 요청 주소 -> 이걸 요청하면  welcome page인 index.jsp가 보임)/로 보여야 함
			// Servlet url mapping 값이 노출되면 보안에 취약함 -> sql injection 등 공격 가능 -> 해결방법 = url 재요청/sendRedirect 방식
			// cf. localhost:8001/jsp를 요청하면 브라우저 주소창 가장 우측에 /(하위 폴더 의미)가 자동적으로 추가됨
			view.forward(request, response);
			// session에 담은 것은 jsp로? 넘어가서 session으로부터 빼면 됨 <- session은 어디서든 사용 가능함
			*/
			
			// 방식2 = url 재요청/sendRedirect 방식: url을 재요청함으로써 응답 페이지가 보여지도록 response 객체를 이용하는 방법; response.sendRedirect(재요청할 경로);
			// url 재요청 방식의 가장 큰 특징 = 내가 요청한 경로가 url(브라우저 주소 창)에 보임
			response.sendRedirect("/jsp"); // localhost:8001/jsp로 url 재요청이 감
		}
		
	} // doGet() 종료

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
