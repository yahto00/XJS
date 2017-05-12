package com.pipi.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pipi.common.constant.SystemConstant;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Created by yahto on 07/05/2017.
 */
public class ExcelUtil2 {
	
	private static int MAX_READ_LINES = 5000;	// 限制每次读取的最大行数

	private WritableWorkbook writeBook;
	private WritableSheet writeSheet;
	private Workbook readBook;
	private Sheet readSheet;
	private int rowIndex;

	// 创建工作表
	public void createSheet(OutputStream output, String name){
		try {
			writeBook = Workbook.createWorkbook(output);
			writeSheet = writeBook.createSheet(name, 0);
		} catch (IOException e) {}
	}

	// 增加表头
	public void addHeader(int row, String[] rowTitle){
		if (rowTitle == null)
			return;
		WritableFont boldFont = new WritableFont(WritableFont.ARIAL,
				WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD); // 设置写入字体
		WritableCellFormat cellFormat = new WritableCellFormat(boldFont); // 设置CellFormat
		try {
			cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
			//title.setBackground(jxl.format.Colour.GRAY_25);
			for (int i=0; i<rowTitle.length; i++){
				String value = rowTitle[i];
				if (value != null){
					jxl.write.Label cell = new jxl.write.Label(i, row, value, cellFormat);
					writeSheet.addCell(cell);
				}
			}
		} catch (WriteException e) {}
	}

	// 增加一行数据
	public void addRow(int row, Object[] rowValue){
		for (int i=0; i<rowValue.length; i++)
			addCell(row, i, rowValue[i]);
	}
	// 添加数组列表
	public void addList(List<Object[]> list){
		if (list != null){
			int nLen = list.size();
			for (int i=0; i<nLen; i++)
				addRow(i+1, list.get(i));
		}
	}
	/* 添加bean列表
	public void addBeanList(List<Object> list, String[] properties){
		if (list != null){
			int nLen = list.size();
			for (int i=0; i<nLen; i++)
				addRow(i+1, BeanLite.toArray(list.get(i), properties));
		}		
	}*/
	
	// 添加单元格
	public void addCell(int row, int column, Object value){
		WritableCell cell = null;
		if (value instanceof Integer){
			cell = new jxl.write.Number(column, row, (Integer)value);
		} else if (value instanceof Float){
			cell = new jxl.write.Number(column, row, (Float)value);
		} else if (value instanceof Double){
			cell = new jxl.write.Number(column, row, (Double)value);
		} else if (value instanceof Date){
			cell = new jxl.write.Label(column, row, DateUtil.dateToString((Date)value, SystemConstant.DATE_PATTEN));
		} else if (value != null){
			cell = new jxl.write.Label(column, row, value.toString());
		}
		try {
			if (cell != null)
				writeSheet.addCell(cell);
		} catch (RowsExceededException e) {
		} catch (WriteException e) {}
	}

	// 写入并关闭文档
	public void writeClose(){
	    try {
			writeBook.write();
			writeBook.close();
		} catch (Exception e) {}
	}
	
	// 打开Excel文档
	public boolean open(File file, int sheetIndex, int startRow){
		try {
			readBook = Workbook.getWorkbook(file);
			readSheet = readBook.getSheet(sheetIndex);
			rowIndex = startRow;
			return true;
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean open(String filename, int sheetIndex, int startRow){
		return open(new File(filename), sheetIndex, startRow);
	}
	public boolean open(InputStream stream, int sheetIndex, int startRow){
		try {
			readBook = Workbook.getWorkbook(stream);
			readSheet = readBook.getSheet(sheetIndex);
			rowIndex = startRow;
			return true;
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;		
	}
	public int getRows(){
		return readSheet.getRows();
	}
	public int getColumns(){
		return readSheet.getColumns();
	}
	
	// 读取Excel里的一整个sheet数据
	public List<String[]> read(){
		return read(-1);
	}
	
	// 读取Excel数据
	public List<String[]> read(int columns){
		List<String[]> list = null;
		if (columns == -1)		// 如果未指定列数，则取得excel表总列数
			columns = readSheet.getColumns();
		list = new ArrayList<String[]>();
		int nLength = readSheet.getRows();
		if ((nLength - rowIndex) > MAX_READ_LINES)
			nLength = rowIndex + MAX_READ_LINES;
		int i = rowIndex;
		final String BLANK = "";
		for ( ; i<nLength; i++) {
			String[] rowData = new String[columns];
			for (int j=0; j<columns; j++) {
				Cell cell = readSheet.getCell(j, i);
				rowData[j] = cell.getContents().trim();
				if (BLANK.equals(rowData[j]))
					rowData[j] = null;
				//System.out.println("i=" + i + ",j=" + j + ",value=" + rowData[j]);
			}
			list.add(rowData);
			rowIndex++;
		}
		return list;		
	}
	// 读取一行数据
	public String[] readLine(int columns){
		if (columns == -1)
			columns = readSheet.getColumns();
		String[] rowData = new String[columns];
		for (int j=0; j<columns; j++) {
			Cell cell = readSheet.getCell(j, rowIndex);
			rowData[j] = cell.getContents();
		}
		rowIndex++;
		return rowData;
	}
	
	// 判断是否读取完毕
	public boolean isEnd(){
		return rowIndex >= readSheet.getRows();
	}
	
	// 关闭表格
	public void close(){
		if (readBook != null)
			readBook.close();
	}

	// 读取文本文件，保存成List，每行是一个字符串数组
	public static List<String[]> readText(String filename) {
		List<String[]> list = null;
		FileReader reader;
		try {
			reader = new FileReader(filename);
			BufferedReader buf = new BufferedReader(reader);
			String line = buf.readLine();	// 去掉第一行
			list = new ArrayList<String[]>();
			while((line = buf.readLine()) != null){
				String[] token = line.split(",");	// 逗号分隔符
				for (int i=0; i<token.length; i++){
					String str = token[i];	// 把空字符串替换为null
					if (str != null && (str.equals("") || str.equals(" ")))
						token[i] = null;
				}
				list.add(token);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
