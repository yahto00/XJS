package com.pipi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Created by yahto on 07/05/2017.
 */
public class ExcelReadWriteUtil {
	
	private static int MAX_READ_LINES = 5000;	// 限制每次读取的最大行数

	private Workbook writeBook;
	private Sheet writeSheet;
	private int rowIndex;
	private OutputStream out;
	final String BLANK = "";

	/**
	 * 添加数组列表
	 * @param list
	 */
	public void addList(List<Object[]> list){
		if (list != null){
			int nLen = list.size();
			for (int i=0; i<nLen; i++)
				addRow(i+1, list.get(i));
		}
	}
	/**
	 * 增加一行数据
	 * @param row
	 * @param rowValue
	 */
	public void addRow(int row, Object[] rowValue){
		Row sheetRow = writeSheet.createRow(row);
		for (int i=0; i<rowValue.length; i++)
			addCell(sheetRow, i, rowValue[i]);
	}
	/**
	 * 添加单元格
	 * @param row
	 * @param column
	 * @param value
	 * @return
	 */
	public Cell addCell(Row row, int column, Object value){
		Cell cell = row.createCell(column);
		if (value instanceof Integer){
			cell.setCellValue((Integer)value);
		} else if (value instanceof Float){
			cell.setCellValue((Float)value);
		} else if (value instanceof Double){
			cell.setCellValue((Double)value);
		} else if (value instanceof Date){
			cell.setCellValue((Date)value);
		} else if (value != null){
			cell.setCellValue(value.toString());
		}
		return cell;
	}

	/**
	 * 写入并关闭文档
	 */
	public void writeClose(){
	    try {
			writeBook.write(out);
			writeBook.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//读excel
	/**
	 * 打开Excel文档
	 * @param stream
	 * @param sheetIndex
	 * @param startRow
	 * @return
	 */
	private boolean open(InputStream stream, int sheetIndex, int startRow){
		try {
			writeBook = WorkbookFactory.create(stream);
			writeSheet = writeBook.getSheetAt(sheetIndex);
			rowIndex = startRow;
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return false;		
	}
	public boolean open(File file, int sheetIndex, int startRow){
		boolean flag = false;
		try {
			out = new FileOutputStream(file);
			flag = open(new FileInputStream(file), sheetIndex, startRow);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	public boolean open(String filename, int sheetIndex, int startRow){
		return open(new File(filename), sheetIndex, startRow);
	}
	public void setSheet(int sheetIndex, int startRow){
		writeSheet = writeBook.getSheetAt(sheetIndex);
		rowIndex = startRow;
	}
	public void setSheet(String sheetName, int startRow){
		writeSheet = writeBook.getSheet(sheetName);
		rowIndex = startRow;
	}
	
	/**
	 * 获取行总数
	 * @return
	 */
	public int getRows(){
		return writeSheet.getLastRowNum();
	}
	/**
	 * 获取列总数
	 * @return
	 */
	public int getColumns(){
		return writeSheet.getRow(1).getPhysicalNumberOfCells();
	}
	
	/**
	 * 读取Excel里的一整个sheet数据
	 * @return
	 */
	public Map<String,String[]> read(int keyColumn){
		return read(-1,keyColumn);
	}
	
	/**
	 * 读取Excel数据
	 * @return
	 */
	public Map<String,String[]> read(int columns, int keyColumn){
		Map<String,String[]> map = new HashMap<String,String[]>();
		if (columns == -1)		// 如果未指定列数，则取得excel表总列数
			columns = getColumns();
		if(columns < keyColumn)
			return map;
		int nLength = getRows();
		if ((nLength - rowIndex) > MAX_READ_LINES)
			nLength = rowIndex + MAX_READ_LINES;
		int i = rowIndex;
		final String BLANK = "";
		for ( ; i<=nLength; i++) {
			String[] rowData = new String[columns];
			for (int j=0; j<columns; j++) {
				Cell cell = writeSheet.getRow(i).getCell(j);
				rowData[j] = cell.toString().trim();
				if (BLANK.equals(rowData[j]))
					rowData[j] = null;
			}
			map.put(rowData[keyColumn], rowData);
			rowIndex++;
		}
		return map;
	}
	/**
	 * 读取一行数据
	 * @param columns
	 * @return
	 */
	public String[] readLine(int columns){
		if (columns == -1)
			columns = getColumns();
		String[] rowData = new String[columns];
		for (int j=0; j<columns; j++) {
			Cell cell = writeSheet.getRow(rowIndex).getCell(j);
			rowData[j] = cell.getStringCellValue().trim();
			if (BLANK.equals(rowData[j]))
				rowData[j] = null;
		}
		rowIndex++;
		return rowData;
	}
	
	/**
	 * 判断是否读取完毕
	 * @return
	 */
	public boolean isEnd(){
		return rowIndex >= writeSheet.getLastRowNum();
	}
}
