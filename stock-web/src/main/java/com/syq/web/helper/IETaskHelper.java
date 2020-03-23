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

import com.syq.biz.bo.IEPersonBo;
import com.syq.biz.bo.IETaskBo;
import com.syq.util.constant.CostSourceType;

public class IETaskHelper {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(IETaskHelper.class);
  
  private static final   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
  
  private static final String BLACKMSG = "$不能为空";
  
  private static final String LENGTHMSG = "$长度必能大于#";

  public static final boolean processImportTask(Sheet sheet,List<IETaskBo> ieTaskBoes) {

    boolean isOK = true;
    // 获取header
    final XSSFRow rowHeader = (XSSFRow) sheet.getRow(0);
    int rowSize = sheet.getLastRowNum() + 1;
    String msg = null;
    
     for (int i = 1; i < rowSize; i++) {
      XSSFRow hssfRow = (XSSFRow) sheet.getRow(i);
      
      // 批件号
      int cell = 0;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String instructionNo = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(instructionNo)) {
//          ieTaskBoes.add(getErrorIETaskBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
//          isOK = false;
        //停止条件
          break;
      }else if(instructionNo.length() > 15) {
        msg = LENGTHMSG.replace("$", rowHeader.getCell(cell).getStringCellValue());
        msg = msg.replace("#", "15");
        ieTaskBoes.add(getErrorIETaskBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
        isOK = false;
        continue;
      }
      //任务名称 
      cell = 1;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String taskName = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(taskName)) {
          ieTaskBoes.add(getErrorIETaskBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }
      //团长姓名
      cell = 2;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String headerName = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(headerName)) {
          ieTaskBoes.add(getErrorIETaskBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }
      //团长身份账号
      cell = 3;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String headerIdCard = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(headerIdCard)) {
          ieTaskBoes.add(getErrorIETaskBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }
      
      //组团单位
      cell = 4;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String groupUnit = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(groupUnit)) {
          ieTaskBoes.add(getErrorIETaskBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }
      
      //出差开始时间
      cell = 5;
      Date beginTimeDate = null;
      try{
         beginTimeDate = hssfRow.getCell(cell).getDateCellValue();
      }catch(Exception e) {
        ieTaskBoes.add(getErrorIETaskBo(hssfRow.getRowNum() + 1, "日期格式错误!"+e.getMessage()));
        isOK = false;
        continue;
      }
      
      //出差结束时间
      cell = 6;
      Date endTimeDate = null;
      try{
        endTimeDate = hssfRow.getCell(cell).getDateCellValue();
     }catch(Exception e) {
       ieTaskBoes.add(getErrorIETaskBo(hssfRow.getRowNum() + 1, "日期格式错误!"+e.getMessage()));
       isOK = false;
       continue;
     }
      
      //出访国家
      cell = 7;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String country = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(country)) {
          ieTaskBoes.add(getErrorIETaskBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }
      
      //出访目的
      cell = 8;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String visitPurpose = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(visitPurpose)) {
          ieTaskBoes.add(getErrorIETaskBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }
      
      //费用来源
      cell = 9;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String costSource = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(costSource)) {
          ieTaskBoes.add(getErrorIETaskBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }
      
      //出批件日期
      cell = 10;
      Date instructionDate =  null;
      try{
        instructionDate = hssfRow.getCell(cell).getDateCellValue();
     }catch(Exception e) {
       ieTaskBoes.add(getErrorIETaskBo(hssfRow.getRowNum() + 1, "日期格式错误!"+e.getMessage()));
       isOK = false;
       continue;
     }
      
      //是否有集团领导班子成员: 0 否；1 是
      cell = 11;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String hasGroupMember = hssfRow.getCell(cell).getStringCellValue();
      int hasGroupMemberInt = 0;
      if (StringUtils.isBlank(hasGroupMember)) {
          ieTaskBoes.add(getErrorIETaskBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }else{
        if("是".equals(hasGroupMember)) {
          hasGroupMemberInt = 1;
        }
      }
      
      //是否列入外事计划
      cell = 12;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String hasFaffPlan = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(hasGroupMember)) {
          ieTaskBoes.add(getErrorIETaskBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }
      
      //经办人
      cell = 13;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String transactor = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(transactor)) {
          ieTaskBoes.add(getErrorIETaskBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }
      
      ieTaskBoes.add(getIETaskBo(hssfRow.getRowNum() + 1, instructionNo,taskName,headerName,headerIdCard,groupUnit,beginTimeDate,endTimeDate,country,visitPurpose,costSource,instructionDate,hasGroupMemberInt,hasFaffPlan,transactor));
      
    }
    
    return isOK;
    

  }
  
  
  public static final boolean processImportTaskPersons(Sheet sheet,List<IEPersonBo> iePersonBoes) {

    boolean isOK = true;
    // 获取header
    final XSSFRow rowHeader = (XSSFRow) sheet.getRow(0);
    int rowSize = sheet.getLastRowNum() + 1;
    String msg = null;
    
     for (int i = 1; i < rowSize; i++) {
      XSSFRow hssfRow = (XSSFRow) sheet.getRow(i);
      
      // 批件号
      int cell = 0;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String instructionNo = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(instructionNo)) {
//          ieTaskBoes.add(getErrorIETaskBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
//          isOK = false;
        //停止条件
          break;
      }else if(instructionNo.length() > 15) {
        msg = LENGTHMSG.replace("$", rowHeader.getCell(cell).getStringCellValue());
        msg = msg.replace("#", "15");
        iePersonBoes.add(getErrorIEPersonBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
        isOK = false;
        continue;
      }
      //姓名
      cell = 1;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String userName = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(userName)) {
        iePersonBoes.add(getErrorIEPersonBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }
      //身份证号
      cell = 2;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String idNumber = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(idNumber)) {
        iePersonBoes.add(getErrorIEPersonBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }
      //性别
      cell = 3;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String sexey = hssfRow.getCell(cell).getStringCellValue();
      int sexeyInt = 0;
      if (StringUtils.isBlank(sexey)) {
        iePersonBoes.add(getErrorIEPersonBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }else{
        if ("女".equals(sexey)) {
          sexeyInt = 1;
        }
      }
      
      //出生日期
      cell = 4;
      Date birthday = null;
      try{
        birthday = hssfRow.getCell(cell).getDateCellValue();
      }catch(Exception e) {
        iePersonBoes.add(getErrorIEPersonBo(hssfRow.getRowNum() + 1, "日期格式错误!"+e.getMessage()));
        isOK = false;
        continue;
      }
      
      //出生地
      cell = 5;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String placeBirth = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(placeBirth)) {
        iePersonBoes.add(getErrorIEPersonBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }
      
      //工作单位
      cell = 6;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String deptName = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(deptName)) {
        iePersonBoes.add(getErrorIEPersonBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }
      
      //职务
      cell = 7;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String business = hssfRow.getCell(cell).getStringCellValue();
      if (StringUtils.isBlank(business)) {
        iePersonBoes.add(getErrorIEPersonBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }
      
      //职务
      cell = 8;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String identity = hssfRow.getCell(cell).getStringCellValue();
      int identityInt = 0;
      if (StringUtils.isBlank(identity)) {
        iePersonBoes.add(getErrorIEPersonBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }else{
        
      }
      
      //是否列入外事计划
      cell = 9;
      hssfRow.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);
      String leader = hssfRow.getCell(cell).getStringCellValue();
      int isLeader = 0;
      if (StringUtils.isBlank(leader)) {
        iePersonBoes.add(getErrorIEPersonBo(hssfRow.getRowNum() + 1, BLACKMSG.replace("$", rowHeader.getCell(cell).getStringCellValue())));
          isOK = false;
          continue;
      }else{
        if("是".equals(leader)) {
          isLeader = 1;
        }
      }
      
      iePersonBoes.add(getIEPersonBo(hssfRow.getRowNum() + 1,instructionNo, userName,idNumber,sexeyInt,birthday,placeBirth,deptName,business,identityInt,isLeader));
      
    }
    
    return isOK;
    

  }
  
  private static IEPersonBo getErrorIEPersonBo(int num,String errorMsg) {
    IEPersonBo imQues = new IEPersonBo(num, errorMsg);
    
    return imQues;
  }
  
  private static IETaskBo getErrorIETaskBo(int num,String errorMsg) {
    IETaskBo imQues = new IETaskBo(num, errorMsg);
    
    return imQues;
  }
  
  private static IETaskBo getIETaskBo(int num,String instructionNo,String taskName,String headerName,String headerIdCard,String groupUnit,Date beginTime,Date endTime, String country,String visitPurpose
      ,String costSource,Date instructionDate,int hasGroupMember,String hasFaffPlan,String transactor) {
    IETaskBo ieTaskBo = new IETaskBo(num, instructionNo,taskName,headerName,headerIdCard,groupUnit,beginTime,endTime,country,visitPurpose,costSource,instructionDate,hasGroupMember,hasFaffPlan,transactor);
    
    return ieTaskBo;
  }
  
  private static IEPersonBo getIEPersonBo(int rowNum,String instructionNo,String userName,String idNumber,int sexeyInt,Date birthday,String placeBirth,String deptName,String business,int identity,int isLeader) {
    IEPersonBo iePersonBo = new IEPersonBo(rowNum, instructionNo,userName,idNumber,sexeyInt,birthday,placeBirth,deptName,business,identity,isLeader);
    
    return iePersonBo;
  }
}
