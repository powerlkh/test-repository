package com.kosmo.common;



import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelTemplateWrite {
	static final Logger logger = LoggerFactory.getLogger(ExcelTemplateWrite.class);

	public ExcelTemplateWrite() {
		logger.info("ExcelUtil CLASS call");
	}


	public String get_Filename() {
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddmmmm");
		return ft.format(new Date());
	}

	public String get_Filename(String pre) {
		return pre + get_Filename();
	}

	/**
	 *  board_template.xlsx 읽어와 my_excel_file.xlsx 생성 후 저장
	 *  template 파일 위치 : /webapp/xls_templete/board_template.xlsx
	 */
	public void makeExcelByTemplate(HttpServletRequest request, HttpServletResponse response,
			Map<String , Object> map, String filename
			, String templateFile) {
		String tempPath = request.getSession().getServletContext().getRealPath("/xls_templete") ;

		try {
			InputStream is = new BufferedInputStream(new FileInputStream(tempPath + "\\" + templateFile));

			XLSTransformer transformer = new XLSTransformer();
			Workbook resultWorkbook = transformer.transformXLS(is, map);

			//Content-Disposition:attachment;filename=aa.xlsx
			response.setHeader("Content-Disposition", "attachment; filename=\"" + filename);
			OutputStream os = response.getOutputStream();
			resultWorkbook.write(os);

		//} catch (ParsePropertyException | InvalidFormatException | IOException ex) {
		} catch (Exception ex) {
			logger.error("MakeExcel : makeExcelByTemplate");
		}
	}


}
