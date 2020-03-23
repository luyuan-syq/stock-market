package com.syq.biz.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.syq.biz.domain.WzaxModule;

@Repository
public interface WzaxModuleMapper extends BaseMapper<WzaxModule>{
  
  List<WzaxModule> getRootModules();

}
