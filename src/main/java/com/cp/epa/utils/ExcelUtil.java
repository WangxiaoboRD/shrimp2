package com.cp.epa.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cp.epa.base.BaseEntity;
import com.cp.epa.bussobj.entity.BussinessEleDetail;
import com.cp.epa.exception.SystemException;



/**
 * Excel操作工具
 * 类名：ExcelUtil  
 *
 * 功能：
 * 创建时间：Jul 4, 2013 4:16:01 PM 
 * @version Jul 4, 2013
 */
public class ExcelUtil {
	
	/** yyyy-MM-dd日期格式 */
	public static final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

	private static final Logger log = Logger.getLogger(ExcelUtil.class);	
	private String filePath;
	private HSSFWorkbook wb;
	private XSSFWorkbook xb;
	private String version;
	
	/**
	 * 功能：构建Excel工作薄对象<br/>
	 *
	 * @author 孟雪勤
	 * @version 2015-3-29 上午09:50:49 <br/>
	 * @throws Exception 
	 */
	public static Workbook buildWorkbook(File file, String fileName) throws Exception {
		int excelVersion = fileName.endsWith("xls") ? 2003 : fileName.endsWith("xlsx") ? 2007 : 0;
		FileInputStream fileStream = new FileInputStream(file);
		return excelVersion == 2003 ? new HSSFWorkbook(fileStream) : excelVersion == 2007 ? new XSSFWorkbook(fileStream) : null;
	}
	
	/**
	 * 功能：合并单元格<br/>
	 *
	 * @author 孟雪勤
	 * @version 2015-12-2 下午03:01:21 <br/>
	 */
	public static void mergeCell(HSSFSheet sheet, HSSFCellStyle style, int startRow, int startCell, int endRow, int endCell, int currentCell, Object value) {
		sheet.addMergedRegion(new CellRangeAddress(startRow, endRow, startCell, endCell));
		for (int row = startRow; row <= endRow; row ++) {
			HSSFRow r = sheet.getRow(row);
			if (null == r) {
				r = sheet.createRow(row);
			}
			for (int cell = startCell; cell <= endCell; cell ++) {
		         HSSFCell c = r.createCell(cell);
		         setCellValue(c, value);
		         c.setCellStyle(style);
			}
		}
        HSSFRow currentRow = sheet.getRow(startRow);
        HSSFCell cell = currentRow.getCell(currentCell);
        setCellValue(cell, value);
	}
	
	/**
	 * 功能：设置单元格的值<br/>
	 *
	 * @author 孟雪勤
	 * @version 2015-12-2 下午04:45:01 <br/>
	 */
	public static void setCellValue(HSSFSheet sheet, HSSFCellStyle style, int rowIndex, int cellIndex, Object value) {
		HSSFRow row = sheet.getRow(rowIndex);
		if (row == null)
			row = sheet.createRow(rowIndex);
        HSSFCell cell = row.createCell(cellIndex);
        setCellValue(cell, value);
        cell.setCellStyle(style);
	}
	
