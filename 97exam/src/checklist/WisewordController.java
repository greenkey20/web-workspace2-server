package checklist;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

// 2022.3.17(목) 11h
/**
 * Servlet implementation class WisewordController
 */
@WebServlet("/wiseword.do")
public class WisewordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WisewordController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ranNum = (int)(Math.random() * 20) + 1;
//		System.out.println(ranNum);
		
		Wiseword w = new WisewordDao().getRandomWord(ranNum);
		
		if (w == null) {
			// 나의 질문 = 문제 요구 사항에서 
			System.out.println("조회하는 도중 오류가 발생했습니다.");
			
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().print("조회하는 도중 오류가 발생했습니다.");
		} else {
			response.setContentType("application/json; charset=UTF-8");
			new Gson().toJson(w, response.getWriter());
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
