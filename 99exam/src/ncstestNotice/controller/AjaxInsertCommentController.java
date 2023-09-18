package ncstestNotice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ncstestNotice.model.service.NoticeService;

// 2022.2.16(수) 15h35
/**
 * Servlet implementation class AjaxInsertComment
 */
@WebServlet("/commentRegister.do")
public class AjaxInsertCommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxInsertCommentController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String commentContents = request.getParameter("commentContents"); // 시험 볼 때 이 key 값을 'queryString'으로 잘못 씀 -> 관련 테이블에 null 값은 insert할 수 없다는 sql exception 발생 -> 댓글 내용이 db에 전달 안 되는 것 같아 이 코드 확인해서 오류 발견
//		System.out.println(commentContents); // 사용자가 입력한 댓글 잘 넘어옴
		
		int result = new NoticeService().insertComment(commentContents);
		
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
