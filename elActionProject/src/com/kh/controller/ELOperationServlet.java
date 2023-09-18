package com.kh.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.model.vo.Person;

// 2022.2.7(월) 16h40
/**
 * Servlet implementation class ELOperationServlet
 */
@WebServlet("/operation.do")
public class ELOperationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ELOperationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 테스트할 수 있는 값을 request 객체에 담아 피연산자로써 넘기기
		request.setAttribute("big", 10);
		request.setAttribute("small", 3);
		
		request.setAttribute("sOne", "안녕"); // StringPool로 들어감 cf. Java String api 수업 내용 참고하기
		request.setAttribute("sTwo", new String("안녕")); // StringPool로 들어감(x) instance화되어 heap 메모리 영역 어딘가에 들어가..(o) 17h20 강사님 필기 제대로 못 봄 
		
		request.setAttribute("pOne", new Person("강무민", 4, "남자"));
		request.setAttribute("pTwo", null);
		
		ArrayList<String> list1 = new ArrayList<>();
		request.setAttribute("lOne", list1);
		
		ArrayList<String> list2 = new ArrayList<>();
		list2.add("hej ^^");
		request.setAttribute("lTwo", list2);
		
		// 이상 requestScope에 총 8개의 값이 담겨있음
		request.getRequestDispatcher("views/1_EL/02_elOperation.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
