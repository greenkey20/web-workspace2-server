package com.kh.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.model.vo.Person;

// 2022.2.7(월) 14h45
/**
 * Servlet implementation class ELServlet
 */
@WebServlet("/el.do")
public class ELServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ELServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 지금까지는 JSP/scope 내장 객체인 request, session, application, page에 담아서 응답 jsp 페이지에 보냈음
		/* 데이터를 담을 수 있는 JSP 내장 객체 4가지
		 * 1. ServletContext = application scope = application당 1개 존재하는 객체 -> 이 영역에 데이터를 담으면 application 전역에서/프로그램 어디서든지 사용 가능 -> 공유 범위가 가장 큼
		 * 2. HttpSession = session scope = browser당 1개 존재하는 객체 -> 이 영역에 데이터를 담으면 jsp + servlet단에서 사용 가능 -> 값이 1번 담기면, 서버가 멈추거나 브라우저가 닫히기 전까지 사용 가능 e.g. login 기능 -> ServletContext에 비해 공유 범위가 다소 제한적임
		 * 3. HttpServletRequest = request scope = 요청 및 응답 시 매번 생성되는 객체 -> 이 영역에 데이터를 담으면 해당 request 객체를 forwarding 받는 응답 jsp에서만 사용 가능; 1회성 -> 공유 범위가 해당 요청에 대한 응답 jsp 단 하나뿐임
		 * 4. PageContext = page scope = 현재 그/해당 jsp 페이지에서만 사용 가능/쓰임 -> 공유 범위가 가장 작음
		 * 
		 * 위의 객체들에 값을 담을 때는 .setAttribute("key", "value");
		 * 위의 객체들로부터 값을 뽑을 때는 .getAttribute("key"); -> Object 형태 반환
		 * 위의 객체들에서 값을 지우고자 할 때는 .removeAttribute("key");
		 */
		
		// requestScope에 담기
		request.setAttribute("classRoom", "C강의장");
		request.setAttribute("student", new Person("강미피", 3, "여자"));
		
		// sessionScope에 담기
		HttpSession session = request.getSession();
		session.setAttribute("academy", "KH정보교육원");
		session.setAttribute("teacher", new Person("강토미", 5, "여자"));
//		session.setAttribute("teacher", new Person("강판다", 4, "남자")); // key 값은 중복되면 안 됨 -> key 값이 중복되는 경우 덮어씌여짐
		
		// requestScope와 sessionScope에 동일한 key 값으로 데이터를 담음
		request.setAttribute("scope", "request");
		session.setAttribute("scope", "session");
		
		// + applicationScope에도 동일한 key 값으로 데이터를 담음
		ServletContext application = request.getServletContext(); // application 객체 생성
		application.setAttribute("scope", "application");
		
		// 응답해줄 때 forwarding 해줘야 하므로, requestDispatcher 객체가 필요함 cf. 응답해주는 또 다른 방법 = sendRedirect
		RequestDispatcher view = request.getRequestDispatcher("views/1_EL/01_el.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
