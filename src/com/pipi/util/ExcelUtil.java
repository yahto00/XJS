package com.pipi.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Created by yahto on 07/05/2017.
 */
public class ExcelUtil {
    private static Logger log = Logger.getLogger(ExcelUtil.class);
    private static int MAX_READ_LINES = 50000;    // 限制每次读取的最大行数

    private Workbook writeBook;
    private Sheet writeSheet;
    private Workbook readBook;
    private Sheet readSheet;
    private int rowIndex;
    final String BLANK = "";
    private CellStyle cellStyle;

    public Workbook getWriteBook() {
        return writeBook;
    }

    public void setWriteBook(Workbook writeBook) {
        this.writeBook = writeBook;
    }

    public Sheet getWriteSheet() {
        return writeSheet;
    }

    public void setWriteSheet(Sheet writeSheet) {
        this.writeSheet = writeSheet;
    }

    public Workbook getReadBook() {
        return readBook;
    }

    public void setReadBook(Workbook readBook) {
        this.readBook = readBook;
    }

    public Sheet getReadSheet() {
        return readSheet;
    }

    public void setReadSheet(Sheet readSheet) {
        this.readSheet = readSheet;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public CellStyle getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }
    //写excel

    /**
     * 创建工作表
     *
     * @param name
     */
    public void createSheet(String name) {
        rowIndex = 0;
        writeBook = new HSSFWorkbook();
        writeSheet = writeBook.createSheet(name);
        cellStyle = writeBook.createCellStyle();
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中显示
        cellStyle.setWrapText(true);//自动换行
    }

    /**
     * 创建工作表
     *
     * @param fileName
     * @param name
     */
    public void createSheet(String fileName, String name) {
        createSheet(new File(fileName), name);
    }

