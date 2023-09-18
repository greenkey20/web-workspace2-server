package com.kh.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
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

// 2022.1.13(목) 17h15
/**
 * Servlet implementation class BoardUpdateController
 */
@WebServlet("/update.bo")
public class BoardUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// post 방식 요청 -> 단계0) encoding 필요
		request.setCharacterEncoding("UTF-8");
		
		// 단계1) 값 뽑기 <- '첨부 파일이 전송될 것인가'를 파악하고 난 뒤에
		if (ServletFileUpload.isMultipartContent(request)) {
			// 2가지 설정 -> 파일 내려받기
			// 1. 전송 파일 용량 제한 -> int maxSize = 10Mbyte
			int maxSize = 1024 * 1024 * 10;
			// 2. 전달된 파일을 저장시킬 서버 폴더의 물리적인 경로 알아내기 -> String savePath
			String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfiles/"); // 17h20 getRealPath()의 기준점 = 클래스?(학우님의 질문 및 강사님의 코멘트를 정확히 못 들음)
			
			// 전달될 파일명 수정 후 서버에 업로드
			// MultipartRequest 객체 생성 -> 서버에 파일이 잘 감
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy()); // 필기 다 못함?
			// 요청 시 정보는 모두 request 객체 안에 들어있는 바, 전송하고자 하는 파일도 request 안에 들어있음; 웹 통신에서 너무 용량이 큰 파일은 time out이 발생할 수 있음 + 17h25 추가 설명 미처 필기 못함 -> 웹 통신에서 너무 큰 용량의 파일 전송은 자제하는 것이 좋음
			
			// UPDATE BOARD
			
			// request 객체가 multiRequest 객체로 변환됨(?) ->  multiRequest 객체로부터 값 뽑기
			int boardNo = Integer.parseInt(multiRequest.getParameter("bno"));
			String category = multiRequest.getParameter("category");
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");
			
			// 단계2) vo로 가공
			// Board 관련
			Board b = new Board();
			b.setBoardNo(boardNo);
			b.setCategory(category);
			b.setBoardTitle(boardTitle);
			b.setBoardContent(boardContent);
			
			// Attachment 관련된 것도 처리해서 넘김
			Attachment at = null; // Attachment 객체 선언만 해둠(null)
			// 실제 첨부 파일이 있다면 객체 생성 vs 없다면 null -> 단, 첨부 파일이 있는 경우, 기존에 존재하던 파일 ou 새로운 첨부 파일(금번 글 수정 시 수정된/바꿔서 올라온 ou 원래 없었는데 새로 업로드된) 등 경우의 수 여러 가지 있음
			if (multiRequest.getOriginalFileName("reUpfile") != null) { // 금번 글 수정 시 새로 업로드된/새로운 파일의 이름이 존재한다면 -> 객체 생성 + 원본 이름, 수정 이름, 파일 경로 set
				at = new Attachment();
				at.setOriginName(multiRequest.getOriginalFileName("reUpfile"));
				at.setChangeName(multiRequest.getFilesystemName("reUpfile"));
				at.setFilePath("/resources/board_upfiles/"); // 경로 기준 = / = WebContent 폴더 <- 2022.1.13(목) 수업 시 맨 앞 / 없이 경로 지정했었는데, 파일 다운로드 시 경로 못 찾아서 오류
				// 이상/여기까지는 새롭게 업로드한, 새로운 첨부파일에 대한 내용 -> set
				
				// 17h45 첨부 파일이 있었을 경우 + 원본 파일도 있을 경우, 원본 파일의 파일 번호 및 수정 파일명 필요(강사님 설명 놓침 + 2022.1.14(금) 9h25 강사님 설명 또 이해 잘 못함)
				// 방법1) view단에서 hidden으로 넘겨 받음
				// 방법2) selectAttachment() 메소드 사용해도 되긴 하는데, 그러면 db에 한 번 다녀와야 하는  delay time 소요
				if (multiRequest.getParameter("originFileNo") != null) { // 기존 파일이 있었다면; 요청 페이지로부터 hidden으로 넘겼을 때 getOriginalFileName() 메소드의 결과 null이 문자열 "null"로 넘어옴 -> 이 조건문에서는 getParameter()로 값 뽑아냄
					// 기존 파일에 대한 파일 번호를 at에 담음(WHERE절 - 필기 의미 이해 안 됨 >.<)
					at.setFileNo(Integer.parseInt(multiRequest.getParameter("originFileNo")));
					
					// 서버에 기존에 존재(수정 파일명으로 저장되어 있음)하던 첨부파일 삭제
//					System.out.println(savePath + multiRequest.getParameter("originFileName")); // 2022.1.14(금) 11h20 C:\Web-workspace2\JSP_Project\WebContent\resources\board_upfiles\2022011310280332565.png
					new File(savePath + multiRequest.getParameter("originFileName")).delete();
				} else { // 새로운 첨부파일이 넘어왔지만, 기존 파일은 없는 경우 -> INSERT + 어느 게시글의 첨부 파일인지 boardNo(ATTACHMENT 테이블의 REF_BNO 컬럼)
					at.setRefNo(boardNo);
				}
				
				// 나의 질문 = 기존에 올렸던 파일 삭제는 못하는 건가? -> 17h45 강사님 답변 = Java 코드만으로는 그런 요청 받을 수 있는 방법이 없으니까, script 이용해야 할 듯
			}
			
			// 단계3) service단으로 toss -> 경우의 수 따져서 트랜잭션 처리 필요
			// case1) 새로운 첨부파일 없음 -> b, null -> UPDATE BOARD
			// case2) 새로운 첨부파일 있음 + 기존 첨부파일 있음 -> UPDATE BOARD, UPDATE ATTACHMENT
			// case3) 새로운 첨부파일 있음 + 기존 첨부파일 없음 -> UPDATE BOARD, INSERT INTO ATTACHMENT
			int result = new BoardService().updateBoard(b, at);
			
			// 단계4) 결과에 따른 응답 view 지정
			if (result > 0) { // 성공 시 -> alertMsg(성공 메시지) 보내기 -> sendRedirect 방식으로 상세 보기 페이지(localhost:8001/jsp/detail.bo?bno=X)로 이동
				request.getSession().setAttribute("alertMsg", "일반게시판 게시글 수정을 성공했습니다");
				response.sendRedirect(request.getContextPath() + "/detail.bo?bno=" + boardNo);
				System.out.println(boardNo); // 2022.1.14(금) 11h20 현재 4로 찍힘
			} else { // 실패 시 -> error page 보여주기
				request.setAttribute("errorMsg", "일반게시판 게시글 수정에 실패했습니다");
				RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
				view.forward(request, response);
			}
			
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
