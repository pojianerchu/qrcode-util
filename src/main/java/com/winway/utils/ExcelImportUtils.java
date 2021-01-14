package com.winway.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.winway.enums.ImpModelEnum;
import com.winway.qrcode.model.Message;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;



public class ExcelImportUtils {


	/**
	 * IO读取excel内容
	 * @return
	 */
	public static Object readExcel(MultipartFile excelFile, ImpModelEnum impEnum, Message message){
		Integer headerRow=impEnum.getHeadeindex();
		Integer firstRow=impEnum.getStartrow();
		List<String[]> listData=new ArrayList<String[]>();
		InputStream is=null;
		Workbook book=null;
		Sheet sheet=null;
		Row row=null;
		Cell cell=null;
		int rownum=-1;
		int colnum=-1;
		List<String> headerStrs=null;
		try {
			is=excelFile.getInputStream();
		    book=WorkbookFactory.create(is);
			sheet=book.getSheetAt(0);
			row=sheet.getRow(headerRow);
			rownum=sheet.getLastRowNum();
			colnum=row.getPhysicalNumberOfCells();
			headerStrs=getHeader(row,colnum);
			//验证头部
			validateHeader(headerStrs,impEnum,message);
			if(message.getCode()==-1){
				return listData;
			}
			for(int rowindex=firstRow;rowindex<=rownum;rowindex++){
				row=sheet.getRow(rowindex);
				if(row!=null){
					String[] strs=new String[colnum];
					for(int colindex=0;colindex<colnum;colindex++){
						cell=row.getCell(colindex);
						String value=getCellValue(cell);
						strs[colindex]=value;
					}
					if(isAllNull(strs))continue;
					listData.add(strs);
				}
			}
			if(listData.size()==0){
				message.setMsg("没有可导入的数据");
				message.setCode(-1);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(is!=null)is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listData;
	}
	/**
	 * 获取excel头部
	 * @param row
	 * @param colnum
	 * @return
	 */
	public static List<String> getHeader(Row row,int colnum){
		List<String> list=new ArrayList<String>();
		Cell cell=null;
		for(int colindex=0;colindex<colnum;colindex++){
			//row.getCell(colindex).setCellType(CellType.STRING);
			cell=row.getCell(colindex);
			String value=cell.getStringCellValue();
			list.add(value);
		}
		return list;
	}
	public static String getCellValue(Cell cell) {
		if(cell==null){
			return null;
		}
        String value = "";
        // 以下是判断数据的类型
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_NUMERIC:
			short format = cell.getCellStyle().getDataFormat();
			value = cell.getNumericCellValue() + "";
            if (DateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
                if (date != null) {
					value = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss").format(date);
                } else {
                    value = "";
                }
            } else {
                //value = new DecimalFormat("0").format(cell.getNumericCellValue());
				value =cell.getNumericCellValue()+"";
            }
            break;
        case Cell.CELL_TYPE_STRING: // 字符串
            value = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_BOOLEAN: // Boolean
            value = cell.getBooleanCellValue() + "";
            break;
        case Cell.CELL_TYPE_FORMULA: // 公式
            value = cell.getCellFormula() + "";
            break;
        case Cell.CELL_TYPE_BLANK: // 空值
            value = "";
            break;
        case Cell.CELL_TYPE_ERROR: // 故障
            value = "非法字符";
            break;
        default:
            value = "未知类型";
            break;
        }
        return value;
    }
	public static void validateHeader(List<String> headerStrs,ImpModelEnum impEnum,Message message){
		String[] headers=impEnum.getZhHeader();
		if(headers.length!=headerStrs.size()){
			message.setMsg("与excel模板头部不匹配");
			message.setCode(-1);
			return;
		}
		/*for(int i=0;i<headers.length;i++){
			if(!headers[i].trim().equals(headerStrs.get(i).trim())){
				aj.setMsg("excel头部不吻合");
				aj.setSuccess(false);
				return;
			}
		}*/
	}
	public static boolean isAllNull(String[] vals){
		boolean reg=true;
		for(int i=0;i<vals.length;i++){
			if(!Strings.isBlank(vals[i])){
				if(!vals[i].trim().equals("")){
					reg=false;
					break;
				}
			}
		}
		return reg;
	}

	public static void main(String[] args) {
	}
}
