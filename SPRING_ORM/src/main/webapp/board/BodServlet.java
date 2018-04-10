package com.kosmo.alio;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kosmo.common.AlioPagingUtil;



@WebServlet("/bod")
public class BodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public BodServlet() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=UTF-8");
		String action = request.getParameter("action");
		String gseq = request.getParameter("gseq");
		String btseq = request.getParameter("btseq");
		
		//Imp 선언
		GameImp gi = new GameImp();
		BodImp bi = new BodImp();
		
		//gvo 값 세팅
		GameVO gvo = gi.gameDetail(Integer.parseInt(gseq));
		
		
		if(action.equals("list")) {
			
			//페이징
			int totalCount = bi.bodCount(Integer.parseInt(gseq), Integer.parseInt(btseq));
			
			int currentPage = 1; // 시작하는 페이지 번호
			int blockCount = 5; // 아래 번호당 나올 게시물 수
			int blockPage = 5; // |1|2|3|4|5|...
			
			if(request.getParameter("currentPage") != null) {
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
			}
		
			String url = "/bod?action=list&gseq="+gseq+"&btseq="+btseq;
			AlioPagingUtil apu = new AlioPagingUtil(url, currentPage, totalCount, blockCount, blockPage);
			String html = apu.getPagingHtml();
			
			//페이징 된 list 출력
			ArrayList<WrapperVO> list = bi.bodList(apu.getStartSeq(), apu.getEndSeq(), Integer.parseInt(gseq), Integer.parseInt(btseq));
			
			BodTypeImp bti = new BodTypeImp();
			ArrayList<BodTypeVO> btlist = bti.bodTypeList(Integer.parseInt(gseq));
			
			request.setAttribute("LVL_WVO_LIST", list);
			request.setAttribute("LVL_BTVO_LIST", btlist);
			request.setAttribute("LVL_PAGING", html);
			request.setAttribute("LVL_GVO", gvo);
			request.setAttribute("LVL_BTSEQ", btseq);
			
			RequestDispatcher rd = request.getRequestDispatcher("/alio/fuse/alio_bod_list.jsp");
			rd.forward(request, response);
			
		} else if(action.equals("view")) {
			WrapperVO wvo = new WrapperVO();
			LikeCheckVO lvo = new LikeCheckVO();
			ReplyImp ri = new ReplyImp();
			LikeCheckImp lci = new LikeCheckImp();
			BodTypeImp bti = new BodTypeImp();
			
			String bseq = request.getParameter("bseq");
			System.out.println(bseq);
			
			wvo = bi.bodView(Integer.parseInt(bseq));
			
			ArrayList<BodTypeVO> btlist = bti.bodTypeList(Integer.parseInt(gseq));
			ArrayList<WrapperVO> rlist = ri.replyList(Integer.parseInt(bseq));
			ArrayList<LikeCheckVO> lclist = lci.likeCheckList(Integer.parseInt(bseq));
			
			//request.setAttribute("LVL_BSEQ", bseq);
			request.setAttribute("LVL_BTVO_LIST", btlist);
			request.setAttribute("LVL_WVO", wvo);
			request.setAttribute("LVL_BTSEQ", btseq);
			request.setAttribute("LVL_RLIST", rlist);
			request.setAttribute("LVL_LCLIST", lclist);
			request.setAttribute("LVL_GVO", gvo);
			
			RequestDispatcher rd = request.getRequestDispatcher("/alio/fuse/alio_bod_view.jsp");
			rd.forward(request, response);
		} else if(action.equals("input")) {
			BodTypeImp bti = new BodTypeImp();
			ArrayList<BodTypeVO> btlist = bti.bodTypeList(Integer.parseInt(gseq));
			
			System.out.println(gseq);
			request.setAttribute("LVL_BTVO_LIST", btlist);
			request.setAttribute("LVL_GVO", gvo);
			request.setAttribute("LVL_BTSEQ", btseq);
			
			RequestDispatcher rd = request.getRequestDispatcher("/alio/fuse/alio_bod_input.jsp");
			rd.forward(request, response);
			
		} else if(action.equals("edit")) {
			
			System.out.println(gseq);
			
			WrapperVO wvo = new WrapperVO();
			BodTypeImp bti = new BodTypeImp();
			
			String bseq = request.getParameter("bseq");
			
			ArrayList<BodTypeVO> btlist = bti.bodTypeList(Integer.parseInt(gseq));
			
			wvo = bi.bodView(Integer.parseInt(bseq));
			
			request.setAttribute("LVL_BTVO_LIST", btlist);
			request.setAttribute("LVL_WVO", wvo);
			request.setAttribute("LVL_GVO", gvo);
			request.setAttribute("LVL_GSEQ", gseq);
			request.setAttribute("LVL_BTSEQ", btseq);
			
			
			RequestDispatcher rd = request.getRequestDispatcher("/alio/fuse/alio_bod_edit.jsp");
			rd.forward(request, response);
		} else if(action.equals("good")) {
			
			System.out.println("gseq : " + gseq);
			String bseq = request.getParameter("bseq");
			int btseq2 = Integer.parseInt(btseq);	//8		13		10		4
			int n = btseq2 / 6;  					//1		2		1		0
			int a = (6 * n) + 1; 					//7		13		7		1
			int modBtseq = a;
			System.out.println("해당 추천 게시판 : " + modBtseq);
			System.out.println("bseq : " + bseq);
			BodVO bvo = new BodVO();
			
			bvo.setBseq(Integer.parseInt(bseq));
			bvo.setBtseq(modBtseq);
			
			int res = 0;
			
			res = bi.bodGoodUpdate(bvo);
			
			System.out.println("추천게시판으로 " + res + "건 이동 완료!");
			
			if(res > 0) {
				response.sendRedirect("/bod?action=list&gseq="+gseq+"&btseq="+modBtseq);
			}
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Post Call------------------");
		
		response.setContentType("text/html; charset=UTF-8");
		Gson gson = new Gson();
		String action = request.getParameter("action");
		String gseq = request.getParameter("gseq");
		String btseq = request.getParameter("btseq");
		
		System.out.println("action = " + action);
		System.out.println("gseq = " + gseq);
		System.out.println("btseq = " + btseq);
		
		BodVO bvo = new BodVO();
		
		if(action.equals("delete")) {
			
			String bseq = request.getParameter("bseq");
			System.out.println("bseq = " + bseq);
			
			BodImp bi = new BodImp();
			
			int res = 0;
			
			res = bi.bodDelete(Integer.parseInt(bseq));
			
			if(res>0){
				response.sendRedirect("/bod?action=list&gseq="+gseq+"&btseq="+btseq);
			}
		} else if(action.equals("input")) {
			
			String cseq = request.getParameter("cseq");
			String btitle = request.getParameter("btitle");
			String bbody = request.getParameter("bbody");
			
			BodImp bi = new BodImp();
			bvo.setGseq(Integer.parseInt(gseq));
			bvo.setBtseq(Integer.parseInt(btseq));
			bvo.setCseq(Integer.parseInt(cseq));
			bvo.setBtitle(btitle);
			bvo.setBbody(bbody);
			bvo.setBfile(null);
			int res = 0;
			
			res = bi.bodInsert(bvo);
			
			if(res>0){
				response.sendRedirect("/bod?action=list&gseq="+gseq+"&btseq="+btseq);
			}
		} else if(action.equals("edit")) {
			
			String cseq = request.getParameter("cseq");
			String btitle = request.getParameter("btitle");
			String bbody = request.getParameter("bbody");
			String bseq = request.getParameter("bseq");
//			String btseqq = request.getParameter("btseqq");
					
			
			BodImp bi = new BodImp();
			
			bvo.setBseq(Integer.parseInt(bseq));
			bvo.setBtitle(btitle);
			bvo.setBbody(bbody);
			bvo.setBfile(null);
			int res = 0;
			
			res = bi.bodUpdate(bvo);
			
			System.out.println(res);
			
			if(res>0){
				response.sendRedirect("/bod?action=view&gseq="+gseq+"&btseq="+btseq+"&bseq="+bseq);
			}
		} else if(action.equals("insertForAjax")) {
			WrapperVO wvo = new WrapperVO();
			CrewVO cvo = new CrewVO();
			ReplyImp ri = new ReplyImp();
			String bseq = request.getParameter("bseq");
			String cseq = request.getParameter("cseq");
			String rbody = request.getParameter("rbody");
			ReplyVO rvo = new ReplyVO();
			String jsonStr = request.getParameter("MYKEY");
			System.out.println(jsonStr);
			rvo = gson.fromJson(jsonStr, ReplyVO.class);
			
			
			int bseq2 = rvo.getBseq();

			int row = ri.replyInsert(rvo);
			
			
			ArrayList<WrapperVO> rlist = ri.replyList(bseq2);
			//System.out.println(rlist.size() + "------------");
			//toJson : 자바객체->json(String문장)
			String res = gson.toJson(rlist);
			System.out.println(res);
			PrintWriter out = response.getWriter();
			out.println(res);
			
			
		} else if(action.equals("likeForAjax")) {
			LikeCheckImp lci = new LikeCheckImp();
			LikeCheckVO lvo = new LikeCheckVO();
			CrewVO cvo = new CrewVO();
			WrapperVO wvo = new WrapperVO();
			
			String bseq = request.getParameter("bseq");
			String cseq = request.getParameter("cseq");
			
			String jsonStr = request.getParameter("MYKEY");
//			System.out.println(jsonStr);
//			wvo = gson.fromJson(jsonStr, LikeCheckVO.class);
			
			JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(jsonStr);
			
			String bseq2 = element.getAsJsonObject().get("bseq").getAsString();
			String cseq2 = element.getAsJsonObject().get("cseq").getAsString();
			System.out.println("bseq2:---------"+ bseq2);
			System.out.println("cseq2:---------"+ cseq2);
			
			bvo.setBseq(Integer.parseInt(bseq2));
			cvo.setCseq(Integer.parseInt(cseq2));
			
			wvo.setBvo(bvo);
			wvo.setCvo(cvo);
			
			int row = lci.likeCheckInsert(wvo);
			int lcnt = lci.likeCheckCount(Integer.parseInt(bseq2));
			System.out.println(lcnt);
			PrintWriter  out = response.getWriter();
			out.println(lcnt);
			
		}else if(action.equals("editForAjax")) {

			ReplyVO rvo = new ReplyVO();
			ReplyImp ri = new ReplyImp();
//			Gson gson = new Gson();
			//--------------------------
			//2.JSON 검색조건 추출
			//--------------------------
			String jsonStr = request.getParameter("MYKEY");
			System.out.println(jsonStr);	//{"rseq":"0","rbody":"값0"}
	
	//		//fromJson : json(String문장)->자바객체
			rvo = gson.fromJson(jsonStr, ReplyVO.class);
			System.out.println("impl.update("+rvo.getRseq()+","+rvo.getRbody()); //ReplyImp 메서드 사용할 것
	
		//-----------------------------------------
		// 1건 수정
		//-----------------------------------------
//			int res = impl.update(vo);
			int res = ri.replyUpdate(rvo);
			
			PrintWriter  out = response.getWriter();
			out.println(res);

	} else if(action.equals("deleteForAjax")) {
			WrapperVO wvo = new WrapperVO();
			ReplyVO vo = new ReplyVO();
			BodImp bi = new BodImp();
			ReplyImp ri = new ReplyImp();
			
			
			
			String bseq = request.getParameter("bseq");
			//String rseq = request.getParameter("rseq");
			
			String jsonStr = request.getParameter("MYKEY");
			System.out.println(jsonStr);	//{"rseq":"0"}
			
			
			JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(jsonStr);
			
			String rseq = element.getAsJsonObject().get("rseq").getAsString();
			System.out.println(rseq);
			
			//System.out.println(jsonParser);
			
			//JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonStr);
			//System.out.print("rseq : " + jsonObject.get("rseq"));
			
			
			//Gson gson = new Gson();
			//String parsedjSon = gson.fromJson(json, int);
			//-----------------------------------------
			// 전체목록
			//-----------------------------------------
			//ReplyImpl impl = new ReplyImpl(); 
			//ArrayList<ReplyVO> rlist = impl.replyList();
//			wvo = bi.bodView(Integer.parseInt(bseq));
			
			
			int row = ri.replyDelete(Integer.parseInt(rseq));
			
			ArrayList<WrapperVO> rlist = ri.replyList(Integer.parseInt(bseq));
			//---------------------아래 for문을 impl로 대체할 것.
			
//			for(int i=0; i<5; i++) {
//				WrapperVO wvo = new WrapperVO();
//				ReplyVO rvo = new ReplyVO();
//				CrewVO cvo = new CrewVO();
//				rvo.setRseq(i);
//				rvo.setRbody("title"+i);
//				rvo.setRregdate("2017-03-01 221:20:17");
//				cvo.setCid("cid"+i);
//				wvo.setRvo(rvo);
//				wvo.setCvo(cvo);
//				rlist.add(wvo);
//			}
			
			//---------------------
	//		"[ {rseq:1, rbody:"제목1"},
	//		  {rseq:2, rbody:"제목2"},
	//		  {rseq:3, rbody:"제목3"} ]  "
	
			System.out.println(rlist.size() + "------------");
			//toJson : 자바객체->json(String문장)
			String res = gson.toJson(rlist);
			System.out.println(res);
			PrintWriter out = response.getWriter();
			out.println(res);
		}
	}

}
