package com.syq.biz.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.syq.biz.domain.Log;

@Repository
public interface LogMapper extends BaseMapper<Log>{
  
  <T> List<Log> selectByCondition(T logPo);
  
  <T> int getCount(T logPo);

}