    /**
     * 创建工作表
     *
     * @param file
     * @param name
     */
    public void createSheet(File file, String name) {
        try {
            rowIndex = 0;
            writeBook = WorkbookFactory.create(file);
            writeSheet = writeBook.createSheet(name);
            cellStyle = writeBook.createCellStyle();
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中显示
            cellStyle.setWrapText(true);//自动换行
        } catch (EncryptedDocumentException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setColumnWidth(int columnIndex, int width) {
        writeSheet.setColumnWidth(columnIndex, width);
    }

    /**
     * 增加表头
     *
     * @param row
     * @param rowTitle
     */
    public void addHeader(int row, Object rowTitle) {
        if (rowTitle instanceof String[])
            addHeader(row, (String[]) rowTitle);

    }

    /**
     * 增加表头
     *
     * @param row
     * @param rowTitle
     */
    public void addHeader(int row, String[] rowTitle) {
        if (rowTitle == null)
            return;
        Row row1 = writeSheet.createRow(row);
        CellStyle cellStyle = writeBook.createCellStyle();
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中显示
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < rowTitle.length; i++) {
            String value = rowTitle[i];
            if (value != null) {
                Cell cell = row1.createCell(i);
                cell.setCellValue(value);
                cell.setCellStyle(cellStyle);
            }
        }
        rowIndex++;
    }

    /**
     * 添加数组列表
     *
     * @param list
     */
    public void addList(List<Object[]> list) {
        if (list != null) {
            int nLen = list.size();
            for (int i = 0; i < nLen; i++)
                addRow(i + 1, list.get(i));
        }
    }

    /**
     * 增加一行数据
     *
     * @param row
     * @param rowValue
     */
    public void addRow(int row, Object[] rowValue) {
        addRow(row, rowValue, cellStyle);
    }

    /**
     * 增加一行数据
     *
     * @param row
     * @param rowValue
     */
    public void addRow(int row, Object[] rowValue, CellStyle cellStyle) {
        Row sheetRow = writeSheet.createRow(row);
        for (int i = 0; i < rowValue.length; i++)
            addCell(sheetRow, i, rowValue[i], cellStyle);
        rowIndex++;
    }

    /**
     * 添加单元格
     *
     * @param row
     * @param column
     * @param value
     * @return
     */
    public Cell addCell(Row row, int column, Object value) {
        return addCell(row, column, value, cellStyle);
    }

    /**
     * 添加单元格
     *
     * @param row
     * @param column
     * @param value
     * @return
     */
    public Cell addCell(Row row, int column, Object value, CellStyle cellStyle) {
        Cell cell = row.createCell(column);
        cell.setCellStyle(cellStyle);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Float) {
            cell.setCellValue((Float) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        } else if (value != null) {
            cell.setCellValue(value.toString());
        } else {
            cell.setCellValue("");
        }
        return cell;
    }

    /**
     * 合并单元格
     */
    public void addMergedRegion(int firstRow, int lastRow, int firstCol, int lastCol, Object value) {
        addMergedRegion(firstRow, lastRow, firstCol, lastCol, value, cellStyle);
    }

    /**
     * 合并单元格
     */
    public void addMergedRegion(int firstRow, int lastRow, int firstCol, int lastCol, Object value, CellStyle cellStyle) {
        writeSheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
        Row sheetRow = writeSheet.getRow(firstRow);
        if (sheetRow == null)
            sheetRow = writeSheet.createRow(firstRow);
        addCell(sheetRow, firstCol, value, cellStyle);
    }

    /**
     * 写入并关闭文档
     *
     * @param out
     */
    public void writeClose(OutputStream out) {
        try {
            writeBook.write(out);
            writeBook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //读excel

    /**
     * 打开Excel文档
     *
     * @param stream
     * @param sheetIndex
     * @param startRow
     * @return
     */
    public boolean open(InputStream stream, int sheetIndex, int startRow) {
        try {
            readBook = WorkbookFactory.create(stream);
            readSheet = readBook.getSheetAt(sheetIndex);
            rowIndex = startRow;
            return true;
        } catch (Exception e) {
            log.error("Excel exception : ", e);
            e.printStackTrace();
        }
        return false;
    }

    public boolean open(File file, int sheetIndex, int startRow) {
        boolean flag = false;
        try {
            flag = open(new FileInputStream(file), sheetIndex, startRow);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean open(String filename, int sheetIndex, int startRow) {
        return open(new File(filename), sheetIndex, startRow);
    }

    /**
     * 获取行总数
     *
     * @return
     */
    public int getRows() {
        return readSheet.getLastRowNum();
    }

    /**
     * 获取列总数
     *
     * @return
     */
    public int getColumns() {
        return readSheet.getRow(1).getPhysicalNumberOfCells() + 1;
    }

    /**
     * 读取Excel里的一整个sheet数据
     *
     * @return
     */
    public List<String[]> read() {
        return read(-1);
    }

    /**
     * 读取Excel数据
     *
     * @param columns 代表读的总列数
     * @return
     */
    public List<String[]> read(int columns) {
        List<String[]> list = null;
        if (columns == -1)        // 如果未指定列数，则取得excel表总列数
            columns = getColumns();
        list = new ArrayList<String[]>();
        int nLength = getRows();
        if ((nLength - rowIndex) > MAX_READ_LINES)
            nLength = rowIndex + MAX_READ_LINES;
        int i = rowIndex;
        final String BLANK = "";
        for (; i <= nLength; i++) {
            String[] rowData = new String[columns];
            for (int j = 0; j < columns; j++) {
                Cell cell = readSheet.getRow(i).getCell(j);
                if (cell == null)
                    rowData[j] = null;
                else {
                    rowData[j] = cell.toString().trim();
                    if (BLANK.equals(rowData[j]))
                        rowData[j] = null;
                }
            }
            list.add(rowData);
            rowIndex++;
        }
        return list;
    }

    /**
     * 读取一行数据
     *
     * @param columns
     * @return
     */
    public String[] readLine(int columns) {
        if (columns == -1)
            columns = getColumns();
        String[] rowData = new String[columns];
        for (int j = 0; j < columns; j++) {
            Cell cell = readSheet.getRow(rowIndex).getCell(j);
            rowData[j] = cell.getStringCellValue().trim();
            if (BLANK.equals(rowData[j]))
                rowData[j] = null;
        }
        rowIndex++;
        return rowData;
    }

    /**
     * 判断是否读取完毕
     *
     * @return
     */
    public boolean isEnd() {
        return rowIndex >= readSheet.getLastRowNum();
    }

    /**
     * 关闭表格
     */
    public void close() {
        try {
            if (readBook != null)
                readBook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文本文件，保存成List，每行是一个字符串数组
     *
     * @param filename
     * @return
     */
    public static List<String[]> readText(String filename) {
        List<String[]> list = null;
        FileReader reader;
        try {
            reader = new FileReader(filename);
            BufferedReader buf = new BufferedReader(reader);
            String line = buf.readLine();    // 去掉第一行
            list = new ArrayList<String[]>();
            while ((line = buf.readLine()) != null) {
                String[] token = line.split(",");    // 逗号分隔符
                for (int i = 0; i < token.length; i++) {
                    String str = token[i];    // 把空字符串替换为null
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
