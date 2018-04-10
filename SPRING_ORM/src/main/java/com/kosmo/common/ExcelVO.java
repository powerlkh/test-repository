package com.kosmo.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelVO {
	private String filePath;			//엑셀 파일 경로
    private List<String> outputColumns;	//컬럼명
    private int startRow;				//스타트 행번호


    /**
     * Cell에 포함된 Column Name 리턴
     *
     * @param cell
     * @param cellIndex
     * @return null : cellIndex / not null : Column Name
     */
    public static String getColumnName(Cell cell, int cellIndex) {
        int cellNum = 0;
        if(cell != null) {
            cellNum = cell.getColumnIndex();
        } else {
            cellNum = cellIndex;
        }
        return CellReference.convertNumToColString(cellNum);
    }

    /**
     * 셀 값 가져오기
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        String value = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        DataFormatter format = new DataFormatter();

        if(cell == null) {
            value = "";
        } else {

        	if (HSSFDateUtil.isInternalDateFormat(cell.getCellStyle().getDataFormat())) {
				value = dateFormat.format(cell.getDateCellValue());
			} else {
				value = format.formatCellValue(cell);
			}

//	            if( cell.getCellType() == Cell.CELL_TYPE_FORMULA ) {
//	                value = cell.getCellFormula();
//		        } else if( cell.getCellType() == Cell.CELL_TYPE_NUMERIC ) {
//	            	value = (int)cell.getNumericCellValue() + "";
//	            } else if( cell.getCellType() == Cell.CELL_TYPE_STRING ) {
//	                value = cell.getStringCellValue();
//	            } else if( cell.getCellType() == Cell.CELL_TYPE_BOOLEAN ) {
//	                value = cell.getBooleanCellValue() + "";
//	            } else if( cell.getCellType() == Cell.CELL_TYPE_ERROR ) {
//	                value = cell.getErrorCellValue() + "";
//	            } else if( cell.getCellType() == Cell.CELL_TYPE_BLANK ) {
//	                value = "";
//	            } else {
//	                value = cell.getStringCellValue();
//	            }
        }

        return value;
    }



    /**
	 * 확장자(XLS/XLSX)에 따른 Workbook 객체 가져오기
	 *
	 * @param filePath
	 * @return
	 *
	 */
	public static Workbook getWorkbook(String filePath) {

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		Workbook wb = null;

		if(filePath.toUpperCase().endsWith(".XLS")) {
			try {
				wb = new HSSFWorkbook(fis);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		} else if(filePath.toUpperCase().endsWith(".XLSX")) {
			try {
				wb = new XSSFWorkbook(fis);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}

		return wb;
	}


    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getOutputColumns() {

        List<String> temp = new ArrayList<String>();
        temp.addAll(outputColumns);

        return temp;
    }
    public void setOutputColumns(List<String> outputColumns) {

        List<String> temp = new ArrayList<String>();
        temp.addAll(outputColumns);

        this.outputColumns = temp;
    }

    public void setOutputColumns(String ... outputColumns) {

        if(this.outputColumns == null) {
            this.outputColumns = new ArrayList<String>();
        }

        for(String ouputColumn : outputColumns) {
            this.outputColumns.add(ouputColumn);
        }
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

}
