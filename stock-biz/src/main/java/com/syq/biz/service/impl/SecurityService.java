package com.syq.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syq.biz.bo.RoleBo;
import com.syq.biz.domain.WzaxModule;
import com.syq.biz.mapper.WzaxModuleMapper;
import com.syq.biz.service.ISecurityService;
import com.syq.biz.util.JsonUtil;

@Service
public class SecurityService implements ISecurityService{
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(SecurityService.class);
  
  @Autowired
  private WzaxModuleMapper wzaxModuleMapper;
  
  @Override
  public Map<String,Object> getRootModulesToJson() throws Exception{
    List<WzaxModule> modules = wzaxModuleMapper.getRootModules();
    List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>(modules.size());
    for(WzaxModule module : modules)
        listMap.add(JsonUtil.ModuleToJson(module));
    //获取根节点
    Map<String, Object> modulesTree = JsonUtil.addRightNode(listMap);
    return modulesTree;
  }

}
