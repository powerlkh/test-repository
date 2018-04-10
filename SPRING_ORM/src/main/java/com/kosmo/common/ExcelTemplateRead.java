package com.kosmo.common;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelTemplateRead {

	public static List<Map<String, String>> getListByMultiSheet(ExcelVO excelVO) {
		//엑셀파일을 읽어 들인다.
		//Name: /xl/workbook.xml
		//Content Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml
		Workbook wb = ExcelVO.getWorkbook(excelVO.getFilePath());

		int sheetNum = wb.getNumberOfSheets(); //시트 수
		int numOfCells = 0;

		Row row = null;
		Cell cell = null;
		String cellName = "";
		Map<String, String> map = null;

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		//엑셀 내 시트 수 만큼 반복
		for(int i =0; i<sheetNum; i++){
			System.out.println("Sheet name : "+ wb.getSheetName(i));
			System.out.println("데이터가 있는 Sheet의 수 :" + wb.getNumberOfSheets());

			//Name: /xl/worksheets/sheet1.xml
			//Content Type: application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml
			Sheet sheet = wb.getSheetAt(i);

			int numOfRows = sheet.getPhysicalNumberOfRows();		//sheet에서 데이터가 있는 행 개수
			// 해당 시트 내 레코드(row) 수 만큼 반복
			for(int rowIndex = excelVO.getStartRow() - 1; rowIndex < numOfRows; rowIndex++) {
				row = sheet.getRow(rowIndex);

				if(row != null) {
					numOfCells = row.getPhysicalNumberOfCells();	//가져온 Row의 Cell의 개수
					map = new HashMap<String, String>();
					for(int cellIndex = 0; cellIndex < numOfCells; cellIndex++) {
						cell = row.getCell(cellIndex);

						if( cell == null ) {
							numOfCells++;
							continue;
						}

						cellName = excelVO.getColumnName(cell, cellIndex);
						if( !excelVO.getOutputColumns().contains(cellName) ) {
							continue;
						}
						map.put(cellName, excelVO.getCellValue(cell));	//key : cell Name
					}
					list.add(map);
				}
			}
		}
		return list;
	}


	public static List<Map<String, String>> getListByOneSheet(ExcelVO excelVO) {
		//엑셀파일을 읽어 들인다.
		Workbook wb = ExcelVO.getWorkbook(excelVO.getFilePath());

		//엑셀 파일에서 첫번째 시트를 가지고 온다.
		Sheet sheet = wb.getSheetAt(0);
		System.out.println("Sheet name: "+ wb.getSheetName(0));
		System.out.println("데이터가 있는 Sheet의 수 :" + wb.getNumberOfSheets());


		int numOfRows = sheet.getPhysicalNumberOfRows();			//sheet에서 데이터가 있는 행 개수
		int numOfCells = 0;

		Row row = null;
		Cell cell = null;
		String cellName = "";
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		// 해당 시트 내 레코드(row) 수 만큼 반복
		for(int rowIndex = excelVO.getStartRow() - 1; rowIndex < numOfRows; rowIndex++) {
			row = sheet.getRow(rowIndex);

			if(row != null) {
				numOfCells = row.getPhysicalNumberOfCells(); 		//가져온 Row의 Cell의 개수
				map = new HashMap<String, String>();
				for(int cellIndex = 0; cellIndex < numOfCells; cellIndex++) {
					cell = row.getCell(cellIndex);
					if( cell == null ) {
						numOfCells++;
						continue;
					}

					cellName = excelVO.getColumnName(cell, cellIndex);
					if( !excelVO.getOutputColumns().contains(cellName) ) {
						continue;
					}
					map.put(cellName, excelVO.getCellValue(cell));		//key : cell Name
				}
				list.add(map);
			}
		}
		return list;
	}



	public static void main(String[] args) {

		ExcelVO vo = new ExcelVO();
		//vo.setFilePath("D:\\workspaceJava\\07.SPRING_UTIL\\src\\main\\webapp\\xls_templete\\sample_template.xlsx");
		vo.setFilePath("D:\\download\\34기 수업자료 -------\\SPRING\\SPRING WEB UITL\\excel poi\\sample_data.xlsx");

		vo.setOutputColumns("B", "C", "D", "E", "F");
		vo.setStartRow(2);

		List<Map<String, String>> list = ExcelTemplateRead.getListByOneSheet(vo);  //ExcelUtil.getListByOneSheet(vo);

		//for(Map<String, String> map : list) {

		Map<String, String> resMap = new HashMap<String, String>();
		for(int i=0; i<list.size(); i++) {
			resMap = (Map<String, String>)list.get(i);
			System.out.println(resMap.get("B") +"\t"+ resMap.get("C") +"\t"+ resMap.get("D") +"\t"+ resMap.get("E") +"\t"+ resMap.get("F"));
		}
	}



}