	/**
	 * 通过字节流构建warkbook
	 * 构造方法
	 */
	public ExcelUtil(InputStream is,String version) {
		POIFSFileSystem fs;
		try {
			if("2003".equals(version)){
				fs = new POIFSFileSystem(is);
				wb = new HSSFWorkbook(fs);
			}else if("2007".equals(version)){
				if(is instanceof FileInputStream)
					xb = new XSSFWorkbook((FileInputStream)is);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 直接构造
	 * 构造方法
	 */
    public ExcelUtil() {
    	wb = new HSSFWorkbook();
	}
    
    /**
     * 通过文件路径构建
     * 构造方法
     */
	public ExcelUtil(String filePath) {
		this.filePath = filePath;
		POIFSFileSystem fs;
		try {
			if(filePath.endsWith("xls")){
				fs = new POIFSFileSystem(new FileInputStream(filePath));
				wb = new HSSFWorkbook(fs);
			}else if(filePath.endsWith("xlsx")){
				xb = new XSSFWorkbook(new FileInputStream(filePath));
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage() + filePath, e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage() + filePath, e);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage() + filePath, e);
		}
	}

	/**
	 * 构建内部类
	 * 类名：Point  
	 *
	 * 功能：
	 *
	 * @author dzl 
	 * 创建时间：Jul 5, 2013 4:51:29 PM 
	 * @version Jul 5, 2013
	 */
	class Point {
		
		/**
		 * 将列名解析为坐标位置 比如 A12 坐标位置为 第0列 第11行
		 * 构造方法
		 */
		public Point(String cellPositionStr) {
			char[] chars = cellPositionStr.toCharArray();
			int i = 0;
			for (; i < chars.length; i++) {
				if (Character.isDigit(chars[i])) {
					break;
				}
			}
			row = Integer.parseInt(cellPositionStr.substring(i)) - 1;
			col = cellNumStr2Int(cellPositionStr.substring(0, i));
		}

		/**
		 * 根据列的英文 序列如 BC列 行数 3行 进行转换为坐标数为 第54列 第2行
		 * 构造方法
		 */
		public Point(String colStr, int row) {
			col = cellNumStr2Int(colStr);
			this.row = row;
		}

		int row;
		int col;
	}

	/** 
	 * 获取sheet数目。 
	 * @return 
	 */
	public int getSheetCnt() {
		return this.wb.getNumberOfSheets();
	}

	/** 
	 * 给Excel中的某个sheet的某个单元格赋值。 
	 *  
	 * @param cellPositionStr 位置参数如A12表示A列，12行。 
	 * @param sheetNo 
	 * @param v 
	 * @return 
	 */
	public HSSFCell setCellValue(String cellPositionStr, int sheetNo, Object v) {
		Point p = new Point(cellPositionStr);
		return setCellValue(p, sheetNo, v);
	}

	public HSSFCell setCellValue(String cellPositionStr, Object v) {
		Point p = new Point(cellPositionStr);
		return setCellValue(p, 0, v);
	}

	/** 
	 * 给Excel中的某个sheet的某个单元格赋值。 
	 *  
	 * @param colNumStr 哪一列 指的是英文指示的列名 比如 B列或者BC列
	 * @param rowNum  //行数 比如第10行
	 * @param sheetNo 
	 * @param v 
	 * @return 
	 */
	public HSSFCell setCellValue(String colNumStr, int rowNum, int sheetNo,Object v) {
		Point p = new Point(colNumStr, rowNum);
		return setCellValue(p, sheetNo, v);
	}

	
	public HSSFCell setCellValue(Point p, int sheetNo, Object v) {
		return setCellValue(p.col, p.row, sheetNo, v);
	}

	/** 
	 * 给Excel中的某个sheet的某个单元格赋值。 
	 * 主方法
	 * @param colNum 
	 * @param rowNum 从0开始。 
	 * @param sheetNo 从0开始。 
	 * @param v 
	 * @return 
	 */
	public HSSFCell setCellValue(int colNum, int rowNum, int sheetNo, Object v) {
		HSSFCell cell = this.getCell(colNum, rowNum, sheetNo);
		if (v == null) {
			cell.setCellValue(new HSSFRichTextString(""));//TODO 添加的值是以单元格格式为准，还是以数据类型为准？  
			return cell;
		}else{
			if (v.getClass() == Boolean.class) {
				cell.setCellValue((Boolean) v);
			} else if (v.getClass() == Integer.class) {
				cell.setCellValue((Integer) v);
			} else if (v.getClass() == Double.class) {
				cell.setCellValue((Double) v);
			} else if (v.getClass() == Float.class) {
				cell.setCellValue((Float) v);
			} else if (v.getClass() == BigDecimal.class) {
				cell.setCellValue(((BigDecimal) v).doubleValue());
			} else if (v instanceof Date) {
				cell.setCellValue(new HSSFRichTextString(PapUtil.date((Date) v)));
			} else if (v.getClass() == String.class) {
				String cellStr = (String) v;
				if (cellStr.length() >= 32766) {
					cellStr = cellStr.substring(0, 32765);
				}
				cell.setCellValue(new HSSFRichTextString(cellStr));
			} else {
				cell.setCellValue(new HSSFRichTextString(v.toString()));
			}
		}
		return cell;
	}
	
	/**
	 * 给cell设置值
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jul 10, 2013 5:26:07 PM <br/>
	 */
	public static void setCellValue(HSSFCell cell, Object v){
		if (v == null) {
			cell.setCellValue(new HSSFRichTextString(""));//TODO 添加的值是以单元格格式为准，还是以数据类型为准？  
		}else {
			if (v.getClass() == Boolean.class) {
				cell.setCellValue((Boolean) v);
			} else if (v.getClass() == Integer.class) {
				cell.setCellValue((Integer) v);
			} else if (v.getClass() == Double.class) {
				cell.setCellValue((Double) v);
			} else if (v.getClass() == Float.class) {
				cell.setCellValue((Float) v);
			} else if (v.getClass() == BigDecimal.class) {
				cell.setCellValue(((BigDecimal) v).doubleValue());
			} else if (v instanceof Date) {
				cell.setCellValue(new HSSFRichTextString(PapUtil.date((Date) v)));
			} else if (v.getClass() == String.class) {
				String cellStr = (String) v;
				if (cellStr.length() >= 32766) {
					cellStr = cellStr.substring(0, 32765);
				}
				cell.setCellValue(new HSSFRichTextString(cellStr));
			} else {
				cell.setCellValue(new HSSFRichTextString(v.toString()));
			}
		}
	}

	/** 
	 * 根据指定行列和sheet获取单元。 
	 *  
	 * @param rowNum  行数比如20行
	 * @param cellNum 列数比如第10列
	 * @param sheetNo 所属的sheet
	 * @return 
	 */
	public HSSFCell getCell(int colNum, int rowNum, int sheetNo) {
		HSSFRow row = getRow(rowNum, sheetNo);
		HSSFCell cell = row.getCell(colNum);
		if (cell == null)
			cell = row.createCell(colNum);
		return cell;
	}
	
	/**
	 * 功能：<br/>
	 * 根据列的名称colNumStr 与 行数rowNum 获取cell对象
	 * 
	 * 比如 B 列的20行
	 * @author 
	 * @version Jul 5, 2013 5:37:50 PM <br/>
	 */
	public HSSFCell getCell(String colNumStr, int rowNum, int sheetNo) {
		int colNum = cellNumStr2Int(colNumStr);
		return getCell(colNum, rowNum, sheetNo);
	}

	/**
	 * 通过cell的名称与sheet号码获取cell对象 
	 * 比如cellPositionStr 的值为 B12
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jul 5, 2013 5:39:31 PM <br/>
	 */
	public HSSFCell getCell(String cellPositionStr, int sheetNo) {
		Point p = new Point(cellPositionStr);
		return getCell(p.col, p.row, sheetNo);
	}

	/**
	 * 根据sheet编号获得sheet对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jul 5, 2013 5:40:46 PM <br/>
	 */
	public HSSFSheet getSheetAt(int num) {
		return wb.getSheetAt(num);
	}

	/** 
	 * 合并
	 * @param sheetNum 所属的sheet
	 * @param firstRow 从哪一行开始
	 * @param lastRow  到哪一行结束
	 * @param firstCol 从那一列开始
	 * @param lastCol  到那一列结束
	 */
	public void addMergedRegion(int sheetNum, int firstRow, int lastRow,int firstCol, int lastCol) {
		HSSFSheet sheet = getSheetAt(sheetNum);
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol,lastCol));//指定合并区域  
	}

