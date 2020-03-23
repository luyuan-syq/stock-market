package com.syq.web.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Strings;

public class UploadHelper {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(UploadHelper.class);
  
  public static String FILE_PATH = new File(UploadHelper.class.getClassLoader().getResource(File.separator).getFile()).getParentFile().getParentFile().getAbsolutePath()+File.separator+"fileTemplate"+File.separator;
  
  
  /**
   * 保存文件，返回文件名
   * @param inputStream
   * @param extensionName
   * @return
   * created by Shaoyuqi on 2017年2月12日 下午4:22:01
   * @throws IOException 
   */
  public static String saveFile(MultipartFile inputFile,String path,String fileNameAndExtensionName) throws IOException {
    if(Strings.isNullOrEmpty(path)) {
      mkdirsParentFiles(FILE_PATH);
      path = FILE_PATH;
    }else{
      mkdirsParentFiles(path);
    }
    String uuid = UUID.randomUUID().toString();
    File file = new File(path,fileNameAndExtensionName);
    file.createNewFile();
    
    OutputStream out = null;
    try{
      out = new BufferedOutputStream(new FileOutputStream(file));
      out.write(getBuffer(inputFile));
    }catch(Exception e) {
      LOG.error(e.getMessage(),e);
    }finally{
      if(out != null) {
        out.close();
      }
    }
    
    return uuid;
    
  }
    
  
  public static File getFile(String path,String fileName) throws IOException {
    if(Strings.isNullOrEmpty(path)) {
      mkdirsParentFiles(FILE_PATH);
      path = FILE_PATH;
    }else{
      mkdirsParentFiles(path);
    }
    File file = new File(path,fileName);
    
    return file.exists()?file:null;
    
  }
  
  public static byte[] getBuffer(MultipartFile file) {
    Long fileSize = file.getSize();
    
    byte[] buffer = new byte[fileSize.intValue()];
    InputStream stream = null;
    try{
      stream = new BufferedInputStream(file.getInputStream());
      stream.read(buffer);
    }catch(Exception e) {
      LOG.error(e.getMessage(),e);
      return null;
    }finally{
      if(stream != null) {
        try {
          stream.close();
        } catch (IOException e) {
          LOG.error(e.getMessage(),e);
        }
      }
    }
    return buffer;
    
  }
  
  public static void mkdirsParentFiles(String path) {
    File file = new File(path);
    if(!file.exists()) {
      file.mkdirs();
    }
  }
}
