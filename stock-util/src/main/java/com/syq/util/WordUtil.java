package com.syq.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.syq.word.entity.WordPersons;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class WordUtil {
  
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(WordUtil.class);
  
  public static String FILE_PATH = new File(WordUtil.class.getClassLoader().getResource(File.separator).getFile()).getParentFile().getParentFile().getAbsolutePath()+File.separator+"word"+File.separator;
  
  public static String FILE_NAME_PREFIX = "中检公司批件";
  
  public static String FILE_NAME_SUFFIX = ".doc";
  
  public static String WORD_TEMP_NAME = "faff.ftl";
  
  
  
/**
* @Desc：生成word文件
* @Author：张轮
* @Date：2014-1-22下午05:33:42
* @param dataMap word中需要展示的动态数据，用map集合来保存
* @param templateName word模板名称，例如：test.ftl
* @param filePath 文件生成的目标路径，例如：D:/wordFile/
* @param fileName 生成的文件名称，例如：test.doc
*/
public static File createWord(Map<?, ?> dataMap,String templateName,String fileName){
    try {
      // 创建配置实例
      Configuration configuration = new Configuration();

      // 设置编码
      configuration.setDefaultEncoding("UTF-8");

      // ftl模板文件统一放至 com.lun.template 包下面
      configuration.setClassForTemplateLoading(WordUtil.class, "/com/wzax/word/temp/");

      // 获取模板
      Template template = configuration.getTemplate(templateName);

      // 输出文件
      File outFile = getFile(fileName);

      // 如果输出目标文件夹不存在，则创建
      if (!outFile.getParentFile().exists()) {
        outFile.getParentFile().mkdirs();
      }

      // 将模板和数据模型合并生成文件
      Writer out =
          new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));


      // 生成文件
      template.process(dataMap, out);

      // 关闭流
      out.flush();
      out.close();
      return outFile;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

public static File getFile(String fileName) throws IOException {
  String path = FILE_PATH;
  mkdirsParentFiles(FILE_PATH);
  
  File file = new File(path,fileName);
  return file;
  
}

public static void mkdirsParentFiles(String path) {
  File file = new File(path);
  if(!file.exists()) {
    file.mkdirs();
  }
}

public static InputStream getWordFile(String fileName){
  try {
   //解决中文乱码
   fileName = URLDecoder.decode(fileName, "UTF-8");
   /** 返回最终生成的word文件流  */
   return new FileInputStream(FILE_PATH + File.separator + fileName);
  } catch (Exception e) {
   e.printStackTrace();
   return null;
  }
}

public static Map<String, Object> createTestDataMap(){
  Map<String, Object> dataMap = Maps.newHashMap();
  dataMap.put("year", "2017");
  dataMap.put("userName", "荆雷");
  dataMap.put("personCount", "3");
  dataMap.put("beginYear", "2016");
  dataMap.put("beginMonth", "07");
  dataMap.put("beginDay", "07");
  dataMap.put("endYear", "2017");
  dataMap.put("endMonth", "08");
  dataMap.put("endDay", "08");
  dataMap.put("taskCountry", "中国,日本");
  dataMap.put("taskName", "工厂巡查");
  dataMap.put("stickDay", "5");
  dataMap.put("costSource", "自己");
  
  List<WordPersons> personList = Lists.newArrayList();
  WordPersons wp1 = new WordPersons();
  wp1.setUserName("荆雷");
  wp1.setIdNumber("12345678");
  wp1.setDeptName("网众");
  
  WordPersons wp2 = new WordPersons();
  wp2.setUserName("邵玉琪");
  wp2.setIdNumber("87654321");
  wp2.setDeptName("安信");
  
  personList.add(wp1);
  personList.add(wp2);
  
  dataMap.put("personList", personList);
  
  return dataMap;
}

public static File testCreateWord(){
  String templateName = WORD_TEMP_NAME;
  String fileName = FILE_NAME_PREFIX + FILE_NAME_SUFFIX;

  return createWord(createTestDataMap(), templateName, fileName);
}

public static File testCreateWord(Map<String, Object> dataMap){
  String templateName = WORD_TEMP_NAME;
  String fileName = FILE_NAME_PREFIX + FILE_NAME_SUFFIX;

  return createWord(dataMap, templateName, fileName);
}

  public static void main (String[] args){
    testCreateWord();
  }
}
