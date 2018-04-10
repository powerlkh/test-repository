package com.test.common.util;

import java.io.*;
import java.util.ArrayList;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/* xlsx */
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
 



/*
 * You can identify the BIFF version used in an XLS file from the Beginning Of File (BOF) record present in all BIFF version 5, 7, and 8 files. 
 * In BIFF4 and earlier versions, various records (other than the BOF record) have version information specified in the high-order byte of 
 * their record numbers. This was a redundant methodology, so for versions of BIFF after BIFF4, Excel obtains the BIFF version by reading 
 * the BOF record.
 */
//BIFF       Microsoft Office Excel version
//---------------------------------------------------
//BIFF5      Microsoft Excel version 5.0 (XL5)
//BIFF7      Microsoft Excel 95 (XL7) (also called Microsoft Excel version 7)
//BIFF8      Microsoft Excel 97 (XL8), Microsoft Excel 2000 (XL9), 
//           Microsoft Excel 2002 (XL10), Microsoft Office Excel 2003 (XL11), 
//           Microsoft Office Excel 2007 (XL12)


public class InsaCardParserForJava2 {

	private static ArrayList<String> exceptFileList = new ArrayList<String>();
	private static ArrayList<String> headerList = new ArrayList<String>();
	private static ArrayList<ArrayList<String>> insaCardList = new ArrayList<ArrayList<String>>();		
	private static String path="D:\\download\\인사카드";
	static boolean headerListAdd = true;
	static int startDataRow = 2;
	static int endDataRow = 20;
	
	
	public static void main(String[] args) {
		
		File dirFile = new File(path);
		File[] fileList = dirFile.listFiles();
		int idx = -1;
		String ext = "";
		
		for(File f : fileList) {
			String tempPath=f.getParent();
			String tempFileName=f.getName();
			idx = tempFileName.lastIndexOf(".");
			if (idx != -1) {
				ext  = tempFileName.substring(idx + 1);
			}
			
			
			if(f.isFile()) {
				if("xls".equals(ext)) {
					insaCardList.add(parseExcelForXls(f));
					System.out.println("Path="+tempPath +", FileName="+tempFileName);
				} else if("xlsx".equals(ext)) {
					insaCardList.add(parseExcelForXlsx(f));
					System.out.println("Path="+tempPath +", FileName="+tempFileName);
				} else {
					exceptFileList.add(tempFileName+":"+"엑셀파일이 아닙니다.");
				}
			} 
		}
		System.out.println(headerList.size() + "헤더" + insaCardList.size() + "," + fileList.length);

		//		//temp print ---------------------
		//		for(int i=0; i<insaCardList.size(); i++ ) {
		//			System.out.println(insaCardList.get(i).size() + ","+insaCardList.get(i));
		//		}
		System.out.println("\n\n\n총:" + fileList.length +" / 에러:" + exceptFileList.size());
		for(String fnmae : exceptFileList) {
			System.out.println(fnmae);
		}

		makeExcel();
	}


	public ArrayList<String>  getHeaderList() {
		return headerList;
	}


	public static void makeExcel(){	
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("mySheet");

		//		if("xls".equals(ext)) {
		//			HSSFWorkbook workbook = new HSSFWorkbook();
		//			HSSFSheet sheet = workbook.createSheet("mySheet");
		//		} else if("xlsx".equals(ext)) {
		//			XSSFWorkbook workbook = new XSSFWorkbook();
		//			XSSFSheet sheet = workbook.createSheet("mySheet");				
		//		}

		FileOutputStream outFile = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		
		try {
			//font
			HSSFFont cellFontStyle = workbook.createFont();
//			cellFontStyle.setColor(Font.COLOR_RED);
			cellFontStyle.setBold(true);
			//style
			HSSFCellStyle cellStyle = workbook.createCellStyle();    
			cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);    
			cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);     					
			cellStyle.setFont(cellFontStyle); 
			
			//출력 헤더 생성
			row = sheet.createRow(0);
			for(int h=0; h<headerList.size(); h++) {
				cell = row.createCell(h);
				cell.setCellStyle(cellStyle);    
				cell.setCellValue(headerList.get(h)+"");
			}

			//출력 row 생성
			ArrayList clist = new ArrayList();
			for(int i=0; i<insaCardList.size(); i++) {
				row = sheet.createRow(i+1);	//헤더행 이후부터 
				clist = insaCardList.get(i);
				for(int j=0; j<clist.size(); j++) {
					row.createCell(j).setCellValue(clist.get(j)+"");

				}
			}

