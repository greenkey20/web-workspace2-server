package com.kh.ajax;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.kh.model.vo.Member;

// 2022.1.18(화) 15h20
/**
 * Servlet implementation class JqAjaxController3
 */
@WebServlet("/jqAjax3.do")
public class JqAjaxController3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JqAjaxController3() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// vo 객체 1개만 응답 시 JSONObject{} 형태로 만들어져서 응답 vs ArrayList 응답 시 JSONArray[] 형태로 만들어져서 응답
		
		// case1) vo 객체 1개만 응답
		// get 방식 요청 -> encoding 필요 없음
		
		// 값 뽑기
		int memberNo = Integer.parseInt(request.getParameter("no"));
		
		// view단에서 전달받은 회원 번호(memberNo)를 가지고 db로부터 회원 정보/데이터를 조회했다는 가정 하에 Member 객체에 값 담기
		Member m = new Member(memberNo, "강토미", 5, "여성");
		
		// m을 응답
		// 단계0 = 형식 및 encoding 지정
//		response.setContentType("text/html; charset=UTF-8");
//		response.getWriter().print(m); // Java 객체를 넘겼을 때, 내부적으로 Member 객체의 toString() 메소드가 호출되어, 문자열 형태로 값이 넘어감
		
		// JSON 이용 -> Java 객체를 JavaScript 객체(JSONObject)로 변환
		/*
		JSONObject jObj = new JSONObject(); // JSONObject 객체 생성 -> 빈 JavaScript 객체 {} 생성
		jObj.put("memberNo", m.getMemberNo()); // {memberNo : 20}
		jObj.put("memberName",m.getMemberName()); // {memberNo : 20, memberName : "강토미"}
		jObj.put("age", m.getAge()); // {memberNo : 20, memberName : "강토미". age : 5}
		jObj.put("gender", m.getGender()); // {memberNo : 20, memberName : "강토미". age : 5, gender : "여성"}
		
		// 응답으로 넘기기
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jObj);
		*/
		// 이상 귀찮았던 작업 = Java 객체를 필드 하나하나씩 JSON 객체로 옮기는 것(? 15h35 강사님 설명 제대로 못 들음) -> JSON은 정석 방법 vs GSON(Google JSON library; GSON library를 연동해야지만 사용 가능)
		// https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.5에서 download <- usages 숫자가 가장 높은 것(=검증된 것, 버그 비교적 적음 등) 선택
		
		// 형식 및 encoding 지정
		response.setContentType("application/json; charset=UTF-8");
		
		// 2022.1.18(화) 16h
		// GSON 객체 생성
		/*
		Gson gson = new Gson();
		
		// gson.toJson() 메소드 호출
		// 표현법: toJson(응답할 객체, 응답할 stream) -> response.getWriter()라는 통로/stream으로 m이라는 Java 객체를 응답 + key 값은 자동적으로 필드명이 됨
		gson.toJson(m, response.getWriter());
		*/
		
		// case2) 여러 개의 객체가 들어있는 ArrayList 넘기기/응답
		ArrayList<Member> list = new ArrayList<>();
		list.add(new Member(1, "강무민", 4, "남성"));
		list.add(new Member(2, "강스노크메든", 4, "여성"));
		list.add(new Member(3, "강미피", 3, "여성"));
		
		/*
		JSONArray jArr = new JSONArray();
		for (Member m : list) {
			JSONObject jObj = new JSONObject();
			jObj.put("memberNo", m.getMemberNo());
			jObj.put("memberName", m.getMemberName());
			jObj.put("age", m.getAge());
			jObj.put("gender", m.getGender());
			// 19h 나의 질문 = 위와 같이 jObj 하나씩 만든 뒤에 jArr에 추가 안 해도 될까?
		}
		*/
		
		// Gson().toJson -> 위와 같이 귀찮은 방법으로 JavaScript 객체로 변환하지 않아도 됨
		// 형식 및 encoding 지정 -> 위에서 해둠
		// GSON 객체 생성 후 응답 보내기
		new Gson().toJson(list, response.getWriter()); // list라는 변수를 response.getWriter()라는 stream/통로로 보냄
	
		// 응답 데이터는 1번 밖에 못 보냄 -> 나의 오류 = 위에서 실험해본 response 부분 주석 처리 제대로 안 해서 500 error 발생
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
