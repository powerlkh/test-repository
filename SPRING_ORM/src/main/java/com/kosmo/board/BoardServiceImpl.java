package com.kosmo.board;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosmo.board.mapper.BoardMapper;

@Service("service")
public class BoardServiceImpl implements BoardService{
	@Autowired
//	@Qualifier("dao")
	private BoardMapper dao;


//	public void setBoardDAO(BoardDAO dao) {
//		this.dao = dao;
//	}
	public ArrayList<BoardVO> brList(){
		return dao.brList();
	}

	public int boardCount() {
		return dao.boardCount();
	}

	/**
	 * 목록 + 댓글
	 * @return ArrayList<BoardVO>
	 */
	public ArrayList<BoardVO> boardListAndReplyList() {
		return dao.boardListAndReplyList();
	}

	/**
	 * 상세보기 + 댓글
	 * @return ArrayList<BoardVO>
	 */
	public BoardVO boardDetailAndReplyList(int bseq) {
		return dao.boardDetailAndReplyList(bseq);
	}


	/**
	 * 목록
	 * @return ArrayList<BoardVO>
	 */
	public ArrayList<BoardVO> boardList(int sseq, int eseq) {
		return dao.boardList(sseq, eseq);
	}


	public ArrayList<BoardVO> boardList(int sseq, int eseq, String searchGubun, String searchStr) {
		return dao.boardListForSearch(sseq, eseq, searchGubun, searchStr);
	}


	/**
	 * 수정
	 * @param BoardVO
	 * @return int
	 */
	public int boardUpdate(BoardVO vo) {
		return dao.boardUpdate(vo);
	}
	/**
	 * 수정하기(첨부파일용)
	 * @param vo
	 * @return
	 */
	public int boardUpdateForUpload(BoardVO vo){
		return dao.boardUpdateForUpload(vo);
	}


	/**
	 * 삭제
	 * @param BoardVO
	 * @return int
	 */
	 public int boardDelete(int bseq) {
		 return dao.boardDelete(bseq);
	 }
	/**
	 * 입력
	 * @param BoardVO
	 * @return int
	 * @throws Exception
	 */
	 public int boardInsert(BoardVO vo) { //throws Exception{
		 int res = 0;
		 res = dao.boardInsert(vo);
//		 System.out.println("1건 입력");
//		 int num = 10 / 0;
//
//		 res = dao.boardInsert(vo);
//		 System.out.println("2건 입력");
//		 //throw new Exception("강제 오류");
		 return res;

	 }



	/**
	 * 상세보기
	 * @param BoardVO
	 * @return BoardVO
	 */
	 //public HashMap<String, Object> boardDetail(int bseq) {
	 public BoardVO boardDetail(int bseq) {
		 return dao.boardDetail(bseq);
//		                           dao.boardCountUpdate()
//		 BoardVO vo              = dao.boardDetail(bseq);
//		 ArrayList<ReplyVO> rlist = dao.boardListAndReplyList();
//
//		 HashMap<String,Object > map = new HashMap<String,Object>();
//		 map.put("LVL_VO", vo);
//		 map.put("LVL_RLIST", rlist);
//
//
//		 return map;

	 }
}
