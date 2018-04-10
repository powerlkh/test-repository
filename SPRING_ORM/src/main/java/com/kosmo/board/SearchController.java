package com.kosmo.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;

@Controller
public class SearchController  {
	private static final long serialVersionUID = 1L;

	@Autowired
	private BoardService service;


	@RequestMapping(value="/search2.do", method = RequestMethod.POST)
	@ResponseBody
	//ResponseEntity<ArrayList<BoardVO>>
	public String search2(
			@RequestBody BoardVO vo
			)
			throws ServletException, IOException {
		System.out.println("vo:"+vo.getSearchGubun()+","+vo.getSearchStr());

		ArrayList<BoardVO> list = service.boardList(1, 10, vo.getSearchGubun(), vo.getSearchStr() );
		return "";
	}


	@RequestMapping(value="/search.do")
	public ResponseEntity<String> search(
			HttpServletRequest request, HttpServletResponse response
			)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
//		//--------------------------
//		//2.JSON 검색조건 추출
//		//--------------------------
		String jsonStr = request.getParameter("MYKEY");
		System.out.println(jsonStr);	//{"searchGubun":"btitle","searchStr":"값"}
//
//
		BoardVO vo = new BoardVO();
		Gson gson = new Gson();
		//fromJson : json(String문장)->자바객체
		vo = gson.fromJson(jsonStr, BoardVO.class);
		System.out.println("vo:"+vo.getSearchGubun()+","+vo.getSearchStr());

		//-----------------------------------------
		// 전체목록
		//-----------------------------------------
		//BoardService service = new BoardServiceImpl();
		ArrayList<BoardVO> list = service.boardList(1, 10, vo.getSearchGubun(), vo.getSearchStr() );

//		"[ {bseq:1, btitle:"제목1"},
//		  {bseq:2, btitle:"제목2"},
//		  {bseq:3, btitle:"제목3"} ]  "

		//toJson : 자바객체->json(String문장)
		String res = gson.toJson(list);

		return new ResponseEntity<String>(res, HttpStatus.OK);

	}

}
