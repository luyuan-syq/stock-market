package com.syq.web.controller;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.syq.util.JsonUtil;
import com.syq.web.utils.BaseBeanToJsonView;
import com.syq.web.utils.ImageHelper;

@RequestMapping("/fileUpload")
@Controller
public class FileUploadController {

  private static Logger logger = LoggerFactory.getLogger(FileUploadController.class);
  /** 图片后缀名 */
  private String fileExt = "jpg,JPG,png,PNG";

  @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
  @ResponseBody
  public String uploadPic(MultipartHttpServletRequest request, HttpServletResponse response)
      throws Exception {
    // Iterator<String> fileNames = request.getFileNames();
    // while(fileNames.hasNext()) {
    // System.out.println(fileNames.next());
    // }
    MultipartFile fileUpload = request.getFile("Filedata");
    response.setCharacterEncoding("UTF-8");
    String extensionName = null;
    String fileNameLong = null;
    // 文件名
    String fileName = null;
    String url = null;


    // 判断是否有文件
    if (!fileUpload.isEmpty()) {
      // 获取文件上传路径名称
      fileNameLong = fileUpload.getOriginalFilename();
      fileName = fileNameLong.substring(0, fileNameLong.lastIndexOf("."));
      // 获取文件扩展名
      extensionName = fileNameLong.substring(fileNameLong.lastIndexOf(".") + 1);
      if (("," + fileExt.toLowerCase() + ",").indexOf("," + extensionName.toLowerCase() + ",") < 0) {
        printInfo(response, "不允许上传此类型的文件,只能是JPG或这个PNG格式", null);
        return null;
      }
      Image src = javax.imageio.ImageIO.read(fileUpload.getInputStream());
      if (src == null) {
        printInfo(response, "请上传正常图片", null);
        return null;
      }

    } else {
      printInfo(response, "上传文件不能为空", null);
      return null;
    }
    InputStream image = null;
    try {
      image = fileUpload.getInputStream();
      url = ImageHelper.saveFile(fileUpload, null, extensionName);
    } catch (Exception e) {
      logger.error("图片接口异常:" + e.getMessage(), e);
      printInfo(response, "上传图片出错", null);
      return null;
    } finally {
      if (image != null) {
        image.close();
      }
    }
    printInfoWithName(response, null, url +"."+extensionName , fileName);
    return null;
  }



  @RequestMapping(value = "/showImg", method = RequestMethod.GET)
  @ResponseBody
  public void showPic(HttpServletRequest request, HttpServletResponse response, String fileName)
      throws Exception {



  }

  /**
   * 发送消息
   * 
   * @param response
   * @param err
   * @param success
   * @throws IOException
   */
  private void printInfo(HttpServletResponse response, String err, String success)
      throws IOException {
    response.setContentType("text/html;charset=UTF-8");
    BaseBeanToJsonView bt = new BaseBeanToJsonView();
    PrintWriter out = response.getWriter();
    if (err != null) {
      bt.setError(err);
    } else {
      bt.setSuccess(success);
    }
    out.println(JsonUtil.beanToJson(bt));
    out.flush();
    out.close();
  }

  /**
   * 发送消息
   * 
   * @param response
   * @param err
   * @param success
   * @param fileName
   * @throws IOException
   */
  private void printInfoWithName(HttpServletResponse response, String err, String success,
      String fileName) throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    BaseBeanToJsonView bt = new BaseBeanToJsonView();
    PrintWriter out = response.getWriter();
    if (err != null) {
      bt.setError(err);
    } else {
      bt.setSuccess(success);
    }
    bt.setFileName(fileName);
    out.println(JsonUtil.beanToJson(bt));
    out.flush();
    out.close();
  }
}
