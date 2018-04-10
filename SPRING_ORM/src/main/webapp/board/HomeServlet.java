package com.insta.content;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.insta.db.DBConnect;
import com.insta.follower.FollowerVO;
import com.insta.hashtag.HashTagImpl;
import com.insta.member.MemberVO;
import com.insta.reply.ReplyVO;

@WebServlet("/homeList")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String loginedUser = (String) request.getSession().getAttribute("loginedUser");
		int mseq = (int) request.getSession().getAttribute("mseq");
		request.setAttribute("loginedUser", loginedUser);
		
		HashTagImpl impl = new HashTagImpl();
		
		//내가 팔로우한 사람 리스트
		Connection conn = null;
		DBConnect db = new DBConnect();
		
		
		try{
			//DB connection 1번 열기
			conn=db.dbConn();
			
			ArrayList<FollowerVO> boardList = impl.boardList();
			
			
				for(int i=0; i<boardList.size();i++){
					int bseq = boardList.get(i).getBSeq();
					ArrayList<ContentVO> replyList = impl.replyList(2);
					
						allList.add(replyList);
						
						
					
				
				
//				나를 팔로우한 사람 리스트
				ArrayList<FollowerVO> requesterList = impl.requesterList(mseq, conn);
				ArrayList<ContentVO> contentList2 = new ArrayList<>();
				if(requesterList.size()>0){
					for(int i=0; i<requesterList.size(); i++){
						int followerSeq = requesterList.get(i).getFollowerSeq();
						ArrayList<ContentVO> tempList = impl.selectMemberSeqList(followerSeq, conn);
						if(tempList.size()>0){
							contentList2.addAll(tempList);
						}
					}
				}
				
				ArrayList<MemberVO> listM = new ArrayList<>();
				listM = impl.memberList(conn);
				ArrayList<String> listName = new ArrayList<>();
				for(int i=0; i<listM.size(); i++ ){
					listName.add(listM.get(i).getName());
				}
				
				request.setAttribute("listName", listName);
				request.setAttribute("followerList", contentList);
				request.setAttribute("requesterList", contentList2);
				RequestDispatcher rd = request.getRequestDispatcher("/insta/insta-home.jsp");
				rd.forward(request, response);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			//DB connection 1번 닫기
			db.dbClose(conn);
		}
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
