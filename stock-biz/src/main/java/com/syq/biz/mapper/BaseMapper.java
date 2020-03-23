package com.syq.biz.mapper;

import java.util.List;


public interface BaseMapper<T> {
  
  int save(T t);
  
  int batchSave(List<T> list);
  
  int delete(T t);
  
  int update(T t);
  
  T select(T t);
  
  int deleteByIds(Long[] ids);
  
  T selectById(Long id);
  
}
