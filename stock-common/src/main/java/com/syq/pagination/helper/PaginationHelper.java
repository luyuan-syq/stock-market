package com.syq.pagination.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.syq.pagination.IAdapterService;
import com.syq.pagination.common.PaginationPo;

public class PaginationHelper {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(PaginationHelper.class);
  
  public static <T> void paginationHandle(PaginationPo po,IAdapterService<T> adapter,ModelAndView view) {
    
    if(po.getPageNo() == null || po.getPageNo() < 0) {
      po.setPageNo(1);
    }
    
    Integer total = adapter.getCount(po);
    Integer totalPage = (total + po.getMaxResults() - 1) / po.getMaxResults();
    
    if(totalPage > 0 && po.getPageNo() > totalPage) {
      po.setPageNo(totalPage);
    }

    view.addObject("total", total);
    view.addObject("pageNo", po.getPageNo());
    view.addObject("rows", adapter.query(po));
    view.addObject("limit", po.getMaxResults());
    view.addObject("start", po.getStart());
    
  }
  
  public static <T> int paginationCountHandle(PaginationPo po,IAdapterService<T> adapter) {
    return adapter.getCount(po);
  }
}
