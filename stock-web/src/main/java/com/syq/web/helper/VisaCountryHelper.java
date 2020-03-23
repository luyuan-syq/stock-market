package com.syq.web.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syq.biz.bo.VisaCountryBo;

public class VisaCountryHelper {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(VisaCountryHelper.class);
  
  public static Map<String,Object> visaCountryBoToMap(List<VisaCountryBo> boes) {
    //创建根节点
    Map<String,Object> root = rootNode();
    if(boes != null && boes.size() > 0) {
      List<Map<String,Object>> children = new ArrayList<Map<String,Object>>();
      for(VisaCountryBo bo : boes) {
        Map<String,Object> node = new HashMap<String,Object>();
        Map<String, String> data = new HashMap<String, String>();
        Map<String, Object> attrMap = new HashMap<String, Object>();
        node.put("data", data);
        node.put("attr", attrMap);
        attrMap.put("id", bo.getId());
        attrMap.put("icon", "country");
        attrMap.put("code", bo.getCode());
        data.put("title", bo.getName());
        data.put("icon", "./images/tree/user.png");
        children.add(node);
      }
      root.put("children",children);
    }
    return root;
  }
  
  public static Map<String,Object> rootNode() {
    Map<String,Object> node = new HashMap<String,Object>();
    Map<String, String> data = new HashMap<String, String>();
    Map<String, Object> attrMap = new HashMap<String, Object>();
    node.put("data", data);
    node.put("attr", attrMap);
    attrMap.put("icon", "root");
    attrMap.put("title", "root");
    attrMap.put("id", "_countryTree");
    data.put("title", "国家树");
    data.put("icon", "./images/tree/house.png");
    node.put("children", null);
    node.put("state", "open");
    return node;
}
}
