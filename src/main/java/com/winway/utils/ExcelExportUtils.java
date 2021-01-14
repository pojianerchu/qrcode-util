package com.winway.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.winway.enums.EmpModelEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFDataValidationHelper;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.util.ReflectionUtils;

public class ExcelExportUtils {
	/**
	 * 导出模板
	 * @param empModelEnum
	 * @param response
	 */
	public static <X> void exportExcel(
			EmpModelEnum empModelEnum, HttpServletResponse response){
		InputStream  input=null;
		OutputStream out=null;
		try {
			input = Thread.currentThread().getContextClassLoader().getResourceAsStream(empModelEnum.getTemplatePath());
			Workbook workBook=WorkbookFactory.create(input);
			String fileName=empModelEnum.getName()+"模板"+empModelEnum.getExtension();
			fileName = new String(fileName.getBytes(), "iso-8859-1");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename="+fileName);
			out=response.getOutputStream();
			workBook.write(out);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(out!=null)out.close();
				if(input!=null) input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//为空输出警告
	private static boolean isWriteTip(HttpServletResponse response,List list){
		OutputStream out=null;
		if(list==null||list.size()==0){
			try {
				out=response.getOutputStream();
				String data = "提示：无数据导出！";
				out.write(data.getBytes("GBK"));
			} catch (EncryptedDocumentException | IOException e) {
				e.printStackTrace();
			}finally{
				try {
					if(out!=null)out.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
			return false;
		}
		return true;
	}
	//获取值
	public static String getValue(Object obj,String colHead){
		String value=null;
		Field[] fields=obj.getClass().getDeclaredFields();
		for(Field field:fields){
			if(field.getName().equals(colHead)){
				try {
					ReflectionUtils.makeAccessible(field);//					field.setAccessible(true);
					Object val = field.get(obj);
					if (val == null) break;
					if (field.getType() == Timestamp.class) {
						value = val.toString().substring(0, val.toString().lastIndexOf("."));
						break;
					} else if (field.getType() == Date.class) {
						value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(val);
						break;
					} else if (field.getType() == LocalDateTime.class){
						DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
						if(val!=null){
							value = dtf2.format((LocalDateTime )val);
						}
						break;
					}else{
						if(val!=null){
							value=val.toString();
						}
						break;
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	//获得每个单元格的样式
	public static List<CellStyle> getCellStyles(int rowStart,int colNum,Sheet sheet){
		List<CellStyle> list=new ArrayList<CellStyle>();
		Row row=sheet.getRow(rowStart);
		for(int colRow=0;colRow<colNum;colRow++){
			Cell cell=row.getCell(colRow);
			list.add(cell.getCellStyle());
		}
		return list;
	}
	//获取行样式
	public static short getRowStyle(int rowStart,Sheet sheet){
		Row row=sheet.getRow(rowStart);
		return row.getHeight();
	}
	/*
	 * 设置下拉框
	 */
	private static void setValidation(Sheet sheet,List<? extends DataValidation> valis,int firstRow,int lastRow){
		if(valis==null||valis.size()==0)return;
		int i=0;
		List<Integer> _colSelects=new ArrayList<Integer>();
		for(DataValidation v:valis){
			CellRangeAddress[] addres=v.getRegions().getCellRangeAddresses();
			for(CellRangeAddress a:addres){
				if(!_colSelects.contains(a.getFirstColumn())){
					_colSelects.add(a.getFirstColumn());
				}
			}
			String[] values=v.getValidationConstraint().getExplicitListValues();
			String[] vas=new String[values.length];
			for(int k=0;k<values.length;k++){
				vas[k]=values[k].replace("\"", "");
			}
			DataValidationConstraint constraint=null;
			DataValidation dataValidation=null;
			DataValidationHelper helper=null;
			CellRangeAddressList address=new CellRangeAddressList(firstRow, lastRow, _colSelects.get(i), _colSelects.get(i));
			if(sheet instanceof HSSFSheet){
				helper=new HSSFDataValidationHelper((HSSFSheet) sheet);
				constraint=helper.createExplicitListConstraint(vas);
				dataValidation=helper.createValidation(constraint, address);
				dataValidation.setSuppressDropDownArrow(false);
			}else if(sheet instanceof XSSFSheet){
				helper=new XSSFDataValidationHelper((XSSFSheet) sheet);
				constraint=helper.createExplicitListConstraint(vas);
				dataValidation=helper.createValidation(constraint, address);
				//下拉框箭头与显示框
				dataValidation.setSuppressDropDownArrow(true);  
		        dataValidation.setShowErrorBox(true);  
			}else if(sheet instanceof SXSSFSheet){
				constraint=sheet.getDataValidationHelper().createExplicitListConstraint(values);
				dataValidation=sheet.getDataValidationHelper().createValidation(constraint, address);
				dataValidation.setSuppressDropDownArrow(false);
			}
			sheet.addValidationData(dataValidation);
			i++;
		}
	}
	
	/**
	 * 
	 * 往Excel文件写入数据保存到本地文件中
	 * 
	 * @param empModelEnum
	 * @param list
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public static <X> void ReadAndWriteToExcelBefore(EmpModelEnum empModelEnum, Object list,
													 String additionalFileName, File targetFile, boolean isExsite, InputStream in){
		Class<X> clazz=(Class<X>) empModelEnum.getEntityClass();
		List<X> xList=(List<X>) list;
		InputStream  input=null;
		OutputStream out=null;
		List<CellStyle> cellStyles=null;
		List<? extends DataValidation> valis=null;
		short rowStyle=0;
		Map<String,Object> mapR=new HashMap<>();
		try {
			int rowStartNum=empModelEnum.getDataWriteStartRow();
			int colNum=empModelEnum.getColnum();
			String[] headers=empModelEnum.getHeader();
			int rowNum=rowStartNum;
			if(isExsite){
				input=new FileInputStream(targetFile);
			}else{
				input=in;
			}
			Workbook workBook=WorkbookFactory.create(input);
			Sheet sheet=workBook.getSheetAt(0);
			int rownum=0;
			if(isExsite){
				rownum=sheet.getLastRowNum();//如果存在文件则就在此文件上添加记录
				rowNum=rownum;
				rowStartNum=rownum;
			}
			Row row=null;
			Cell cell=null;
			int listIndex=0;
			for(int rowindex=rownum;rowindex<xList.size()+rownum;rowindex++){
				if(rowNum==rowStartNum){
					cellStyles= ExcelExportUtils.getCellStyles(rowStartNum, colNum, sheet);
					rowStyle= ExcelExportUtils.getRowStyle(rowStartNum, sheet);
					valis=sheet.getDataValidations();
				}
				if(isExsite)rowNum++;
				X x=xList.get(listIndex);
				row=sheet.createRow(rowNum);
				row.setHeight(rowStyle);
				for(int colindex=0;colindex<colNum;colindex++){
					cell=row.createCell(colindex);
					String value= ExcelExportUtils.getValue(x, headers[colindex]);
					
					cell.setCellValue(value);
					cell.setCellStyle(cellStyles.get(colindex));
				}
				rowNum++;
				listIndex++;
			}
			out=new FileOutputStream(targetFile);
			workBook.write(out);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(out!=null)out.close();
				if(input!=null) input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	/**
	 * 保留两位小数
	 *
	 * @param str
	 * @return
	 */
	public static String substring2(String str) {
		if (StringUtils.isNotBlank(str)) {
			BigDecimal b = new BigDecimal(Double.parseDouble(str));
			String real = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "";
			if (real.endsWith(".0")) {
				real = real.replace(".0", "");
			}
			return real;
		}
		return "";
	}
}
