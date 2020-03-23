package com.syq.biz.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.syq.biz.bo.RoleBo;
import com.syq.biz.domain.Role;

@Repository
public interface RoleMapper extends BaseMapper<Role>{
  
  <T> List<RoleBo> selectByCondition(T rolePo);
  
  <T> int getCount(T rolePo);
  
  boolean saveRoleAndRight(RoleBo roleBo);
  
  boolean deleteRoleAndRight(long roleId);
  
}
