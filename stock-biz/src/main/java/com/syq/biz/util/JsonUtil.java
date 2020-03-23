package com.syq.biz.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syq.biz.bo.RoleBo;
import com.syq.biz.domain.WzaxModule;
import com.syq.biz.domain.WzaxRight;

public class JsonUtil {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(JsonUtil.class);

  public static String toJson(Object obj) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(obj);

    } catch (Exception e) {
      LOG.error("toJson错误，原因", e);
    }
    return null;
  }

  public static Map<String, Object> roleToJson(RoleBo roleBo) {
    Map<String, Object> node = new HashMap<String, Object>();
    Map<String, String> data = new HashMap<String, String>();
    Map<String, Object> attrMap = new HashMap<String, Object>();

    node.put("data", data);
    node.put("attr", attrMap);

    attrMap.put("icon", "role");
    attrMap.put("id", roleBo.getId());
    data.put("title", roleBo.getName());
    data.put("icon", "./images/tree/folder.png");
    return node;

  }

  /**
   * 组装权限树
   */
  public static Map<String, Object> ModuleToJson(WzaxModule module) {
    Map<String, Object> node = new HashMap<String, Object>();
    Map<String, String> data = new HashMap<String, String>();
    Map<String, Object> attrMap = new HashMap<String, Object>();

    node.put("data", data);
    node.put("attr", attrMap);
    List<WzaxModule> elements = module.getChildren();
    if (elements.size() <= 0) {
      List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
      node.put("children", children);
      node.put("state", "open");

      List<WzaxRight> rights = module.getRights();
      Map<String, Object> cnode = null;
      Map<String, String> cdata = null;
      Map<String, Object> cattrMap = null;

      if (CollectionUtils.isNotEmpty(rights)) {
        for (WzaxRight rTemp : rights) {
          cnode = new HashMap<String, Object>();
          cdata = new HashMap<String, String>();
          cattrMap = new HashMap<String, Object>();
          cattrMap.put("id", rTemp.getId());
          cnode.put("data", cdata);
          cnode.put("attr", cattrMap);
          cdata.put("title", rTemp.getName());
          children.add(cnode);
        }
        attrMap.put("icon", "module");
        attrMap.put("id", "m" + module.getId());
        data.put("title", module.getName());
        data.put("icon", "./images/tree/folder.png");
        return node;
      }
    }

    List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
    Iterator<WzaxModule> it = elements.iterator();
    while (it.hasNext()) {
      WzaxModule mTemp = it.next();
      children.add(ModuleToJson(mTemp));
    }

    attrMap.put("icon", "module");
    data.put("title", module.getName());
    data.put("icon", "./images/tree/folder.png");
    attrMap.put("id", "m" + module.getId());
    node.put("state", "open");

    List<WzaxRight> rights = module.getRights();
    Map<String, Object> cnode = null;
    Map<String, String> cdata = null;
    Map<String, Object> cattrMap = null;
    for (WzaxRight rTemp : rights) {
      cnode = new HashMap<String, Object>();
      cdata = new HashMap<String, String>();
      cattrMap = new HashMap<String, Object>();
      cnode.put("data", cdata);
      cnode.put("attr", cattrMap);
      cattrMap.put("icon", "right");
      cattrMap.put("id", rTemp.getId());
      cdata.put("title", rTemp.getName());
      children.add(cnode);
    }
    node.put("children", children);

    return node;


  }

  public static Map<String, Object> addRightNode(List<Map<String, Object>> children) {
    Map<String, Object> node = new HashMap<String, Object>();
    Map<String, String> data = new HashMap<String, String>();
    Map<String, Object> attrMap = new HashMap<String, Object>();
    node.put("data", data);
    node.put("attr", attrMap);
    attrMap.put("id", "rightnode");
    data.put("icon", "./images/tree/house.png");
    data.put("title", "统一身份认证平台权限树");
    node.put("children", children);
    node.put("state", "open");
    return node;
  }
  
  public static Map<String, Object> addRoleNode(List<Map<String, Object>> children) {
    Map<String, Object> node = new HashMap<String, Object>();
    Map<String, String> data = new HashMap<String, String>();
    Map<String, Object> attrMap = new HashMap<String, Object>();
    node.put("data", data);
    node.put("attr", attrMap);
    attrMap.put("id", "rolenode");
    data.put("icon", "./images/tree/house.png");
    data.put("title", "角色树");
    node.put("children", children);
    node.put("state", "open");
    return node;
  }
}
