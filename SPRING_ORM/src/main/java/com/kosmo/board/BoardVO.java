package com.kosmo.board;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

public class BoardVO {

	private MultipartFile ufile;


	private int bseq;
	private int rseq;



	private String btype;
	private String btitle;
	private String bcon;
	private int mseq;
	private String bfile_path;
	private String bfile_name;
	private long bfile_size;
	private String regdate;


	//댓글 담기용 -----------------------------
	private ArrayList<BoardReplyVO> rlist;
	public ArrayList<BoardReplyVO> getRlist() {
		return rlist;
	}
	public void setRlist(ArrayList<BoardReplyVO> rlist) {
		this.rlist = rlist;
	}
	//---------------------------------------


	private int totalCount;
	private String searchGubun;
	private String searchStr;
	private int sseq;
	private int eseq;



	public int getRseq() {
		return rseq;
	}
	public void setRseq(int rseq) {
		this.rseq = rseq;
	}
	public MultipartFile getUfile() {
		return ufile;
	}
	public void setUfile(MultipartFile ufile) {
		this.ufile = ufile;
	}
	public int getSseq() {
		return sseq;
	}
	public void setSseq(int sseq) {
		this.sseq = sseq;
	}
	public int getEseq() {
		return eseq;
	}
	public void setEseq(int eseq) {
		this.eseq = eseq;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getSearchGubun() {
		return searchGubun;
	}
	public void setSearchGubun(String searchGubun) {
		this.searchGubun = searchGubun;
	}
	public String getSearchStr() {
		return searchStr;
	}
	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}
	public int getBseq() {
		return bseq;
	}
	public void setBseq(int bseq) {
		this.bseq = bseq;
	}
	public String getBtype() {
		return btype;
	}
	public void setBtype(String btype) {
		this.btype = btype;
	}
	public String getBtitle() {
		return btitle;
	}
	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}
	public String getBcon() {
		return bcon;
	}
	public void setBcon(String bcon) {
		this.bcon = bcon;
	}
	public int getMseq() {
		return mseq;
	}
	public void setMseq(int mseq) {
		this.mseq = mseq;
	}
	public String getBfile_path() {
		return bfile_path;
	}
	public void setBfile_path(String bfile_path) {
		this.bfile_path = bfile_path;
	}
	public String getBfile_name() {
		return bfile_name;
	}
	public void setBfile_name(String bfile_name) {
		this.bfile_name = bfile_name;
	}
	public long getBfile_size() {
		return bfile_size;
	}
	public void setBfile_size(long bfile_size) {
		this.bfile_size = bfile_size;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
}
