package com.kosmo.board;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.kosmo.board.BoardVO;
import com.kosmo.common.ExcelTemplateWrite;
import com.kosmo.common.PagingUtil;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;


@Controller
public class BoardController { //extends MultiActionController {
	private static final long serialVersionUID = 1L;

	@Autowired
	private BoardService service;
	//	public void setBoardService(BoardService service) {
	//		this.service = service;
	//	}

	@Value("${upload_file_max_size}")
	int upload_file_max_size;

	@Value("${upload_file_dir}")
	String upload_file_dir;

	@Value("${upload_file_format}")
	String upload_file_format;


	/** post 출력 **/
	@RequestMapping(value = "/googleAPI.do", method = RequestMethod.POST)
	//public ResponseEntity<String> postSendResult(\
	public ResponseEntity<ArrayList<String>> postSendResult(
			@RequestHeader Map<String,String> headers,
			@RequestBody String body) {
		System.out.println("요청처리중....넘어온 파라미터:::: " + body);
		for (String elem: headers.keySet()) {
			System.out.println(elem + " : " + headers.get(elem));
		}
		ArrayList<String> list = new ArrayList<String>();
		list.add("1111");
		list.add("2222");
		list.add("3333");


		//return new ResponseEntity<String>("OKOK", HttpStatus.OK);
		return new ResponseEntity<ArrayList<String>>(list, HttpStatus.OK);
	}



