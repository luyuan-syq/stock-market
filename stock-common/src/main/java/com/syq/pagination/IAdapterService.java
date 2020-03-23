package com.syq.pagination;

import java.util.List;

import com.syq.pagination.common.PaginationPo;

public interface IAdapterService<E> {
  
  <T extends PaginationPo> int getCount(T t);
  
  <T extends PaginationPo> List<E> query(T t);

}
