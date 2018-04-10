package com.kosmo.board.mapper;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kosmo.board.BoardVO;

@Repository(value="dao")
public interface BoardMapper {


public int boardCount();

public ArrayList<BoardVO> boardListAndReplyList();

public BoardVO boardDetailAndReplyList(int bseq);


public ArrayList<BoardVO> boardListForSearch(
		 @Param("sseq") int sseq
		,@Param("eseq")  int eseq
		,@Param("searchGubun")  String searchGubun
		,@Param("searchStr")  String searchStr);



/**
 * 목록
 * @return ArrayList<BoardVO>
 */
public ArrayList<BoardVO> boardList(@Param("sseq") int sseq
							, @Param("eseq") int eseq);


public ArrayList<BoardVO> brList();




/**
 * 수정
 * @param BoardVO
 * @return int
 */
public int boardUpdate(BoardVO vo);
/**
 * 수정하기(첨부파일용)
 * @param vo
 * @return
 */
public int boardUpdateForUpload(BoardVO vo);


/**
 * 삭제
 * @param BoardVO
 * @return int
 */
 public int boardDelete(int bseq);
/**
 * 입력
 * @param BoardVO
 * @return int
 */
 public int boardInsert(BoardVO vo);
/**
 * 상세보기
 * @param BoardVO
 * @return BoardVO
 */
 public BoardVO boardDetail(int bseq);
}
