package com.syq.web.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syq.biz.bo.IEPassportBo;

public class IEPassportHelper {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(IEPassportHelper.class);
  
  private static final   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
  
  private static final String BLACKMSG = "$不能为空";
  
  private static final String LENGTHMSG = "$长度必能大于#";

  public static final boolean processImportPassport(Sheet sheet,List<IEPassportBo> iePassportBoes) {

    boolean isOK = true;
    // 获取header
    final XSSFRow rowHeader = (XSSFRow) sheet.getRow(0);
    int rowSize = sheet.getLastRowNum() + 1;
    String msg = null;
    
     for (int i = 1; i < rowSize; i++) {
      XSSFRow hssfRow = (XSSFRow) sheet.getRow(i);
      if(hssfRow == null) {
        break;
      }
      
      // 护照编码
      int cell = 0;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String passportNo = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(passportNo)) {
//          ieTaskBoes.add(getErrorIEPassportBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
//          isOK = false;
        //停止条件
          break;
      }else if(passportNo.length() > 30) {
        msg = LENGTHMSG.replace("$", rowHeader.getCell(cell).getStringCellValue());
        msg = msg.replace("#", "15");
        iePassportBoes.add(getErrorIEPassportBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
        isOK = false;
        continue;
      }
      //姓名
      cell = 1;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String name = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(name)) {
        iePassportBoes.add(getErrorIEPassportBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }
      //性别
      cell = 2;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String sex = hssfRow.getCell(cell).getStringCellValue();
      int sexInt = 0;
      if (StringUtils.isBlank(sex)) {
        iePassportBoes.add(getErrorIEPassportBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }else{
        if("男".equals(sex)) {
          
        }
      }
      
      //身份证号
      cell = 3;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String idNumber = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(idNumber)) {
          iePassportBoes.add(getErrorIEPassportBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }
      
      //颁发日期
      cell = 4;
      Date dateIssue = null;
      try{
        dateIssue = hssfRow.getCell(cell).getDateCellValue();
      }catch(Exception e) {
        iePassportBoes.add(getErrorIEPassportBo(hssfRow.getRowNum() + 1, "日期格式错误!"+e.getMessage()));
        isOK = false;
        continue;
      }
      
      //过期日期
      cell = 5;
      Date dateExpire = null;
      try{
        dateExpire = hssfRow.getCell(cell).getDateCellValue();
     }catch(Exception e) {
       iePassportBoes.add(getErrorIEPassportBo(hssfRow.getRowNum() + 1, "日期格式错误!"+e.getMessage()));
       isOK = false;
       continue;
     }
      
      //籍贯
      cell = 6;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String placeBirth = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(placeBirth)) {
        iePassportBoes.add(getErrorIEPassportBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }
      
      //注册地址
      cell = 7;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String placeIssue = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(placeIssue)) {
        iePassportBoes.add(getErrorIEPassportBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }
      
     
      
      iePassportBoes.add(getIEPassportBo(hssfRow.getRowNum() + 1,  passportNo,name,sexInt,idNumber,dateIssue,dateExpire,placeBirth,placeIssue));
      
    }
    
    return isOK;
    

  }
  
  private static IEPassportBo getErrorIEPassportBo(int num,String errorMsg) {
    IEPassportBo imQues = new IEPassportBo(num, errorMsg);
    
    return imQues;
  }
  
  private static IEPassportBo getIEPassportBo(int num,String passportNo,String name,int sex,String idNumber,Date dateIssue,Date dateExpire, String placeBirth,String placeIssue) {
    IEPassportBo iePassportBo = new IEPassportBo(num, passportNo,name,sex,idNumber,dateIssue,dateExpire,placeBirth,placeIssue);
    
    return iePassportBo;
  }
  
}
