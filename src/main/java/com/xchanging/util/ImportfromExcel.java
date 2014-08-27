package com.xchanging.util;

import java.io.File;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ImportfromExcel {
	
	
	public String fileName = System.getProperty("user.dir");   
    public  File file = new File(fileName + "//TestData//TestData.xls" );   
	 
	public String readFromExcel(String sheetName, int column, int row) throws BiffException, IOException {
		
		
		Workbook workbook = Workbook.getWorkbook(file);
		Sheet sheet = workbook.getSheet(sheetName);
		return sheet.getCell(column, row).getContents();		
	}
	
	public static void getData() {
		
		
	}
}
