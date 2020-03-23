package com.syq.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syq.biz.bo.RoleBo;
import com.syq.biz.domain.Role;
import com.syq.biz.domain.WzaxModule;
import com.syq.biz.domain.WzaxRight;
import com.syq.biz.mapper.RoleMapper;
import com.syq.biz.mapper.WzaxRightMapper;
import com.syq.biz.service.IRoleService;
import com.syq.biz.util.JsonUtil;
import com.syq.pagination.IAdapterService;
import com.syq.pagination.common.PaginationPo;
import com.syq.util.convert.BeanUtil;

@Service
public class RoleService implements IRoleService,IAdapterService<RoleBo>{
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(RoleService.class);
  
  @Autowired
  private RoleMapper roleMapper;
  @Autowired
  private WzaxRightMapper rightMapper;

  @Override
  public <T extends PaginationPo> int getCount(T t) {
    return roleMapper.getCount(t);
  }

  @Override
  public <T extends PaginationPo> List<RoleBo> query(T t) {
    return roleMapper.selectByCondition(t);
  }
  
  @Transactional
  public boolean addRole(RoleBo roleBo) {
    Role role = new Role();
    BeanUtil.copyProperties(roleBo, role);
    roleMapper.save(role);
    roleBo.setId(role.getId());
    roleMapper.saveRoleAndRight(roleBo);
    return true;
  }

  @Override
  public List<WzaxRight> selectRightByRoleId(long roleId) {
    List<WzaxRight> rights =  rightMapper.selectByRoleId(roleId);
    return rights;
  }

  @Override
  public Role get(long id) {
    return roleMapper.selectById(id);
  }

  @Override
  public boolean editRole(RoleBo roleBo) {
    Role role = new Role();
    BeanUtil.copyProperties(roleBo, role);
    roleMapper.update(role);
    roleBo.setId(role.getId());
    roleMapper.deleteRoleAndRight(role.getId());
    roleMapper.saveRoleAndRight(roleBo);
    return true;
  }

  @Override
  public Map<String, Object> getRolesToJson() throws Exception {
    
   List<RoleBo> roles = roleMapper.selectByCondition(null);
    
    List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>(roles.size());
    for(RoleBo role : roles)
        listMap.add(JsonUtil.roleToJson(role));
    //获取根节点
    Map<String, Object> modulesTree = JsonUtil.addRoleNode(listMap);
    return modulesTree;
  }
}