	/** 
	 * 获取某一行。 
	 *  
	 * 根据sheet数与 行数字 获得这个行的对象 
	 * @param rowNum 
	 * @param sheetNo 
	 * @return 
	 */
	public HSSFRow getRow(int rowNum, int sheetNo) {
		HSSFSheet sheet = null;
		if (sheetNo >= wb.getNumberOfSheets()) {
			sheet = wb.createSheet("sheet" + sheetNo);
		} else {
			sheet = wb.getSheetAt(sheetNo);
		}
		HSSFRow row = sheet.getRow(rowNum);
		if (row == null)
			row = sheet.createRow(rowNum);
		return row;
	}

	/** 
	 * 将列的名称转换为数字。 
	 *  
	 * @param cellNumStr 
	 * @return 
	 */
	private static int cellNumStr2Int(String cellNumStr) {
		cellNumStr = cellNumStr.toLowerCase();
		int cellNum = 0;
		char[] chars = cellNumStr.toCharArray();
		int j = 0;
		for (int i = chars.length - 1; i >= 0; i--) {
			cellNum += (chars[i] - 'a' + 1) * Math.pow(26, j);
			j++;
		}
		return cellNum - 1;
	}

	/**
	 * 将列的坐标数字 转换为列名
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jul 5, 2013 5:44:21 PM <br/>
	 */
	public static String cellNumIntToStr(int colNum) {
		String colName = "";
		//            for(int i=0;i<colNum/26+1;i++){  
		//                    char c = (char)(colNum%26+'a'-i);  
		//                    colName = c + colName;  
		//                    colNum = colNum/26;  
		//            }  
		//colNum++;  
		do {
			char c = (char) (colNum % 26 + 'A');
			colName = c + colName;
			colNum = colNum / 26 - 1;
		} while (colNum >= 0);
		return colName;
	}

