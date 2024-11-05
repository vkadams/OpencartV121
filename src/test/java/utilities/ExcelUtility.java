package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	public static FileInputStream fi;
	public static FileOutputStream fo;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static CellStyle style;
	String path;
	
	public ExcelUtility(String path)
	{
		this.path= path;
	}
	
	
	public int getRowCount(String sheetName) throws IOException
	{
		// x1file is path of the file
		// x1sheet is name of the sheet
		fi= new FileInputStream(path); // opening the file in read mode
		workbook= new XSSFWorkbook(fi); // getting workbook
		sheet=workbook.getSheet(sheetName); // getting sheet
		int rowcount = sheet.getLastRowNum(); // finding the last row number
		workbook.close();
		fi.close();
		return rowcount; // this will return the row count
	}
	
	
	
	public int getCellCount(String sheetName, int rownum) throws IOException
	{
		// rownum is the row number to find number of cells in that row
		fi = new FileInputStream(path); // opening the file in read mode
		workbook = new XSSFWorkbook(fi); // getting workbook
		sheet=workbook.getSheet(sheetName); // getting sheet
		row = sheet.getRow(rownum); // Pass row number
		int cellcount = row.getLastCellNum(); // finding the cell
		workbook.close();
		fi.close();
		return cellcount; // this will return cell count
	}
	
	
	
	
	// read data in particular cell
	public String getCellData(String sheetName, int rownum, int colnum) throws IOException
	{
		// rownum & colnum to read data from 
		fi = new FileInputStream(path); // open the file in read mode
		workbook = new XSSFWorkbook(fi); // open work book 
		sheet=workbook.getSheet(sheetName); // get the sheet
		row = sheet.getRow(rownum); // get the row
		cell= row.getCell(colnum); // get the cell
		
		DataFormatter formatter = new DataFormatter();
		String data;
		try {
			data= formatter.formatCellValue(cell); // returns formatted value of cell as String
		}
		catch(Exception e)
		{
			data="";
		}
		workbook.close();
		fi.close();
		return data;
	}
	
	
	
	// write data to cell
	public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException
	{
		File x1file=new File(path);
		if(!x1file.exists()) // if file does not exists
		{
			workbook =new XSSFWorkbook();
			fo= new FileOutputStream(path);
			workbook.write(fo);
		}
		
		fi= new FileInputStream(path);
		workbook= new XSSFWorkbook(fi);
		
		if(workbook.getSheetIndex(sheetName) == -1) // if sheet does not exist, create new sheet
			workbook.createSheet(sheetName);
		sheet=workbook.getSheet(sheetName);
		
		if(sheet.getRow(rownum) == null) // if row does not exist, then create row
			sheet.createRow(rownum);
		row=sheet.getRow(rownum);
		
		cell=row.createCell(colnum);
		cell.setCellValue(data);
		fo= new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();	
	}
	
	
	
	public void fillGreenColor(String sheetName, int rownum, int colnum) throws IOException
	{
		fi = new FileInputStream(path); // open the file in read mode
		workbook = new XSSFWorkbook(fi); // open work book 
		sheet=workbook.getSheet(sheetName); // get the sheet
		row=sheet.getRow(rownum);
		cell =row.getCell(colnum);
		
		style =workbook.createCellStyle();
		
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cell.setCellStyle(style);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();	
		
	}

	
	
	
	public void fillRedColor(String sheetName, int rownum, int colnum) throws IOException
	{
		fi = new FileInputStream(path); // open the file in read mode
		workbook = new XSSFWorkbook(fi); // open work book 
		sheet=workbook.getSheet(sheetName); // get the sheet
		row=sheet.getRow(rownum);
		cell =row.getCell(colnum);
		
		style =workbook.createCellStyle();
		
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cell.setCellStyle(style);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();	
		
	}
	
	

	
}
