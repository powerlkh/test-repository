package com.kosmo.board;
import java.io.IOException;
import java.util.ArrayList;


public interface BoardService {

/**
 *
 * @return
 */
public int boardCount();

/**
 * 목록 + 댓글
 * @return ArrayList<BoardVO>
 */
public ArrayList<BoardVO> boardListAndReplyList();

/**
 * 상세보기 + 댓글
 * @return ArrayList<BoardVO>
 */
public BoardVO boardDetailAndReplyList(int bseq);

public ArrayList<BoardVO> brList();

/**
 * 목록
 * @return ArrayList<BoardVO>
 */
public ArrayList<BoardVO> boardList(int sseq, int eseq);
public ArrayList<BoardVO> boardList(int sseq, int eseq, String searchGubun, String searchStr);

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
 public int boardInsert(BoardVO vo) ; //throws Exception;
/**
 * 상세보기
 * @param BoardVO
 * @return BoardVO
 */
 public BoardVO boardDetail(int bseq);
}
