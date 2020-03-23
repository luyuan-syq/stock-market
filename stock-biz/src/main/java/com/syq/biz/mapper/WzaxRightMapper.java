package com.syq.biz.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.syq.biz.domain.WzaxRight;

@Repository
public interface WzaxRightMapper extends BaseMapper<WzaxRight>{
  
  List<WzaxRight> selectByRoleId(long roleId);

}
