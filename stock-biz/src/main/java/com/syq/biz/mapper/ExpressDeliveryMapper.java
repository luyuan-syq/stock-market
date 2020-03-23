package com.syq.biz.mapper;

import org.springframework.stereotype.Repository;

import com.syq.biz.bo.ExpressDeliveryBo;
import com.syq.biz.domain.ExpressDelivery;

@Repository
public interface ExpressDeliveryMapper extends BaseMapper<ExpressDelivery>{
  
  
  <T> ExpressDeliveryBo selectDeliveryBoById(Long id);
  
  <T> ExpressDeliveryBo selectDeliveryBoByTaskId(Long taskId);
  
  
  
}
