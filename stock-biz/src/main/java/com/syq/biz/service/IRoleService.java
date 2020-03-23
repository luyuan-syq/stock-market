package com.syq.biz.service;

import java.util.List;
import java.util.Map;

import com.syq.biz.bo.RoleBo;
import com.syq.biz.domain.Role;
import com.syq.biz.domain.WzaxRight;


public interface IRoleService {
  
  Role get(long id);
  
  boolean addRole(RoleBo roleBo);
  
  boolean editRole(RoleBo roleBo);
  
  List<WzaxRight> selectRightByRoleId(long id);
  
  Map<String,Object> getRolesToJson() throws Exception;

}