	/** post 출력 **/
	@RequestMapping(value = "/post.do", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> postSend(
			@RequestParam(value="mid") String mid,
			@RequestParam(value="mpw") String mpw
			) throws Exception {
		String myResult = "";
		System.out.println("요청받기 :::: mid:" + mid + ",mpw" + mpw);

		try {
			//--------------------------
			//   URL 설정하고 접속하기
			//--------------------------
			URL url = new URL("http://192.168.0.13/googleAPI.do"); //Google. api url
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setDefaultUseCaches(false);
			http.setDoInput(true);
			http.setDoOutput(true);
			http.setRequestMethod("POST");
			http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

			http.setRequestProperty("Accept-Encoding", "identity");
			http.setRequestProperty("Content-Length", http.getContentLength()+"");
			//int contentLength = connection.getContentLength();

			//response.addHeader("Content-Length",  response.setContentLength());


			//key=value&key=value
			StringBuffer buffer = new StringBuffer();
			buffer.append("id").append("=").append(mid).append("&");	//파라미터
			buffer.append("pword").append("=").append(mpw);				//파라미터

			//OutputStreamWriter osw = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
            OutputStreamWriter osw = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
            PrintWriter pw = new PrintWriter(osw);
            pw.write(buffer.toString());
            pw.flush();


            //구글에서 온 답변 받기
            int responseCode = http.getResponseCode();
    		System.out.println("응답받기 ::::  " + responseCode);
    		if (responseCode == HttpURLConnection.HTTP_OK) { //success
				InputStreamReader isr = new InputStreamReader(http.getInputStream(), "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				StringBuilder builder = new StringBuilder();
				String inputLine;
				ArrayList resList = new ArrayList();
				while ((inputLine = br.readLine()) != null) {
					builder.append(inputLine + "\n");
					resList.add(inputLine);
				}
				br.close();
				myResult = builder.toString();
				System.out.println(resList.size() + "건 결과 받기");
    		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(myResult, HttpStatus.OK);
	}




	/**
	 * BD : 게시판 등록
	 */
	//consumes : 입력  데이터 포맷                                :  HTTP헤더의 Accept
	//produces : 출력  데이터 포맷  "application/json" :  HTTP헤더의 Content-Type
	@RequestMapping(value = "/ajaxinsert.do", method = RequestMethod.POST)
	//,  consumes="application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Map<String, String>> ajaxBoardInsert(
			@ModelAttribute BoardVO boardVO
			) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		int res = 0;
		try {
			res = service.boardInsert(boardVO);
		} catch(Exception e) {
			map.put("ERROR", e.getMessage());
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
		}
		map.put("SUCCESS", res+"");
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
	}

	//	/**
	//	 * BD : 게시판 목록
	//	 */
	//	@RequestMapping(value = "/ajaxlist.do", method = RequestMethod.POST)
	//	@ResponseBody
	//	public Map<String, Object> ajaxBoardList(
	//			@RequestBody BoardVO boardVO
	//	) throws Exception {
	//		//전체 게시물 수 가져오기
	//		int totalCount = service.boardCount();
	//		//게시물 목록 가져오기
	//		ArrayList<BoardVO> list = service.boardList(boardVO.getSseq(), boardVO.getEseq()); //, vo.getSearchGubun(), vo.getSearchStr());
	//		if (list == null) {
	//			//throw new NotFoundException("selectBoardList null");
	//        }
	//		Map<String, Object> map = new HashMap<String, Object>();
	//		map.put("LVL_CNT", totalCount);
	//		map.put("LVL_LIST", list);
	//
	//		//{ LVL_CNT:20, LVL_LIST: [{bseq:1;btitle:제목},{bseq:1;btitle:제목},{bseq:1;btitle:제목}] }
	//        return map;
	//	}


	/**
	 * BD : 게시판 목록
	 */
	@RequestMapping(value = "/ajaxlist.do", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<BoardVO> ajaxBoardList(
			@RequestBody BoardVO boardVO
			) throws Exception {
		//전체 게시물 수 가져오기
		int totalCount = service.boardCount();
		//게시물 목록 가져오기
		ArrayList<BoardVO> list = service.boardList(boardVO.getSseq(), boardVO.getEseq()); //, vo.getSearchGubun(), vo.getSearchStr());
		if (list == null) {
			//throw new NotFoundException("selectBoardList null");
		}
		//		Map<String, Object> map = new HashMap<String, Object>();
		//		map.put("LVL_CNT", totalCount);
		//		map.put("LVL_LIST", list);

		//{ LVL_CNT:20, LVL_LIST: [{bseq:1;btitle:제목},{bseq:1;btitle:제목},{bseq:1;btitle:제목}] }
		return list;
	}




	/**
	 * BD : 게시판 상세보기
	 */
	@RequestMapping(value = "/ajaxdetail.do", method = RequestMethod.POST)

	public ResponseEntity<BoardVO> ajaxBoardDetail(
			@RequestBody BoardVO boardVO
			) throws Exception {
		boardVO = service.boardDetail(boardVO.getBseq());
		if (boardVO == null) {
			//throw new NotFoundException("selectBoardOne null");
		}
		return new ResponseEntity<BoardVO>(boardVO, HttpStatus.OK);
	}

	/**
	 * BD : 게시판 상세보기
	 */
	@RequestMapping(value = "/ajaxdetail.do/{bseq}/{mseq}", method = RequestMethod.GET)
	public ModelAndView ajaxBoardDetail(
			//@RequestParam("bseq") int bseq, //?bseq=141
			@PathVariable("bseq") int bseq, //deatil.do/141
			@PathVariable("mseq") int mseq
			) throws Exception {
		System.out.println("mseq");
		BoardVO boardVO = service.boardDetail(bseq);
		if (boardVO == null) {
			//throw new NotFoundException("ajaxBoardDetail null");
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("LVL_VO", boardVO);
		mav.setViewName("board/board_detail");
		return mav;
	}


	/**
	 * 엑셀 파일 생성
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/excel.do")
	public void boardListExcel(HttpServletRequest request, HttpServletResponse response) {

		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		int cnt = 0;

		list = service.boardList(1, 50);
		cnt = service.boardCount();
		System.out.println("list.size : " + list.size());

		//ModelAndView mav = new ModelAndView();
		//mav.addObject("LVL_BLIST", list);
		//mav.addObject("LVL_CNT", cnt);
		//mav.setViewName("board/list");

		Map<String , Object> hashMap = new HashMap<String , Object>();
		hashMap.put("LVL_BLIST", list);
		hashMap.put("LVL_CNT", cnt);

		ExcelTemplateWrite excel = new ExcelTemplateWrite();
		excel.makeExcelByTemplate(request, response, hashMap, excel.get_Filename("my_excel_file.xlsx"), "board_template.xlsx");
	}



	@RequestMapping("/chart.do")
	public void drawChart(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("image/png");

		DefaultPieDataset set = new DefaultPieDataset();

		set.setValue("식품",20);
		set.setValue("가전",50);
		set.setValue("의류",30);

		JFreeChart chart = ChartFactory.createPieChart3D("title", set, true,  true, false);
		//한글처리
		chart.setTitle("이건제목");
		chart.getTitle().setFont(new Font("돋움", Font.BOLD, 20));
		chart.getLegend().setItemFont(new Font("돋움", Font.BOLD, 20));

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		//한글처리
		plot.setLabelFont(new Font("돋움", Font.BOLD, 8));

		try{
			ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, 500, 300);
			response.getOutputStream().close();

			String fileName = ServletUtilities.saveChartAsPNG(chart, 600, 350, null);
			//fileName = ServletUtilities.saveChartAsPNG(chart, 600, 350, session);

			StringBuffer buffer = new StringBuffer("http://");
			buffer.append(request.getServerName()+":"+request.getServerPort());
			buffer.append(request.getContextPath());
			buffer.append("/chart.do?filename=");
			buffer.append(fileName);
			System.out.println(buffer.toString());
			//http://127.0.0.1:80/chart.do?filename=jfreechart-onetime-3759428402627633663.png

		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	@RequestMapping("/chartimg.do")
	public ModelAndView chartimg(HttpServletRequest request, HttpServletResponse response) {
		DefaultPieDataset set = new DefaultPieDataset();
		set.setValue("식품",20);
		set.setValue("가전",50);
		set.setValue("의류",30);

		JFreeChart chart = ChartFactory.createPieChart3D("title", set, true,  true, false);
		//한글처리
		chart.setTitle("이건제목");
		chart.getTitle().setFont(new Font("돋움", Font.BOLD, 20));
		chart.getLegend().setItemFont(new Font("돋움", Font.BOLD, 20));

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		//한글처리
		plot.setLabelFont(new Font("돋움", Font.BOLD, 8));


		String fileName;
		ModelAndView mav = new ModelAndView();

		try {
			fileName = ServletUtilities.saveChartAsPNG(chart, 600, 350, null);
			//fileName = ServletUtilities.saveChartAsPNG(chart, 600, 350, session);
			StringBuffer buffer = new StringBuffer("http://");
			buffer.append(request.getServerName()+":"+request.getServerPort());
			buffer.append(request.getContextPath());
			buffer.append("/chart.do?filename=");
			buffer.append(fileName);
			System.out.println(buffer.toString());
			//http://127.0.0.1:80/chart.do?filename=jfreechart-onetime-3759428402627633663.png

			mav.addObject("LVL_CHART_URL", buffer.toString());
			mav.setViewName("board/chart");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return mav;
	}
	//
	//	//BufferedImage img = draw(chart, 300,200);
	//	 protected static BufferedImage draw(JFreeChart chart, int width, int height) {
	//        BufferedImage img = new BufferedImage(width , height, BufferedImage.TYPE_INT_RGB);
	//        Graphics2D g2 = img.createGraphics();
	//        chart.draw(g2, new Rectangle2D.Double(0, 0, width, height));
	//        g2.dispose();
	//        return img;
	//	}


	/**
	 * 목록 + 댓글
	 * @return ArrayList<BoardVO>
	 */
	@RequestMapping(value = "/brlist.do", method = RequestMethod.GET)
	public ModelAndView brlist(HttpServletRequest request
			, HttpServletResponse response)
	{
		ArrayList<BoardVO> list = service.boardListAndReplyList();
		ModelAndView mav = new ModelAndView();
		mav.addObject("LVL_LIST", list);
		mav.setViewName("board/board_reply_list");
		return mav;
	}

	/**
	 * 목록 + 댓글 using VIEW
	 * @return ArrayList<BoardVO>
	 */
	@RequestMapping(value = "/br.do", method = RequestMethod.GET)
	public ModelAndView br() throws Exception {
		ArrayList<BoardVO> list = service.brList();
		ModelAndView mav = new ModelAndView();
		mav.addObject("LVL_LIST", list);
		mav.setViewName("board/br_list");
		return mav;
	}




	/**
	 * 상세보기 + 댓글
	 * @return ArrayList<BoardVO>
	 */
	@RequestMapping(value = "/brdetail.do", method = RequestMethod.GET)
	public ModelAndView brdetail(BoardVO vo)
	{
		vo = service.boardDetailAndReplyList(vo.getBseq());
		ModelAndView mav = new ModelAndView();
		mav.addObject("LVL_VO", vo);
		mav.setViewName("board/board_reply_detail");
		return mav;
	}




	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request
			, HttpServletResponse response)
	{

		int currentPage = 1;


		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}

		//BoardDAO impl = new BoardDAO();
		int totalCount = service.boardCount();


		//------------페이징
		PagingUtil pu
		= new PagingUtil("/list.do?"
				, currentPage
				, totalCount  //------------
				, 2	//선택한 2번 블럭에 나타날 게시물 갯수
				, 2 // 1 2 [다음]
				);
		String  html = pu.getPagingHtml();



		ArrayList<BoardVO> list = service.boardList(pu.getStartSeq(), pu.getEndSeq());

		ModelAndView mav = new ModelAndView();

		mav.addObject("LVL_COUNT", totalCount);
		mav.addObject("LVL_LIST", list);
		mav.addObject("LVL_PAGING", html);

		mav.setViewName("board/board_list");
		return mav;
	}

	@RequestMapping(value = "/detail.do", method = RequestMethod.GET)
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response)
	{
		String bseq = request.getParameter("bseq");
		//BoardDAO impl = new BoardDAO();
		BoardVO vo = new BoardVO();
		vo = service.boardDetail(Integer.parseInt(bseq));

		ModelAndView mav = new ModelAndView();
		mav.addObject("LVL_VO", vo);
		mav.setViewName("board/board_detail");
		return mav;
	}


	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public ModelAndView edit(
			HttpServletRequest request,
			HttpServletResponse response
			)
	{
		String bseq = request.getParameter("bseq");
		//BoardDAO impl = new BoardDAO();
		BoardVO vo = new BoardVO();
		vo = service.boardDetail(Integer.parseInt(bseq));

		ModelAndView mav = new ModelAndView();
		mav.addObject("LVL_VO", vo);
		mav.setViewName("board/board_edit");
		return mav;
	}





	@RequestMapping(value = "/insert.do", method = RequestMethod.POST)
	public String insert(BoardVO vo) throws IOException {

		//			HttpSession session = request.getSession();
		//신규첨부파일
		MultipartFile ufile = vo.getUfile();
		if(ufile != null) {
			String fullPath = upload_file_dir+"\\"+ufile.getOriginalFilename();
			File newFile = new File(fullPath); //파일생성
			ufile.transferTo(newFile);
			vo.setBfile_size(ufile.getSize()); //newFile.length());
			vo.setBfile_path(upload_file_dir);
			vo.setBfile_name(ufile.getOriginalFilename());
		}

		int res = service.boardInsert(vo);
		System.out.println(res + "건 입력");
		return "redirect:/list.do";

	}



	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public String update(BoardVO vo)
			throws IOException
	{


		MultipartFile ufile = vo.getUfile();
		int res = 0;
		if(ufile == null) {		//기존 첨부파일 유지
			res = service.boardUpdate(vo);
		} else {				//신규 첨부파일 등록
			//시스템에 기존 첨부파일 삭제
			File oldFile = new File(vo.getBfile_path()+"/"+vo.getBfile_name()); //파일생성
			if(oldFile.exists())
				oldFile.delete();

			//시스템에 신규 첨부파일 생성
			String fullPath = upload_file_dir+"\\"+ufile;
			File newFile = new File(fullPath); //파일생성
			vo.setBfile_size(ufile.getSize());
			vo.setBfile_path(upload_file_dir);
			vo.setBfile_name(ufile.getOriginalFilename());

			//DB에 게시물 등록
			res = service.boardUpdateForUpload(vo);
		}
		return "redirect:/detail.do?bseq="+vo.getBseq();
	}

	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public String delete(BoardVO vo)
	{
		//BoardDAO impl = new BoardDAO();
		//기존 첨부파일 삭제

		File oldFile = new File(vo.getBfile_path()+"/"+vo.getBfile_name()); //파일생성
		if(oldFile.exists())
			oldFile.delete();

		//DB에 게시물 지우기
		int res = service.boardDelete(vo.getBseq());
		if(res > 0) {
			return "redirect:/list.do";

		}else {
			return "redirect:/detail.do?bseq="+vo.getBseq();
		}
	}

}