	/** 
	 * 将excel写入到某个输出流中。 
	 *  
	 * @param out 
	 */
	public void write(OutputStream out) {
		try {
			wb.write(out);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 将信息保存在文件上
	 * 功能：<br/>
	 *
	 * @author 
	 * @version Jul 5, 2013 4:29:55 PM <br/>
	 */
	public void save(String filePath) {
		try {
			OutputStream out = new FileOutputStream(new File(filePath));
			write(out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/** 
	 * 获取某个单元格的值，并做一定的类型判断。 
	 *  
	 * @param cell 
	 * @return 
	 */
	public Object getCellValue(HSSFCell cell) {
		Object value = null;
		if (cell != null) {
			int cellType = cell.getCellType();
			HSSFCellStyle style = cell.getCellStyle();
			short format = style.getDataFormat();
			switch (cellType) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				double numTxt = cell.getNumericCellValue();
				if (format == 22 || format == 14)
					value = HSSFDateUtil.getJavaDate(numTxt);
				else
					value = numTxt;
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				boolean booleanTxt = cell.getBooleanCellValue();
				value = booleanTxt;
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				value = null;
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				HSSFFormulaEvaluator eval = new HSSFFormulaEvaluator(
						(HSSFWorkbook) wb);
				eval.evaluateInCell(cell);
				value = getCellValue(cell);
				break;
			case HSSFCell.CELL_TYPE_STRING:
				HSSFRichTextString rtxt = cell.getRichStringCellValue();
				if (rtxt == null) {
					break;
				}
				String txt = rtxt.getString();
				value = txt;
				break;
			default:
				//System.out.println(cell.getColumnIndex()+" col cellType="+cellType);  
			}
		}
		return value;

	}

	public static interface CellCallback {
		public void handler(HSSFCell cell);
	}

	/** 
	 * 遍历所有的单元格。 
	 * @param callback 
	 * @param sheetNo 
	 */
	public void iterator(CellCallback callback, int sheetNo) {
		HSSFSheet sheet = wb.getSheetAt(sheetNo);
		if (sheet == null)
			return;
		int firstRowNum = sheet.getFirstRowNum();
		int lastRowNum = sheet.getLastRowNum();
		for (int i = firstRowNum; i <= lastRowNum; i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null)
				continue;
			for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
				HSSFCell cell = row.getCell(j);
				callback.handler(cell);
			}
		}
	}

	/** 
	 * 读取某个excel，然后将其转化为List的List。 
	 *  
	 * @param source 
	 * @return 
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public List<List> excelToList(int sheetNo) {
		//首先是讲excel的数据读入，然后根据导入到的数据库的结构和excel的结构来决定如何处理。  
		HSSFSheet sheet = wb.getSheetAt(sheetNo);
		int firstRowNum = sheet.getFirstRowNum();
		int lastRowNum = sheet.getLastRowNum();
		List rows = new ArrayList();
		for (int i = firstRowNum; i <= lastRowNum; i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null) {
				log.debug("Excel.excelToListList()" + i + " filePath="+ filePath);
				continue;
			}
			List cellList = new ArrayList();
			for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
				Object value = null;
				HSSFCell cell = row.getCell(j);
				if (cell != null)
					value = getCellValue(cell);
				cellList.add(value);
			}
			rows.add(cellList);
		}
		return rows;
	}

	/**把excel转换成List<Map>格式 
	 * @param sheetNo 需要转换的数据所在的sheet次序号(0,1,2...n) 
	 * @return 
	 */
	public List<Map> excelToMapList(int sheetNo) {
		HSSFSheet sheet = this.wb.getSheetAt(sheetNo);
		int firstRowNum = sheet.getFirstRowNum();
		return excelToMapList(sheetNo, firstRowNum, firstRowNum + 1);
	}

	/**把excel转换成List<Map>格式 
	 * @param sheetNo需要转换的数据所在的sheet次序号(0,1,2...n) 
	 * @param keyRowNo 作为key的行号 （0,1,2...n） 
	 * @param dataStartRowNo第一行数据的行号 （1,2...n） 
	 * @return 
	 */
	public List<Map> excelToMapList(int sheetNo, int keyRowNo,int dataStartRowNo) {
		return excelToMapList(sheetNo, keyRowNo, keyRowNo, dataStartRowNo);
	}

	/** 
	 * 标题从多行进行合并得到。 
	 * @param sheetNo 
	 * @param keyRowNoFrom 
	 * @param keyRowNoTo 
	 * @param dataStartRowNo 
	 * @return 
	 */
	public List<Map> excelToMapList(int sheetNo, int keyRowNoFrom,int keyRowNoTo, int dataStartRowNo) {
		HSSFSheet sheet = this.wb.getSheetAt(sheetNo);
		List rowMapList = new ArrayList();
		String[] keyList = new String[200];
		for (int i = keyRowNoFrom; i <= keyRowNoTo; i++) {
			HSSFRow mapKeyRow = sheet.getRow(i);
			String lstKey = null;
			for (int j = mapKeyRow.getFirstCellNum(); j < mapKeyRow.getLastCellNum(); j++) {
				HSSFCell col = mapKeyRow.getCell(j);
				String key = col.getRichStringCellValue().getString();
				String keyx = keyList[j];
				if (key == null) {
					key = keyx;
				} else if (keyx != null)
					key = keyx + key;

				if (key == null || "".equals(key)) {
					key = lstKey;
				}
				lstKey = key;

				keyList[j] = key;
			}
		}
		int lastRowNum = sheet.getLastRowNum();
		for (int i = dataStartRowNo; i <= lastRowNum; ++i) {
			HSSFRow dataRow = sheet.getRow(i);
			if (dataRow == null)
				continue;
			Map rowMap = new HashMap();
			for (int j = dataRow.getFirstCellNum(); j < dataRow
					.getLastCellNum(); ++j) {
				String key = keyList[j];
				if (key == null || key.equals("")) {
					continue;
				}
				Object value = getCellValue(dataRow.getCell(j));
				rowMap.put(key, value);
			}
			rowMapList.add(rowMap);
		}
		return rowMapList;
	}

	static interface RowCallBack {
		void handler(Map m);
	}

	public void iterateRows(HSSFSheet sheet, RowCallBack callBack,int keyRowNoFrom, int keyRowNoTo, int dataStartRowNo) {
		List rowMapList = new ArrayList();
		String[] keyList = new String[200];
		for (int i = keyRowNoFrom; i <= keyRowNoTo; i++) {
			HSSFRow mapKeyRow = sheet.getRow(i);
			String lstKey = null;
			for (int j = mapKeyRow.getFirstCellNum(); j < mapKeyRow
					.getLastCellNum(); j++) {
				HSSFCell col = mapKeyRow.getCell(j);
				String key = col.getRichStringCellValue().getString();
				String keyx = keyList[j];
				if (key == null) {
					key = keyx;
				} else if (keyx != null)
					key = keyx + key;

				if (key == null || "".equals(key)) {
					key = lstKey;
				}
				lstKey = key;

				keyList[j] = key;
			}
		}
		int lastRowNum = sheet.getLastRowNum();
		for (int i = dataStartRowNo; i <= lastRowNum; ++i) {
			HSSFRow dataRow = sheet.getRow(i);
			if (dataRow == null)
				continue;
			Map rowMap = new HashMap();
			for (int j = dataRow.getFirstCellNum(); j < dataRow
					.getLastCellNum(); ++j) {
				String key = keyList[j];
				if (key == null || key.equals("")) {
					continue;
				}
				Object value = getCellValue(dataRow.getCell(j));
				rowMap.put(key, value);
			}
			callBack.handler(rowMap);
		}
	}

	public void mapListToExcel(ExcelUtil excel, List<Map> rs, Iterator it) {

	}

	/** 
	 * 复制srcRowNum，然后在targetRowNum处添加一行。 
	 * @param srcRowNum 
	 * @return 
	 */
	public HSSFRow createRow(int srcRowNum) {
		HSSFSheet sheet = wb.getSheetAt(0);
		int targetRowNum = sheet.getLastRowNum() + 1;
		return createRow(sheet, sheet, srcRowNum, targetRowNum);
	}

	/** 
	 * 复制srcRowNum，然后在targetRowNum处添加一行。 
	 * @param srcRowNum 
	 * @param targetRowNum 
	 * @return 
	 */
	public HSSFRow createRow(int srcRowNum, int targetRowNum) {
		HSSFSheet sheet = wb.getSheetAt(0);
		return createRow(sheet, sheet, srcRowNum, targetRowNum);
	}

	/** 
	 * 复制srcRowNum，然后在targetRowNum处添加一行。 
	 * @param sheet 
	 * @param srcRowNum 
	 * @param targetRowNum 
	 * @return 
	 */
	public HSSFRow createRow(HSSFSheet srcSheet, HSSFSheet targetSheet,int srcRowNum, int targetRowNum) {
		HSSFRow srcRow = srcSheet.getRow(srcRowNum);
		HSSFRow newRow = targetSheet.createRow(targetRowNum);
		newRow.setHeight(srcRow.getHeight());
		int i = 0;
		for (Iterator<Cell> cit = srcRow.cellIterator(); cit.hasNext();) {
			Cell hssfCell = cit.next();
			//HSSFCell中的一些属性转移到Cell中  
			HSSFCell cell = newRow.createCell(i++);
			CellStyle s = hssfCell.getCellStyle();
			cell.setCellStyle(hssfCell.getCellStyle());
		}
		return newRow;
	}

	public void deleteRow(int rowNum) {
		deleteRow(0, rowNum);
	}

	public void deleteRow(int sheetNo, int rowNum) {
		HSSFSheet sheet = wb.getSheetAt(sheetNo);
		sheet.shiftRows(rowNum, sheet.getLastRowNum(), -1);
	}

	/** 
	 * 拷贝行粘帖到指定位置。 
	 * @param sheet 
	 * @param srcRow 
	 * @param rowNum 
	 * @return 
	 */
	public HSSFRow copyAndInsertRow(HSSFSheet sheet, HSSFRow srcRow,int targetRowNum) {
		sheet.shiftRows(targetRowNum, sheet.getLastRowNum(), 1);
		HSSFRow newRow = sheet.getRow(targetRowNum);
		newRow.setHeight(srcRow.getHeight());
		int j = 0;
		for (Iterator<Cell> cit = srcRow.cellIterator(); cit.hasNext();) {
			Cell hssfCell = cit.next();
			//HSSFCell中的一些属性转移到Cell中  
			HSSFCell cell = newRow.createCell(j++);
			cell.setCellStyle(hssfCell.getCellStyle());
		}
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			CellRangeAddress s = null;
			s.getFirstColumn();
			CellRangeAddress region = sheet.getMergedRegion(i);
			if (region.getFirstRow() == srcRow.getRowNum()
					&& region.getLastRow() == region.getFirstRow()) {
				sheet.addMergedRegion(new CellRangeAddress(targetRowNum,region.getFirstColumn(), targetRowNum, region.getLastColumn()));
			}
		}
		return newRow;
	}

	public HSSFRow copyAndInsertRow(int sheetNo, int fromRowNum,int targetRowNum) {
		HSSFSheet sheet = wb.getSheetAt(sheetNo);
		HSSFRow srcRow = sheet.getRow(fromRowNum);
		return copyAndInsertRow(sheet, srcRow, targetRowNum);
	}
	
	/**
	 * 标题为空的导出
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jul 11, 2013 10:38:06 AM <br/>
	 */
	public static <T extends BaseEntity> InputStream exportExcel(String[] listHeaders,Collection<T> dataset){
		return exportExcel(null,listHeaders,dataset);
	}
	
	/**
	 * 没有标题，没有头信息的导出
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jul 11, 2013 10:39:15 AM <br/>
	 */
	public static <T extends BaseEntity> InputStream exportExcel(Collection<T> dataset){
		return exportExcel(null,null,dataset);
	}
	/**
	 * 导出总功能
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jul 10, 2013 3:34:28 PM <br/>
	 */
	public static <T extends BaseEntity> InputStream exportExcel(String title, String[] listHeaders,Collection<T> dataset){
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		if(title == null || "".equals(title))
			title ="sheet1";
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		//设置表头样式
		HSSFCellStyle headerStyle = getHeaderStyle(workbook);
		//设置表格样式
		HSSFCellStyle cellStyle = getCellStyle(workbook);
//		Iterator<Entry<String, String>> iterator = mapHeaders.entrySet().iterator();
//        while(iterator.hasNext()) {
//            Map.Entry<String,String> entry = iterator.next();
//            String key = entry.getKey();           
//            String value = entry.getValue();
//        }
		HSSFRow row = null;
		int index = -1;
		List<String> fieldNames = new ArrayList<String>();
		if(listHeaders != null && listHeaders.length>0){
			index++;
			//产生表格标题行
			row = sheet.createRow(index);
			for(int i = 0; i < listHeaders.length; i++){
				String display = listHeaders[i].split(":")[1];
				fieldNames.add(listHeaders[i].split(":")[0]);
				HSSFCell cell = row.createCell(i);
				HSSFRichTextString text = new HSSFRichTextString(display);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(text);
			}
		}
		//遍历集合数据，产生数据行
		try {
			Iterator<T> it = dataset.iterator();
			while (it.hasNext()) {
				index++;
				row = sheet.createRow(index);
				T t = (T) it.next();
				//利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
				if(listHeaders == null ||listHeaders.length==0){
					Field[] fields = t.getClass().getDeclaredFields();
					if(fields.length == 0)
						continue;
					fieldNames.clear();
					for(Field _f : fields){
						fieldNames.add(_f.getName());
					}
				}
				List<Object> objs = TypeUtil.getFieldValues(t,fieldNames);
				//List<Object> objs = TypeUtil.getValueByNaviField(t,fieldNames);
				for(int i=0;i<objs.size();i++){
					HSSFCell cell = row.createCell(i);
					cell.setCellStyle(cellStyle);
					Object obj = objs.get(i);
					setCellValue(cell,obj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}	
		ByteArrayOutputStream out = null;
		InputStream inputStream = null;
		try {
			out = new ByteArrayOutputStream();
			workbook.write(out);
			out.flush();
			byte[] outByte = out.toByteArray();
			inputStream = new ByteArrayInputStream(outByte, 0, outByte.length);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if (null != inputStream) {
					inputStream.close();
				}
				if (null != out) {
					out.close();
				}
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return inputStream;
	}
	/**
	 * 
	 * 功能：导出excel表格，写入输出流<br/>
	 *
	 * @author 席金红
	 * @version 2014-8-8 上午08:53:55 <br/>
	 */
	public static <T extends BaseEntity> ByteArrayOutputStream exportExcelOutStream(String title, String[] listHeaders,Collection<T> dataset){
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		if(title == null || "".equals(title))
			title ="sheet1";
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		//设置表头样式
		HSSFCellStyle headerStyle = getHeaderStyle(workbook);
		//设置表格样式
		HSSFCellStyle cellStyle = getCellStyle(workbook);
		HSSFRow row = null;
		int index = -1;
		List<String> fieldNames = new ArrayList<String>();
		if(listHeaders != null && listHeaders.length>0){
			index++;
			//产生表格标题行
			row = sheet.createRow(index);
			for(int i = 0; i < listHeaders.length; i++){
				String display = listHeaders[i].split(":")[1];
				fieldNames.add(listHeaders[i].split(":")[0]);
				HSSFCell cell = row.createCell(i);
				HSSFRichTextString text = new HSSFRichTextString(display);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(text);
			}
		}
		//遍历集合数据，产生数据行
		try {
			Iterator<T> it = dataset.iterator();
			while (it.hasNext()) {
				index++;
				row = sheet.createRow(index);
				T t = (T) it.next();
				//利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
				if(listHeaders == null ||listHeaders.length==0){
					Field[] fields = t.getClass().getDeclaredFields();
					if(fields.length == 0)
						continue;
					fieldNames.clear();
					for(Field _f : fields){
						fieldNames.add(_f.getName());
					}
				}
				List<Object> objs = TypeUtil.getFieldValues(t,fieldNames);
				for(int i=0;i<objs.size();i++){
					HSSFCell cell = row.createCell(i);
					cell.setCellStyle(cellStyle);
					Object obj = objs.get(i);
					setCellValue(cell,obj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}	
		ByteArrayOutputStream out = null;
		
		try {
			out = new ByteArrayOutputStream();
			workbook.write(out);
			out.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return out;
	}
	
	/**
	 * 功能：<br/>
	 *
	 * @author 席金红
	 * @version 2014-10-10 下午04:29:14 <br/>
	 */
	public static <T extends BaseEntity> ByteArrayOutputStream exportExcelOutStream(String title, String[] listHeaders,List<Map<String, Object>> dataset){
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		if(title == null || "".equals(title))
			title ="sheet1";
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		//设置表头样式
		HSSFCellStyle headerStyle = getHeaderStyle(workbook);
		//设置表格样式
		HSSFCellStyle cellStyle = getCellStyle(workbook);
		HSSFRow row = null;
		int index = -1;
		List<String> fieldNames = new ArrayList<String>();
		if(listHeaders != null && listHeaders.length>0){
			index++;
			//产生表格标题行
			row = sheet.createRow(index);
			for(int i = 0; i < listHeaders.length; i++){
				String display = listHeaders[i].split(":")[1];
				fieldNames.add(listHeaders[i].split(":")[0]);
				HSSFCell cell = row.createCell(i);
				HSSFRichTextString text = new HSSFRichTextString(display);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(text);
			}
		}
		
		//遍历集合数据，产生数据行
		try {
			if (null != dataset && dataset.size() > 0) {
				for (Map<String, Object> obj : dataset) {
					index++;
					row = sheet.createRow(index);
					for (int i = 0; i < fieldNames.size(); i ++) {
						HSSFCell cell = row.createCell(i);
						cell.setCellStyle(cellStyle);
						Object v = obj.get(fieldNames.get(i));
						setCellValue(cell,v);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}	
		
		ByteArrayOutputStream out = null;
		
		try {
			out = new ByteArrayOutputStream();
			workbook.write(out);
			out.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return out;
	}
	/**
	 * 获得字体样式
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jul 10, 2013 4:26:04 PM <br/>
	 */
	public static HSSFFont getFont(HSSFWorkbook workbook,short fontColor,short fontBig,String fontStyle,short fontWidth){
		HSSFFont font = workbook.createFont();
		font.setColor(fontColor);//字体颜色
		font.setFontHeightInPoints(fontBig);//字体高度
		font.setFontName(fontStyle);//字体 
		font.setBoldweight(fontWidth);//宽度
		return font;
	}
	
	/**
	 * 获得表头样式
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jul 10, 2013 3:38:03 PM <br/>
	 */
	public static HSSFCellStyle getHeaderStyle(HSSFWorkbook workbook){
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成一个字体
		HSSFFont font = getFont(workbook,HSSFColor.VIOLET.index,(short) 12,"宋体",HSSFFont.BOLDWEIGHT_BOLD);
		//font.setColor(HSSFColor.VIOLET.index);
		//font.setFontHeightInPoints((short) 12);
		//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		// 把字体应用到当前的样式
		style.setFont(font); 
		
		return style;
	}
	/**
	 * 获得表格样式
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jul 10, 2013 3:38:03 PM <br/>
	 */
	public static HSSFCellStyle getCellStyle(HSSFWorkbook workbook){
		
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);//前景色设置
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style.setFont(font);
		
		return style;
	}
	
	/**
	 * 设置标题内容
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Nov 25, 2014 4:31:20 PM <br/>
	 */
	public static void setHeaderValue(String value,Cell cell,HSSFCellStyle cellStyle){
		HSSFRichTextString text = new HSSFRichTextString(value);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(text);
	}
	
	public HSSFRow copyAndInsertRow(int fromRowNum, int targetRowNum) {
		return copyAndInsertRow(0, fromRowNum, targetRowNum);
	}

	public HSSFWorkbook getWb() {
		return wb;
	}

	public void setWb(HSSFWorkbook wb) {
		this.wb = wb;
	}

	public void setForceFormulaRecalculation(boolean v) {
		setForceFormulaRecalculation(wb.getSheetAt(0), v);
	}

	public void setForceFormulaRecalculation(HSSFSheet sheet, boolean v) {
		sheet.setForceFormulaRecalculation(v);
	}
	
	public static String cellEmptyToNull(Cell cell) throws Exception {
		if (null == cell)
			return null;
		
		cell.setCellType(Cell.CELL_TYPE_STRING);
		String value = cell.getStringCellValue().trim();
		if ("".equals(value)) 
			return null;
		
		return value;
	}
	
	/**
	 * 功能：非空校验<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-8-21 上午09:13:27 <br/>
	 */
	public static String checkCellValue(Cell cell, int row, String colName, boolean required) throws Exception {
		String cellValue = cellEmptyToNull(cell);
		if (required && (null == cellValue)) 
			throw new SystemException("第" + row + "行，【" + colName + "】列信息不允许为空，导入失败！");
		
		return cellValue;
	}
	
	/**
	 * 功能：<br/>
	 *
	 * @author 孟雪勤
	 * @version 2015-3-29 上午09:39:18 <br/>
	 */
	public static String checkCellValue(Cell cell, int row, String colName, boolean required, StringBuffer sb) throws Exception {
		String cellValue = cellEmptyToNull(cell);
		if (required && (null == cellValue)) 
			sb.append("第" + row + "行，【" + colName + "】列信息不允许为空！");
		return cellValue;
	}
	
	/**
	 * 功能：检查日期是否合法<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-8-21 上午09:26:56 <br/>
	 */
	public static String checkDateValue(Cell cell, int row, String colName, boolean required) throws Exception {
		// 非空校验
		String date = checkCellValue(cell, row, colName, required);
		if (null == date)
			return null;
		
		// 格式合法校验
		if ((!date.contains("-")) || (date.length() != 10)) 
			throw new SystemException("第" + row + "行，【" + colName + "】列日期格式输入非法，导入失败！");
		
		try {
			sdfDate.setLenient(false);
			sdfDate.parse(date);
		}catch (ParseException e) {
			throw new SystemException("第" + row + "行，【" + colName + "】列日期输入不合法，导入失败！");
		}
		
		return date;
	}
	
	/**
	 * 功能：检查日期是否合法<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-8-21 上午09:26:56 <br/>
	 */
	public static String checkDateValue(Cell cell, int row, String colName, boolean required, StringBuffer sb) throws Exception {
		// 非空校验
		String date = checkCellValue(cell, row, colName, required);
		if (null == date)
			return null;
		
		// 格式合法校验
		if ((!date.contains("-")) || (date.length() != 10)) 
			sb.append("第" + row + "行，【" + colName + "】列日期格式输入非法！");
		
		try {
			sdfDate.setLenient(false);
			sdfDate.parse(date);
		}catch (ParseException e) {
			sb.append("第" + row + "行，【" + colName + "】列日期输入不合法！");
		}
		
		return date;
	}
	
	/**
	 * 功能：根据业务元素名称获得其编码<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-8-21 下午04:46:02 <br/>
	 */
	public static BussinessEleDetail getEleDetail(List<BussinessEleDetail> list, Cell cell, int row, String colName, boolean required) throws Exception {
		
		// 非空校验
		String name = checkCellValue(cell, row, colName, required);
		if (null == name)
			return null;
		
		BussinessEleDetail eleDetail = null;
		boolean exist = false;
		if (null != list && list.size() > 0) {
			for (BussinessEleDetail detail : list) {
				if (detail.getValue().equals(name)) {
					eleDetail = detail;
					exist = true;
					break;
				}
			}
		} 
		if (!exist) 
			throw new SystemException("第" + row + "行，【" + colName + "】列，列值为【" + name + "】的" + colName + "信息不存在，导入失败！");
			
		return eleDetail;
	}
	
	/**
	 * 功能：根据业务元素名称获得其编码<br/>
	 *
	 * @author 孟雪勤
	 * @version 2015-3-29 上午09:44:03 <br/>
	 */
	public static BussinessEleDetail getEleDetail(List<BussinessEleDetail> list, Cell cell, int row, String colName, boolean required, StringBuffer sb) throws Exception {
		
		// 非空校验
		String name = checkCellValue(cell, row, colName, required);
		if (null == name)
			return null;
		
		BussinessEleDetail eleDetail = null;
		boolean exist = false;
		if (null != list && list.size() > 0) {
			for (BussinessEleDetail detail : list) {
				if (detail.getValue().equals(name)) {
					eleDetail = detail;
					exist = true;
					break;
				}
			}
		} 
		if (!exist) 
			sb.append("第" + row + "行，【" + colName + "】列，列值为【" + name + "】的" + colName + "信息不存在！");
			
		return eleDetail;
	}
	
	/**
	 * 功能：下载文件名中文乱码解决<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-6-19 下午04:09:27 <br/>
	 */
	public static String getFileName(String fileName) {
		try {
			return new String(fileName.getBytes(), "ISO8859-1");
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new SystemException("文件名不支持编码异常：" + e.getMessage());
		}
	}
}