			outFile = new FileOutputStream("D:\\download\\00000000.xls");
			workbook.write(outFile);
			outFile.close();
			System.out.println("파일생성 완료");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public static ArrayList<String> parseExcelForXlsx(File file) {
		ArrayList<String> cellList = new ArrayList<String>();
		InsaCardParserForJava2 thisc = new InsaCardParserForJava2();
		try {
			OPCPackage fs = OPCPackage.open(new FileInputStream(file));
			XSSFWorkbook workbook = new XSSFWorkbook(fs);

			int sheetNum = workbook.getNumberOfSheets();
			
			for (int k = 0; k < sheetNum; k++) {
				XSSFSheet sheet = workbook.getSheetAt(k);
				
				
				
				//row
				for (int r = startDataRow; r < endDataRow; r++) {
					XSSFRow row   = sheet.getRow(r);
					if (row != null) { 
						int cells = row.getPhysicalNumberOfCells();

						//cell 
						for (short c = 0; c < cells; c++) {
							XSSFCell cell  = row.getCell(c);

							if (cell != null) { 
								String value = getCellValue(cell);
								
								if("64".equals(cell.getCellStyle().getFillForegroundColor()+"")) {
									if(c>0 && "27".equals(row.getCell(c-1).getCellStyle().getFillForegroundColor()+"")) {
										cellList.add(value);
									} else {
										continue;
									}
								} else {
									if(headerListAdd == true) {
										if(value != null) {
											//다음 셀이 헤더셀, 다음셀 값있음 : 스킵
											//다음 셀이 헤더셀, 다음셀 값없음 : list담기
											//다음 셀이 헤더셀X, : list담기
											if("27".equals(row.getCell(c+1).getCellStyle().getFillForegroundColor()+"")) {
												if(getCellValue(row.getCell(c+1)) == null) {
													headerList.add(value);
												} else {
													continue;
												}
											} else {
												headerList.add(value);
											}
										}
									} 
								} //headerList 만들기
							} 
							
							
						} //end of cell
					}
				}//end of row	

				headerListAdd = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			exceptFileList.add(file.getName()+":" + e.getMessage());
		}
		return cellList;
	}

	public static ArrayList<String> parseExcelForXls(File file) {
		InsaCardParserForJava2 thisc = new InsaCardParserForJava2();
		ArrayList<String> cellList = new ArrayList<String>();
		//		CardVO cvo = new CardVO();
		//		HashMap<String, String> map = new HashMap<String, String>();
		//		ArrayList<HashMap<String, String>> rowList = new ArrayList<HashMap<String, String>>();

		try {
			//only used with .xls (Excel versions through 2003) documents.
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file)); 
			HSSFWorkbook workbook = new HSSFWorkbook(fs);
			
			
			int sheetNum = workbook.getNumberOfSheets();
			
			for (int k = 0; k < sheetNum; k++) {
				HSSFSheet sheet = workbook.getSheetAt(k);
				cellList = parsing(sheet);
				headerListAdd = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			exceptFileList.add(file.getName()+":" + e.getMessage());
		}
		return cellList;
	}

	public static ArrayList<String> parsing(HSSFSheet sheet) {
		ArrayList<String> cellList = new ArrayList<String>();
		//row
		for (int r = startDataRow; r < endDataRow; r++) {
			HSSFRow row   = sheet.getRow(r);
			if (row != null) { 
				int cells = row.getPhysicalNumberOfCells();

				//cell 
				for (short c = 0; c < cells; c++) {
					
					if (row.getCell(c) != null) { 
						String value = getCellValue(row.getCell(c));
						
						if("64".equals(row.getCell(c).getCellStyle().getFillForegroundColor()+"")) {
							if(c>0 && "27".equals(row.getCell(c-1).getCellStyle().getFillForegroundColor()+"")) {
								cellList.add(value);
							} else {
								continue;
							}
						} else {
							if(headerListAdd == true) {
								if(value != null) {
									//다음 셀이 헤더셀, 다음셀 값있음 : 스킵
									//다음 셀이 헤더셀, 다음셀 값없음 : list담기
									//다음 셀이 헤더셀X, : list담기
									if("27".equals(row.getCell(c+1).getCellStyle().getFillForegroundColor()+"")) {
										if(getCellValue(row.getCell(c+1)) == null) {
											headerList.add(value);
										} else {
											continue;
										}
									} else {
										headerList.add(value);
									}
								}
							} 
						} //headerList 만들기
					} 
					
					
				} //end of cell
			}
		}//end of row	
		return cellList;
	}

	//public String getCellValue(HSSFCell cell) {
	public static <T extends Cell> String getCellValue(T cell) {
		String value = null;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_FORMULA :
			value = cell.getCellFormula();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC :
			value = cell.getNumericCellValue()+"";
			break;
		case HSSFCell.CELL_TYPE_STRING :
			value = cell.getStringCellValue()+"";
			break;
		case HSSFCell.CELL_TYPE_BLANK :
			value = null;
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN :
			value = cell.getBooleanCellValue()+"";
			break;
		case HSSFCell.CELL_TYPE_ERROR :
			value = cell.getErrorCellValue()+"";
			break;
		default :
		}
		return value;
	}
}
