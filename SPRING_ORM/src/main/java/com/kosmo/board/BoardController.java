package com.kosmo.board;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.kosmo.board.BoardVO;
import com.kosmo.common.ExcelTemplateWrite;
import com.kosmo.common.PagingUtil;
import com.kosmo.util.Converter;
import com.kosmo.util.PDFUtil;
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



	@RequestMapping(value="/converter.do")
	//public  ResponseEntity<InputStreamResource> converter(
	//public ResponseEntity<byte[]> converter(
	//public ResponseEntity<OutputStream> converter(
	public ResponseEntity<String> converter(
			HttpServletRequest request, HttpServletResponse response
			)
					throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		String baseDir = "D:\\git_repository\\SPRING_ORM\\src\\main\\webapp\\uploads\\";
		String origFile = request.getParameter("origFile");
		String origFilePath = baseDir+origFile;
		String converterFile = origFile+".pdf";
		String convertFilePath = baseDir+converterFile;
		String ext = "";
		if (origFile != null) {
			ext = StringUtils.lowerCase(FilenameUtils.getExtension(origFile));


			PDFUtil util = new PDFUtil();
			util.ConvertDocToPDF(origFilePath, convertFilePath);

			System.out.println("done");


//			response.setContentType("image/png");
//			response.addHeader("Content-disposition", "attachment;filename="+converterFile);
//			InputStream is = new URL(origFilePath).openStream();	//new FileInputStream(new File(origFilePath));
//			OutputStream os = response.getOutputStream(); 			//new FileOutputStream(new File(convertFilePath));
//			switch (ext) {
//			case "ppt":
//				Converter.ppt2png(is, os);
//				break;
//			case "pptx":
//				Converter.pptx2png(is, os);
//				break;
//			case "docx":
//				Converter.docx2png(is, os); ; //response.getOutputStream());
//				break;
//			case "pdf":
//				Converter.pdf2pngUsingPdfBox(is, os);
//				break;
//			default:
//				response.setContentType("text/plain");
//				response.getWriter().println("Unknown format: " + ext);
//				//result = "Unknown format: " + ext;
//			}
		} else {
			response.setContentType("text/plain");
			response.getWriter().println("origFilePath parameter invalied");
		}

		return new ResponseEntity<String>(converterFile, HttpStatus.OK);
	}



	/*@ResponseBody
    @RequestMapping(value = "/downloadFile.do")
    public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {

        InputStream in = null;
        ResponseEntity<byte[]> entity = null;

        try {
            String formatName = fileName.substring(fileName.lastIndexOf("." ) + 1);
            MediaType mType = MediaUtils.getMediaType(formatName);

            HttpHeaders headers = new HttpHeaders();

            in = new FileInputStream("c:\\" + fileName);
            if (mType != null) {
                headers.setContentType(mType);
            } else {
                fileName = fileName.substring(fileName.indexOf("_") + 1);
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.add("Content-Disposition", "attatchment; filename=\"" +
                        new String(fileName.getBytes("UTF-8"), "ISO-8859-1") +
                        "\"");
            }
            entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
        } catch(Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
        } finally {
            in.close();
        }

        return entity;
    }*/



	@RequestMapping(value = "/get_goole_api.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> getGoogleJsonData(
			//@RequestParam(value="bseq") int bseq
			) throws Exception {
		//System.out.println("요청받기 :::: bseq:" + bseq);
		String myResult = "";
		int res = 0;

		String addr = "https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJod7tSseifDUR9hXHLFNGMIs&key=AIzaSyAX9qYtgvZNmWDKq3vYec6VQHVQld-yTlE";
		String crollingStr  = getWebCrolling(addr);
		String picAddr = "";

		//---------------------------------------------------------------
		// 자바단에서 파싱..
		//---------------------------------------------------------------
		Gson gson = new Gson();
		HotSpotsVO hsvo = gson.fromJson(crollingStr, HotSpotsVO.class);

		//			console.log(jsonObj.result.international_phone_number);
		//			console.log(jsonObj.result.opening_hours.weekday_text[0]);
		//			console.log(jsonObj.result.photos[0].photo_reference);
		//			console.log(jsonObj.result.reviews[0].author_name);


		//			//11111111111111
		//			hsvo.setInternationalPhoneNumber(hsvo.getResult().get("international_phone_number").toString());
		//
		//			//22222222222222
		//			LinkedTreeMap tmap = (LinkedTreeMap)hsvo.getResult().get("opening_hours");
		//			ArrayList<String> weekday_text = (ArrayList)tmap.get("weekday_text");
		//			hsvo.setWeekdayText(weekday_text.get(0));

		//3333333333333
		ArrayList photos = (ArrayList)hsvo.getResult().get("photos");
		ArrayList photoReference = new ArrayList();
		String photo_reference = "";
		ArrayList pictureList = new ArrayList();

		for(int i=0; i<1; i++) {
			//for(int i=0; i<photos.size(); i++) {
			photo_reference = (String)((LinkedTreeMap)photos.get(0)).get("photo_reference");
			photoReference.add(photo_reference);


			//---------------------------------------------------
			//HttpResponse.Header에서 location 정보 가져오기
			//3가지 방법 중 하나.... 마지막방법은 됨....
			//---------------------------------------------------
			picAddr = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=1600&photoreference=" + photo_reference;
			String locationStr = getResponseHeader(picAddr);
			pictureList.add(locationStr);
			System.out.println(locationStr);



			//				RestTemplate template = new RestTemplate();
			//				HttpEntity<String> response = template.getForEntity(picAddr, String.class);
			//				String resultString = response.getBody();
			//				HttpHeaders headers = response.getHeaders();
			//				System.out.println(picAddr + "," + headers.get("location") + "--------header.get(location)");
			//
			//				ResponseEntity<String> entity = template.getForEntity(picAddr, String.class);
			//				String body = entity.getBody();
			//				URI u = entity.getHeaders().getLocation();
			//				System.out.println(u.toString() + "============URI.toString");
			//				//pictureList.add(u.toString());

		}
		hsvo.setPhotoReference(photoReference);

		//			//44444444444444
		//			ArrayList<LinkedTreeMap> reviews = ((ArrayList)hsvo.getResult().get("reviews"));
		//			ArrayList reviewsList = new ArrayList();
		//			for(int i=0; i<reviews.size(); i++) {
		//				HashMap map = new HashMap();
		//				map.put("authorName",  ((LinkedTreeMap)reviews.get(i)).get("author_name").toString());
		//				map.put("rating"	,  ((LinkedTreeMap)reviews.get(i)).get("rating").toString());
		//				map.put("text"		,  ((LinkedTreeMap)reviews.get(i)).get("text").toString());
		//				map.put("time"		,  ((LinkedTreeMap)reviews.get(i)).get("time").toString());
		//				reviewsList.add(map);
		//
		//			}
		//			hsvo.setReviewsList(reviewsList);

		System.out.println(hsvo.getInternationalPhoneNumber());
		System.out.println(hsvo.getWeekdayText());
		System.out.println(hsvo.getPhotoReference());
		System.out.println(hsvo.getReviewsList());

		//service.insertGoogleAPI(hsvo);
		//System.out.println(myResult);

		String message = "success";
		//			if(res <=0) message = "faild";


		return new ResponseEntity<String>(crollingStr, HttpStatus.OK);
	}


	//---------------------------------------------
	//URL 요청 결과 페이지를  문자열로 반환
	//단순 바디만 크롤링 하는 형태
	//---------------------------------------------
	private String getWebCrolling(String URL) {
		String myResult = "";
		int res = 0;

		URL url = null;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String addr = "https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJod7tSseifDUR9hXHLFNGMIs&key=AIzaSyAX9qYtgvZNmWDKq3vYec6VQHVQld-yTlE";
		String readLine = "";

		try {
			url = new URL(addr);
			br = new BufferedReader(new InputStreamReader(url.openStream()));
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine + "\n");
			}
			br.close();


		} catch(Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}




	private String getResponseHeader(String addr) {
		String location = "";
		//--------------------------
		//   URL 설정하고 접속하기
		//--------------------------
		try {
			//--------------------------
			//   URL 설정하고 접속하기
			//--------------------------
			addr = "https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJod7tSseifDUR9hXHLFNGMIs&key=AIzaSyAX9qYtgvZNmWDKq3vYec6VQHVQld-yTlE";
			URL url = new URL(addr); //Google. api url
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setDefaultUseCaches(false);
			http.setDoInput(true);
			http.setDoOutput(true);
			http.setRequestProperty("Accept-Encoding", "identity");
			http.setRequestMethod("GET");

			//구글에서 온 답변 받기
			Map<String, List<String>> headers = http.getHeaderFields();
			Iterator<String> it = headers.keySet().iterator();
			List<String> values =  headers.get("Content-Type"); //location");
			System.out.println(values.get(0));

		} catch (IOException e) {
			e.printStackTrace();
		}

		return location;
	}







	///get_goole_api_pic.do


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



	//---------------------------------------------
	//URL 요청 결과 페이지 + 응답 헤더를  문자열로 반환
	//---------------------------------------------
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
			//String addr = "https://maps.googleapis.com/maps/api/place/details/json
			//?placeid=ChIJod7tSseifDUR9hXHLFNGMIs&key=AIzaSyAX9qYtgvZNmWDKq3vYec6VQHVQld-yTlE";
			//--------------------------
			URL url = new URL("http://192.168.0.13/googleAPI.do"); //Google. api url
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setDefaultUseCaches(false);
			http.setDoInput(true);
			http.setDoOutput(true);
			http.setRequestMethod("POST");
			http.setRequestProperty("Accept-Encoding", "identity");
			//			http.setRequestMethod("GET");
			http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			http.setRequestProperty("Content-Length", http.getContentLength()+"");
			//response.addHeader("Content-Length",  response.setContentLength());


			//HTTP 바디에 key=value&key=value 값을 쓰기
			StringBuffer buffer = new StringBuffer();
			buffer.append("id").append("=").append(mid).append("&");	//파라미터
			buffer.append("pword").append("=").append(mpw);				//파라미터

			//OutputStreamWriter osw = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
			OutputStreamWriter osw = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
			PrintWriter pw = new PrintWriter(osw);
			pw.write(buffer.toString());
			pw.flush();


			//구글에서 온 답변 받기
			String location = http.getHeaderField("location");
			System.out.println(location);

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
				}
				br.close();
				myResult = builder.toString();


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
		System.out.println(list.size());
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

	@RequestMapping(value = "/forward1.do")
	public String forward1(@RequestParam("seq") int aeq,
			Model model) {
		model.addAttribute("LSEQ", aeq);
		return "forward:/forward2.do";
	}

	@RequestMapping(value = "/forward2.do")
	public void forward2(@RequestParam("seq") int beq, Model model) {
		System.out.println(beq + "---");
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
//		//신규첨부파일
//		MultipartFile ufile = vo.getUfile();
//		if(ufile != null) {
//			String fullPath = upload_file_dir+"\\"+ufile.getOriginalFilename();
//			File newFile = new File(fullPath); //파일생성
//			ufile.transferTo(newFile);
//			vo.setBfile_size(ufile.getSize()); //newFile.length());
//			vo.setBfile_path(upload_file_dir);
//			vo.setBfile_name(ufile.getOriginalFilename());
//		}

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
