package com.kosmo.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("get call");

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		//		String searchGubun = request.getParameter("searchGubun");
		//		String searchStr = request.getParameter("searchStr");
		//		System.out.println(searchGubun+","+searchStr);
		//		//1. String 응답
		//		PrintWriter  out = response.getWriter();
		//		out.println("결과");



		//--------------------------
		//2.JSON 검색조건 추출
		//--------------------------
		String jsonStr = request.getParameter("MYKEY");
		System.out.println(jsonStr);	//{"searchGubun":"btitle","searchStr":"값"}


		BoardVO vo = new BoardVO();
		Gson gson = new Gson();
//		//fromJson : json(String문장)->자바객체
		vo = gson.fromJson(jsonStr, BoardVO.class);
		System.out.println("vo:"+vo.getSearchGubun()+","+vo.getSearchStr());

//-----------------------------------------
// 1건 수정
//-----------------------------------------
//		int res = impl.update(vo);
//		int res = 1;
//		PrintWriter  out = response.getWriter();
//		out.println(res);


//-----------------------------------------
// 전체목록
//-----------------------------------------
		//BoardImpl impl = new BoardImpl();
		//ArrayList<BoardVO> list = impl.replyList();
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		for(int i=0; i<5; i++) {
			BoardVO vo2 = new BoardVO();
			vo2.setBseq(i);
			vo2.setBtitle("title"+i);
			list.add(vo2);
		}
//		"[ {bseq:1, btitle:"제목1"},
//		  {bseq:2, btitle:"제목2"},
//		  {bseq:3, btitle:"제목3"} ]  "

		//toJson : 자바객체->json(String문장)
		String res = gson.toJson(list);
		PrintWriter  out = response.getWriter();
		out.println(res);
	}

}
