package com.kh.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

// 2022.1.12(수) 15h10
/**
 * Servlet implementation class BoardInsertController
 */
@WebServlet("/insert.bo")
public class BoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 2022.1.13(목) 9h20 보충 설명 = 웹 서비스에서 대용량의 파일을 서비스하기에는 적합하지 않음 vs 네트워크 통신의 특성상, 중간에 패키지 유실/변조, 누군가의 공격에 의해 뺏길 수 있음 등의 문제가 있어 용량이 큰 파일을 전송하기에는 적합하지 않는 바, FTP(파일 전송 프로토콜)를 이용하는 것이 나음(당시 수업 때 필기하며 패키지 용량 제한?, 보안 문제 등으로 이해했는데, 정확한 메모는 왼쪽과 같음) 
		
		// post 방식으로 요청/전달 받음 -> 단계0) encoding 방식 설정
		request.setCharacterEncoding("UTF-8");
		
		// 단계1) 값 뽑기
//		System.out.println(request.getParameter("category")); // boardEnrollForm.jsp에서 글 작성(제목과 내용 간단히 입력)하고 '작성' 버튼 클릭 = 이 Servlet 호출 -> 이 줄 코드에 의해 console에 null로 찍힘
		// form 전송 시 일반(x) multipart/form-data(o) 방식으로 전송하는 경우, request.getParameter("key 값")로 request 객체로부터 값 뽑기가 불가능함 -> multipart(Request?)라는 객체에 값을 이관시켜서 다뤄야 함
		// step0) enctype이 multipart/form-data로 잘 전송된 경우에 전반적인 내용들이 수정되도록 조건 걸어줌 -> 나의 질문 = 이것에 대한 else는 처리 어떻게/안 해도 되나?
		if (ServletFileUpload.isMultipartContent(request)) { // enctype이 multipart/form-data로 잘 전송된 경우 -> ServletFileUpload.isMultipartContent() 메소드는 true 반환
//			System.out.println("잘 실행되나 확인해봅니다 ^^");
			
			// 파일 업로드를 위한 library 다운로드 @http://www.servlets.com/ -> 파일 업로드를 위한 library명 = cos(com.oreilly.servlet의 약자).jar -> 다운 받은 압축 파일을 WebContent > WEB-INF > lib 폴더 안에 drag and drop해서 넣기
			// cos.jar에 들어 있는 것 examples(출처: https://ninearies.tistory.com/92): DefaultFileRenamePolicy(똑같은 이름을 가진 파일이 존재한다면 파일명(default)에 1,2,3.. 으로 붙게 해주는 클래스), FileRenamePolicy(똑같은 이름을 가진 파일이 존재할 때 사용자가 만든 이름을 적용시킬 수 있게 해주는 클래스)
			
			// step1) 전송되는 파일 관련해서 처리할 작업 내용 = 용량 제한 + 전달된 파일을 저장할 경로 지정 등
			// step1a) 전송 파일 용량(-> 나의 hard disc 자원 소진) 제한 -> int maxSize(단위: byte) = 10Mbytes로 제한 cf. 2022.1.13(목) 9h 보충 설명 = 보통 웹 서비스에서는 3Mbytes(제대로 들었는지 수업 녹화 영상/녹음 확인) 제한
			/* 단위: Byte -> Kbyte -> Mbyte -> Gbyte(기가바이트) -> Tbyte(테라바이트) -> 페타바이트..; 추후 개인적으로 운영체제 공부 시 자세히 공부해보기
			 * 환산: 1024(=2^10) Bytes = 1 Kbyte; 1024 Kbytes = 1 Mbyte = 1024 * 1024(2^20) Bytes
			 */
			// 2023.9.20(수) 20h10 보편적인 이미지 파일 업로드 limit 어떻게 잡는 게 좋을까 reference = https://ux.stackexchange.com/questions/95196/how-can-we-go-about-deciding-an-appropriate-filesize-upload-limit
			int maxSize = 1024 * 1024 * 10; // 10Mbytes
			
			// step1b) getRealPath() 메소드 + WebContent 폴더로부터 board_upfiles 폴더까지의 경로를 인자로 제시 -> 전달된 파일을 저장할 서버의 폴더 경로(String savePath) 알아내기
			// cf. Servlet 제공 객체 4가지
			// 1. ServletContext application: vo 등 Java 클래스 포함하여 web application 어디에서든지 사용 가능 -> board_upfiles 폴더 경로 얻으려면 이 객체의 도움 필요한데, 이 객체는 (순차적으로 request가 있어야만 ->) session이 있어야만 얻을 수 있음 <- request.getSession().getServletContext()
			// 2. HttpSession session: JSP와 Servlet 어디서든 사용 가능함; request를 가지고/request가 있어야만 얻을 수 있음 <- request.getSession()
			// 3. HttpServletRequest request: 요청(page)-응답(page) 1세트에서 사용 가능
			// 4. page = 거의 사용x
			String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfiles/"); // 가장 왼쪽에 나오는 / = WebContent(15h45 왜/어떻게인지 강사님의 설명 놓침); 가장 우측에 /(폴더임을 표시) 꼭 붙여주기
//			System.out.println(maxSize); // 10485760
//			System.out.println(savePath); // C:\Web-workspace2\JSP_Project\WebContent\resources\board_upfiles\
			
			// step2) 전달된 파일명 수정 + 서버에 업로드
			/* HttpServletRequest request 객체를 MultipartRequest multiRequest 객체로 변환 <- MultipartRequest 객체 생성 방법 = cos.jar에서 제공하는 매개변수 생성자로 생성
			 * 표현법:
			 * MultipartRequest multiRequest = new MultipartRequest(요청 받을 때 전달받은 request 객체, 파일이 저장될 물리적 경로, 파일 최대 용량, encoding, 파일명을 수정시켜주는 객체);
			 * 위 구문 1줄 실행 -> 넘어온 첨부파일이 해당/지정한 폴더에 그대로/무조건 업로드됨
			 * 사용자가 올린 파일명은 그대로 해당 폴더에 업로드하지 않는 것이 일반적임 -> 파일명을 (내 마음대로) 수정시켜주는 객체 생성 -> 파일명을 수정하는 이유 = 같은 파일명이 있을 경우를 대비 ou 파일명에 한글, 특수문자, 띄어쓰기가 포함된 경우 서버에 따라 문제 발생 가능
			 * cos.jar에서 제공하는, 기본적으로 파일명을 수정시켜주는 객체 DefaultFileRenamePolicy가 있긴 있음 -> 내부적으로 rename() 메소드를 실행시키면서 파일명 수정됨 -> 동일한 파일명이 존재할 경우, 기본적으로 파일명 뒤에 숫자를 붙여줌(성의가 없음) e.g. aaa.jpg, aaa1.jpg, aaa2.jpg..
			 * vs DefaultFileRenamePolicy를 이용하지 않고, com.kh.common.MyFileRenamePolicy라는 클래스 + 나만의 파일 생성 규칙을 만들어, 내 마음/내가 원하는대로 파일명을 수정해서 파일명이 절대 겹치지 않도록 rename해 보고자 함 e.g. Kakaotalk_yyyymmdd_hhmmssRRR카카오톡
			 */
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy()); // 글 작성 + 수업용 꽃 사진 1개 upload -> Eclipse Navigator refresh -> 서버/hard disc의 해당 경로에 2022011216563414070.jpg 저장되어있음
			// FileRenamePolicy 인터페이스를 구현하는 new MyFileRenamePolicy() 객체를 생성하면 rename() 메소드가 호출되도록 MultipartRequest(cos.jar에 들어있는 클래스) 라이브러리가 만들어둠 -> 2022.1.13(목) 15h25 나의 질문 = rename() 메소드는 파일을 매개변수로 받는 것 같음 vs 나는 파일을 전달한 것이 없는 것 같은데, 어떻게 전달이 되는 것이지?
			// 라이브러리 한 줄 한 줄 분석하는 것은 지금 단계에서 시간 낭비 vs 추후 경력 7-8년차쯤 되면 분석할 일 있을 수도 있음 -> 지금으로써는 활용 방법 알면 됨(?제대로 이해했는지 >.<); 라이브러리 공부 방법 googling 해보기
			// +서버/hard disc에 이렇게 저장된 파일을 사용자가 추후 다운로드 받을 수 있도록 하기 위해 첨부 파일과 관련된 정보(누구, 어떤 게시글 관련 등)도 같이 저장하고자 함
			
			// 값(카테고리 번호, 글 제목, 글 내용, 작성자 회원번호) 뽑기 -> Board 객체로 가공 -> db에 INSERT
			String category = multiRequest.getParameter("category");
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");
			String boardWriter = multiRequest.getParameter("userNo");
			
			// 단계2) vo 객체로 가공
			// 단계2a) Board 객체로 만듦
			Board b = new Board();
			b.setCategory(category);
			b.setBoardTitle(boardTitle);
			b.setBoardContent(boardContent);
			b.setBoardWriter(boardWriter);
			
			// 단계2b) ATTACHMENT 테이블 관련 2번째 INSERT문 = 선택적 실행; 첨부 파일이 있을 경우에만 INSERT
			Attachment at = null; // null로 초기화 -> 첨부 파일이 있으면 그 때 객체 생성
			
			// 첨부 파일이 있을 경우, 원본 파일명, 수정 파일명, 파일 경로
			// 첨부 파일 유무/원본 파일명이 존재(안)하는지를 가려내는 메소드 = multiRequest.getOriginalFileName("key 값") -> 첨부 파일이 있을 경우 '원본 파일명' vs 없을 경우 null return
			if (multiRequest.getOriginalFileName("upfile") != null) { // 원본 파일명/첨부 파일이 있다면
				at = new Attachment(); // 첨부 파일 관련 값들을 vo 객체로 가공
				at.setOriginName(multiRequest.getOriginalFileName("upfile")); // 원본 파일명
				at.setChangeName(multiRequest.getFilesystemName("upfile")); // multiRequest.getFilesystemName("key 값") = 서버에 실제 업로드된 파일의 이름을 return해주는 메소드 -> 수정 파일명 알아오기
				at.setFilePath("/resources/board_upfiles/"); // 파일 경로
			}
			
			// 단계3) service단으로 toss/처리 요청
			int result = new BoardService().insertBoard(b, at);
			
			// 2022.1.13(목) 10h
			// 단계4) 응답 페이지 결정
			if (result > 0) { // 성공 -> 가장 최신 글이므로 list.bo?currentPage=1 요청
				request.getSession().setAttribute("alertMsg", "게시글 작성에 성공했습니다");
				response.sendRedirect(request.getContextPath() + "/list.bo?currentPage=1");
			} else { // 실패
				// 첨부 파일이 있었을 경우 파일 첨부 실패했다면(=게시글 작성도 실패했다면), 82행 코드에 의해 내 서버에 이미 업로드/저장된 첨부 파일은 내 hard disc의 용량만 차지하므로, 굳이 서버에 보관할 이유가 없음
				// 나의 질문 = db에 게시글 작성 및 파일 첨부 성공했을 때만 서버에 파일 업로드(82행 코드 실행)하는 식으로는 안 하나? -> 2022.1.31(월) 16h45 나의 생각 = 요청 페이지로부터 요청 받은 내용 꺼내려면 multiRequest 객체를 생성(82행 코드 실행)해야 하는데, multiRequest 객체 생성과 동시에 첨부파일이 서버에 업로드되는 것 같기 때문에, 절차상 일단은 서버에 업로드해야 하는 듯?
				if (at != null) {
					new File(savePath + at.getChangeName()).delete(); // 파일 객체 생성(이 파일이 어디에 있는 어떤 이름의 파일인지 알아야 객체 생성 가능) + delete() 메소드 호출
					// 파일 객체 = C:\Web-workspace2\JSP_Project\WebContent\resources\board_upfiles\2022011216563414070.jpg
				}
				
				request.setAttribute("errorMsg", "게시글 작성에 실패했습니다");
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			}
			
		} // enctype이 multipart/form-data로 잘 전송된 경우 if문 영역 끝
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
