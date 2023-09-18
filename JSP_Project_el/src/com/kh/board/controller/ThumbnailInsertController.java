package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

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

// 2022.1.14(금) 15h5
/**
 * Servlet implementation class ThumbnailInsertController
 */
@WebServlet("/insert.th")
public class ThumbnailInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThumbnailInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// post 방식 요청 전송 -> 단계0) encoding
		request.setCharacterEncoding("UTF-8");
		
		if (ServletFileUpload.isMultipartContent(request)) { // 첨부 파일 관련해서 multipart/form-data로 잘 넘어왔는지 조건 제시
			// MultipartRequest 객체 생성 -> 곧바로 서버에 파일이 올라옴
			// 객체 생성 전 step1a) 전송 용량 제한 = 10Mbyte
			int maxSize = 1024 * 1024 * 10;
			
			// step1b) 저장할 서버의 물리적 경로 제시
			String savePath = request.getServletContext().getRealPath("/resources/thumbnail_upfiles/"); // request 객체의 'getSession()' 안 써도 됨 -> 간결한 코드가 좋은 바, 생략
			
			// step2) 전달된 파일명 수정해서 올리기 <- MultipartRequest 객체 생성
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
			
			// 단계1) multiRequest에 (무엇을? 제대로 못 들음 15h10) 위임한 바, multiRequest 객체로부터 값 뽑기 <- getParameter() 메소드 이용
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");
			String userNo = multiRequest.getParameter("userNo");
			
			// 단계2) 내가 실행해야 할 쿼리문에서 필요한 값들을 전달하기 위해 vo로 가공
			// 단계2a) Board 객체로 가공
			Board b = new Board();
			b.setBoardTitle(boardTitle);
			b.setBoardContent(boardContent);
			b.setBoardWriter(userNo);
			
			// 단계2b) Attachment 객체로 가공: 사진게시판에서 메인 이미지는 required 요소 -> 게시글에 적어도/최소 1개의 첨부파일 존재 -> 여러 개의 vo 객체를 묶어서 ArrayList로 다룸
			ArrayList<Attachment> list = new ArrayList<>();
			
			// key 값 = file1~4
			for (int i = 1; i <= 4; i++) {
				String key = "file" + i; // 파일들의 key 값을 미리 변수로 세팅
				
				if (multiRequest.getOriginalFileName(key) != null) { // 원본 파일(명)이 존재한다면; getOriginalFileName() 메소드는 파일에 대해서만 사용해야 함 vs 오전에 강사님께서 BoardUpdateController에서 hidden 요소에 대해 사용하려고 하셔서 잘 안 되었음
					// 첨부 파일이 존재할 경우, Attachment 객체(필드 = 원본 파일명, 수정 파일명, 폴더 경로, 파일 레벨(대표 이미지 1 vs 상세 이미지 2)) 생성
					Attachment at = new Attachment();
					
					at.setOriginName(multiRequest.getOriginalFileName(key)); // 원본 파일명
					at.setChangeName(multiRequest.getFilesystemName(key)); // 수정 파일명
					at.setFilePath("/resources/thumbnail_upfiles/"); // 파일 경로
					
					// 파일 레벨
					if (i == 1) { // 대표 이미지인 경우
						at.setFileLevel(1);
					} else { // 상세 이미지인 경우
						at.setFileLevel(2);
					}
					
					list.add(at);
				} // if문 영역 끝
			} // for문 영역 끝
			
			// 단계3) service단으로 toss
			int result = new BoardService().insertThumbnailBoard(b, list);
			
			// 단계4) 결과에 따른 응답 view 지정
			if (result > 0) { // 성공 시 -> sendRedirect를 통해 list.th로 요청
				request.getSession().setAttribute("alertMsg", "사진게시판 업로드에 성공했습니다");
				response.sendRedirect(request.getContextPath() + "/list.th");
			} // 실패 시; result = 0 -> 처리는 생략; 응답 페이지 경로가 /insert.th로 끝남
		} // 첨부 파일 관련해서 multipart/form-data로 잘 넘어왔는지 조건문 if문 영역 끝
		
		// 2021.1.14(금) 16h25 실행 시 나의 오류 = 강사님, 저는 파일이 서버에는 저장되었는데, db ATTACHMENT 테이블에 INSERT가 안 됩니다(console창에 오류가 뜬 건 없었음, 사진게시판 글 작성 후 성공 메시지 팝업 안 뜸, 나의 추측 = INSERT INTO ATTACHMENT 처리 실패한 듯
		// 강사님께서 해결해주심 = if문 '원본 파일(명)이 존재한다면'의 조건식에서 파일의 key 값을 Java 변수 key가 아닌, 문자열 "key"로 넣어놨었음 -> 그러니까 요청 받은 데이터 중에서 파일을 아무리 찾을 수 없었음 -> null인 Attachment list로 service단에 요청 -> UPDATE 처리된 행의 개수 result = 0 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
